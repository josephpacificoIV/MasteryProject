package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation_seed_test_host_id.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/test_host_id.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    //static final int RESERVATION_COUNT = 1;



    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> reservations = repository.findById("test_host_id");
        assertEquals(1, reservations.size());
        assertEquals("663", reservations.get(0).getGuest().getId());
    }

    @Test
    void shouldFindReservationByHost() {
        List<Reservation> reservations = repository.findById("test_host_id");
        Reservation reservation = repository.findReservationById(reservations, "1");

        assertEquals(LocalDate.of(2021, 10, 12), reservation.getStart_date());
        System.out.printf("%s %s %s %s %s",
                reservation.getId(),
                reservation.getStart_date(),
                reservation.getEnd_date(),
                reservation.getGuest().getId(),
                //reservation.getTotal(),
                reservation.getGuest().getEmail());
    }

    @Test
    void shouldAddReservation() throws DataException {


        Reservation reservation = new Reservation();
        reservation.setStart_date(LocalDate.of(2022, 7, 10));
        reservation.setEnd_date(LocalDate.of(2022, 7, 13));

        Guest guest = new Guest(
                "2",
                "Test 1",
                "guest 1",
                "guest1@gmail.com",
                "18007778888",
                "NY");

        reservation.setGuest(guest);
        reservation.setGuest_id(guest.getId());

        Host host = new Host(
                "test_host_id",
                "Test Last",
                "test1@gmail.com",
                "18008889090",
                "Test Address",
                "Test City",
                "NY",
                "Test postal",
                new BigDecimal(340),
                new BigDecimal(425));

        reservation.setHost(host);
        reservation.setTotal(new BigDecimal("400"));

        Reservation actual = repository.add(reservation);
        assertNotNull(actual);
        assertEquals("1", actual.getId());


    }


}