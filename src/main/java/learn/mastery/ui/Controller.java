package learn.mastery.ui;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final ReservationService reservationService;
    private final HostService hostService;
    private final GuestService guestService;
    private final View view;

    public Controller(ReservationService reservationService,
                      HostService hostService,
                      GuestService guestService,
                      View view) {

        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
        this.view = view;
    }


    public void run() {
        view.displayHeader("Welcome to Don't Wreck Your House");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservationByHost();
                    break;
                case CREATE_RESERVATION:
                    createReservation();
                    break;
                case UPDATE_RESERVATION:
                    updateReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }


    private void viewReservationByHost() {
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);
        view.displayHost(host);

        String id = view.displayHost(host);
        List<Reservation> reservations = reservationService.findById(id);
        view.displayReservations(reservations, id);
        view.enterToContinue();
    }


    private void createReservation() throws DataException {

        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email); // finds host

        String id = view.displayHost(host); // displays host reservations
        List<Reservation> reservations = reservationService.findById(id);
        view.displayReservations(reservations, id); // display all reservations for a host


        String emailGuest = view.getGuestEmail(); // get a guest email input
        Guest guest = guestService.findByEmail(emailGuest); // find guest

        Reservation reservation = view.makeReservation(host,guest); // creates a reservation

        //checkReservation(reservation);

        Result<Reservation> result = reservationService.validateDomain(reservation);


        // if reservation is valid and available, send to confirmation screen
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.confirmAdd(result);
        }

        // if admin selects y, then no error message and you add
        if(result.isSuccess()){
            result = reservationService.add(result, reservation); // adds
        }

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }

    }




    private void updateReservation() throws DataException {

        boolean isValid = false;
        List<Reservation> reservations;
        Host host;
        do {
            // get host data
            String email = view.getHostEmail();
            host = hostService.findByEmail(email); // finds host

            String id = view.displayHost(host); // displays host reservations
            reservations = reservationService.findById(id);
            view.displayReservations(reservations, id); // display all reservations for a host
            if(!reservations.isEmpty()){
                isValid = true;
            }
        } while (!isValid);

        // get guest data
        String emailGuest = view.getGuestEmail(); // get a guest email input
        Guest guest = guestService.findByEmail(emailGuest); // find guest

        view.displayReservationsByGuestEmail(reservations, emailGuest); // display all reservations by guest email

        String reservation_id = view.getReservationId(); // selects a reservation
        Reservation reservation_to_change = reservationService.findReservationById(reservations, emailGuest, reservation_id);

        view.displayReservationByGuest(reservations, reservation_id); // display reservation to update
        Reservation reservation = view.makeUpdate(host,guest, reservation_to_change); // creates an updated reservation

        //checkReservation(reservation);
        Result<Reservation> result = reservationService.validateUpdate(reservation); // check if updated reservation is valid

        // if reservation is valid and available, send to confirmation screen
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.confirmAdd(result);
        }

        // if admin selects y, then no error message and you add
        if(result.isSuccess()){
            result = reservationService.update(reservation, result); // updates
        }

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s updated.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }

    }


    private void deleteReservation() throws DataException {

        view.displayHeader(MainMenuOption.DELETE_RESERVATION.getMessage());

        boolean isValid = false;
        List<Reservation> reservations;
        Host host;
        do {
            // get host data
            String email = view.getHostEmail();
            host = hostService.findByEmail(email); // finds host

            String id = view.displayHost(host); // displays host reservations
            reservations = reservationService.findById(id);
            view.displayReservations(reservations, id); // display all reservations for a host
            if(!reservations.isEmpty()){
                isValid = true;
            }
        } while (!isValid);

        // get guest data
        isValid = false;
        Reservation reservation_to_delete;
        do {
            String emailGuest = view.getGuestEmail(); // get a guest email input
            Guest guest = guestService.findByEmail(emailGuest); // find guest
            if(guest != null ){
                isValid = true;
            }
            view.displayReservationsByGuestEmail(reservations, emailGuest); // display all reservations by guest email


            String reservation_id = view.getReservationId(); // selects a reservation
            reservation_to_delete = reservationService.findReservationById(reservations, emailGuest, reservation_id);
            if(reservation_to_delete == null){
                isValid = false;
            }

            try {
                view.displayReservationByGuest(reservations, reservation_to_delete.getId()); // display reservation to delete
            } catch (NullPointerException ex) {
                System.out.printf("%nEnter valid Reservation data.%n");
            }
        } while(!isValid);


        Result<Reservation> result = reservationService.deleteById(host.getId(), reservation_to_delete.getId());

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s deleted.", reservation_to_delete.getId());
            view.displayStatus(true, successMessage);
        }

    }

    /*private void checkReservation(Reservation reservation) throws DataException {

        Result<Reservation> result = reservationService.validateDomain(reservation);

        // if reservation is valid and available, send to confirmation screen
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.confirmAdd(result);
        }

        // if admin selects y, then no error message and you add
        if(result.isSuccess()){
            result = reservationService.add(result, reservation); // adds
        }


        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }

    }*/


}
