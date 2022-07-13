package learn.mastery.data;

import learn.mastery.Model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_PATH = "./data/guests-seed.csv";
    static final String TEST_PATH = "./data/guests-test.csv";
    static final int NEXT_ID = 6;

    GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAllGuests() {
        List<Guest> all = repository.findAllGuest();
        assertEquals(5, all.size());
    }

    @Test
    void shouldFindGuestByEmail() {
        Guest guest = repository.findGuestByEmail("slomas0@mediafire.com");
        assertEquals("slomas0@mediafire.com", guest.getEmail());
    }

    @Test
    void shouldNotFindGuestByNonExistingEmail() {
        Guest guest = repository.findGuestByEmail("missing@mediafire.com");
        assertNull(guest);
    }
}