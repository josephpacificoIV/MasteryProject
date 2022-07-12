package learn.mastery;

import learn.mastery.data.GuestFileRepository;
import learn.mastery.data.HostFileRepository;
import learn.mastery.data.ReservationFileRepository;
import learn.mastery.domain.ReservationService;
import learn.mastery.ui.ConsoleIO;
import learn.mastery.ui.Controller;
import learn.mastery.ui.View;

public class App {

    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        GuestFileRepository guestFileRepository = new GuestFileRepository("./data/guests.csv");
        HostFileRepository hostFileRepository = new HostFileRepository("./data/hosts.csv");
        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("./data/reservations");

        HostService hostService = new HostService(hostFileRepository);
        ReservationService reservationService = new ReservationService(reservationFileRepository, hostFileRepository, guestFileRepository );
        GuestService guestService = new GuestService(guestFileRepository);

        //Controller controller = context.getBean(Controller.class);
        Controller controller = new Controller(reservationFileRepository, hostFileRepository, guestFileRepository, view);
        controller.run();

    }

}
