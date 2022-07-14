package learn.mastery.ui;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;

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
        // display all reservations for a host
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);

        String id = view.displayHost(host);
        List<Reservation> reservations = reservationService.findById(id);
        view.displayReservations(reservations, id);

        // new reservation inputs
        String emailGuest = view.getGuestEmail();
        Guest guest = guestService.findByEmail(emailGuest);

        view.makeReservation(host,guest);

    }




    private void updateReservation() {
    }

    private void deleteReservation() {
    }


}
