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

    /*@Test
    void shouldFindGuestByEmail() throws DataException {
        List<Guest> guests = service.findByEmail("guest1@gmail.com");
        assertEquals(1, guests.size());
    }*/

    @Test
    void shouldFindAllGuests() throws DataException {
        List<Guest> guests = service.findAll();
        assertEquals(3, guests.size());
    }

    @Test
    void shouldFindGuestById() throws DataException {
        Guest guest = service.findById("1");
        assertEquals("Test 1", guest.getFirst_name());
    }





}