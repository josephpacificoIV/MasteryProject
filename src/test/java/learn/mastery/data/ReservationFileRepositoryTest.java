package learn.mastery.data;

import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/2e72f86c-b8fe-4265-b4f1-304dea8762db_seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    static final int RESERVATION_COUNT = 12;

    /*final Host host = new Host(
            "2e72f86c-b8fe-4265-b4f1-304dea8762db",
            "Test",
            "test@gmail.com",
            "18008889090",
            "test address",
            "test city",
            "NY",
            "NY",
            125.00,
            150.00);*/
    final String id = "2e72f86c-b8fe-4265-b4f1-304dea8762db";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> forages = repository.findByHostId(id);
        assertEquals(RESERVATION_COUNT, forages.size());
    }

    @Test
    void shouldCalculateTotalCost() {
        List<Reservation> forages = repository.findByHostId(id);
        assertEquals(RESERVATION_COUNT, forages.size());
    }

}