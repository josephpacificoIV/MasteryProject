package learn.mastery.ui;

import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.util.List;

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

    public void displayReservations(List<Host> hosts) {
        if (hosts == null) {
            io.println("No hosts found.");
            return;
        }

        for (Host host : hosts){
            //io.println(getHostEmail());
            io.printf("%nHost Email: %s%n%s: %s,%s%n",
                    host.getEmail(),
                    host.getLast_name(),
                    host.getCity(),
                    host.getState()
                    );
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
}
