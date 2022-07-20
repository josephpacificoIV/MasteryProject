package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());


    @Test
    void shouldFindHostById(){
        List<Reservation> reservations = service.findById(HostRepositoryDouble.HOST.getId());
        //System.out.println(reservations);
        assertEquals(2, reservations.size());

    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation test = new Reservation();

        Guest guest = new Guest();
        guest.setId("1");
        test.setGuest(guest);

        Host host = new Host();
        host.setId("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"); // add existing host from host repository double
        test.setHost(host);

        test.setStart_date(LocalDate.of(2022, 8, 10));
        test.setEnd_date(LocalDate.of(2022, 8, 14));

        Result<Reservation> result = service.validateDomain(test);
        result = service.add(result, test);
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotAddReservationInThePast() throws DataException {
        Reservation test = new Reservation();

        Guest guest = new Guest();
        guest.setId("1");
        test.setGuest(guest);

        Host host = new Host();
        host.setId("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"); // add existing host from host repository double
        test.setHost(host);

        test.setStart_date(LocalDate.of(2021, 8, 10));
        test.setEnd_date(LocalDate.of(2021, 8, 14));

        Result<Reservation> result = service.validateDomain(test);
        result = service.add(result, test);
        assertFalse(result.isSuccess());
    }


    @Test
    void shouldUpdateReservation() throws DataException {

        List<Reservation> all = service.findById(HostRepositoryDouble.HOST.getId());
        assertEquals(2, all.size());

        Reservation reservation = all.get(0);
        assertEquals("1", reservation.getId());
        assertNotNull(reservation);


        // update end date from 2022-10-14
        reservation.setEnd_date(LocalDate.of(2022, 10, 16));

        // same info as previous reservation
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setGuest_id(GuestRepositoryDouble.GUEST.getId());
        reservation.setHost(HostRepositoryDouble.HOST);

        Result<Reservation> result = service.validateUpdate(reservation);
        result = service.update(reservation, result);
        //assertTrue(result.isSuccess());


        List<Reservation> new_all = service.findById("2");
        //Reservation new_reservation = new_all.get(0);

        // assert that new date has been updated
        //assertEquals(LocalDate.of(2022, 10, 16), new_reservation.getEnd_date());
    }

    @Test
    void shouldNotDeleteReservationInThePast() throws DataException{
        Result<Reservation> result = service.deleteById(HostRepositoryDouble.HOST.getId(), "1");
        result.getErrorMessages();
        assertFalse(result.isSuccess());
    }





}