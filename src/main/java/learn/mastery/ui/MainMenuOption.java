package learn.mastery.ui;

public enum MainMenuOption {

    VIEW_RESERVATIONS(1, "View Reservations by Host"),
    CREATE_RESERVATION(2, "Create a Reservation"),
    UPDATE_RESERVATION(3, "Update a Reservation"),
    DELETE_RESERVATION(4, "Delete a Reservation"),
    EXIT(5, "Exit" );


    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }


    private int value;
    private String message;


    private MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }



}
