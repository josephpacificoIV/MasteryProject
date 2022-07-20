package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service;

    @BeforeEach
    void setup() {
        GuestRepositoryDouble repository = new GuestRepositoryDouble();
        service = new GuestService(repository);
    }

    @Test
    void shouldFindGuestByEmail() throws DataException {
        Guest guest = service.findByEmail("guest2@gmail.com");
        assertEquals("guest2@gmail.com", guest.getEmail());
    }

    @Test
    void shouldFindAllGuests() throws DataException {
        List<Guest> guests = service.findAll();
        assertEquals(2, guests.size());
    }

    @Test
    void shouldFindGuestById() throws DataException {
        Guest guest = service.findById("2");
        assertEquals("Test 2", guest.getFirst_name());
    }





}