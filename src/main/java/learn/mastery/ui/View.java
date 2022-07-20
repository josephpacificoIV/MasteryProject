package learn.mastery.ui;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.domain.Result;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }


    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {

            io.printf("%s. %s%n", option.getValue(), option.getMessage());

            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }



    public String getHostEmail() {
        displayHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());
        return io.readRequiredString("Enter Host Email : ");
    }

    public String getGuestEmail() {
        displayHeader("Reservation Information");
        return io.readRequiredString("Enter Guest Email : ");
    }

    public String getReservationId() {
        displayHeader(MainMenuOption.CREATE_RESERVATION.getMessage());
        return io.readRequiredString("Enter Reservation ID : ");
    }

    public String displayHost(Host host) {
        if (host == null) {
            io.println("No hosts found.");
            return null;
        }

            //io.println(getHostEmail());
            io.printf("%nHost Email: %s%n%s: %s,%s%n",
                    host.getEmail(),
                    host.getLast_name(),
                    host.getCity(),
                    host.getState()
                    );
            //displayReservations(host.getId());

            return host.getId();
    }

    public void displayReservations(List<Reservation> reservations, String id) {
        if (id == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }

        reservations.sort(Comparator.comparing(Reservation::getStart_date));

        for (Reservation reservation : reservations) {
            io.printf("%nID: %s, %s - %s Guest:%s, %s, %s%n",
                    reservation.getId(),
                    reservation.getStart_date(),
                    reservation.getEnd_date(),
                    reservation.getGuest().getLast_name(),
                    reservation.getGuest().getFirst_name(),
                    reservation.getGuest().getEmail());
        }
    }

    public void displayReservationByGuest(List<Reservation> reservations, String id) {

        if (id == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        reservations.sort(Comparator.comparing(Reservation::getStart_date));
        Reservation reservation = reservations.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElse(null);


        assert reservation != null;
        io.printf("%nID: %s, %s - %s Guest:%s, %s, %s%n",
                    reservation.getId(),
                    reservation.getStart_date(),
                    reservation.getEnd_date(),
                    reservation.getGuest().getLast_name(),
                    reservation.getGuest().getFirst_name(),
                    reservation.getGuest().getEmail());

    }

    public void displayReservationsByGuestEmail(List<Reservation> reservations, String email) {

        if (email == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        reservations.sort(Comparator.comparing(Reservation::getStart_date));
        List<Reservation> reservations1 = reservations.stream()
                .filter(p -> Objects.equals(p.getGuest().getEmail(), email))
                .collect(Collectors.toList());
                /*.findAny()
                .orElse(null);*/


        assert reservations1 != null;
        for(Reservation reservation : reservations1) {
            io.printf("%nID: %s, %s - %s Guest:%s, %s, %s%n",
                    reservation.getId(),
                    reservation.getStart_date(),
                    reservation.getEnd_date(),
                    reservation.getGuest().getLast_name(),
                    reservation.getGuest().getFirst_name(),
                    reservation.getGuest().getEmail());
        }

    }


    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();

        reservation.setStart_date(io.readLocalDate("Start date [MM/dd/yyyy]: "));
        reservation.setEnd_date(io.readLocalDate("End date [MM/dd/yyyy]: "));

        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setGuest_id(guest.getId());

        // reservation.setId() in ReservationFileRepository,
        // reservation.setTotal() in ReservationService

        return reservation;
    }

    public Reservation makeUpdate(Host host, Guest guest, Reservation reservation) {
        //Reservation reservation = new Reservation();

        boolean isValid = false;
        do {
            String input = io.readString("New Start date [MM/dd/yyyy]: ");
            LocalDate new_start_date;
            if (input.length() == 0) {
                reservation.setStart_date(reservation.getStart_date());
                isValid = true;
            } else {
                try {
                    new_start_date = LocalDate.parse(input, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    reservation.setStart_date(new_start_date);
                    isValid = true;
                } catch (DateTimeParseException ex) {
                    System.out.println("Please enter a valid date.");
                }
            }
        } while (!isValid);

        isValid = false;
        do {
            String input = io.readString("New End date [MM/dd/yyyy]: ");
            LocalDate new_end_date;
            if (input.length() == 0) {
                reservation.setEnd_date(reservation.getEnd_date());
                isValid = true;
            } else {
                try {
                    new_end_date = LocalDate.parse(input, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    reservation.setEnd_date(new_end_date);
                    isValid = true;
                } catch (DateTimeParseException ex) {
                    System.out.println("Please enter a valid date.");
                }
            }
        } while(!isValid);

        // same host, guest
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setGuest_id(guest.getId());

        // reservation.setId() in ReservationFileRepository,
        // reservation.setTotal() in ReservationService

        return reservation;
    }

    public Result<Reservation> confirmAdd(Result<Reservation> result){
        boolean answer;
        answer = io.readBoolean("Is this okay? [y/n]: ");

        if(!answer){
            result.addErrorMessage("You said no. Back to Main Menu.");
            return result;
        } else {
            return result;
        }
        

    }






    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }


    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
}
