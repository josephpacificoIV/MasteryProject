package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
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
        List<Guest> guests = service.findGuestByEmail("test1@gmail.com");
        assertEquals(1, guests.size());
        //System.out.println(guests.get(0).getEmail());
    }

    /*@Test
    void shouldFindAllGuests() throws DataException {
        List<Guest> guests = service.findAllGuest();
        assertEquals(1, guests.size());
        //System.out.println(guests.get(0).getEmail());
    }*/



}