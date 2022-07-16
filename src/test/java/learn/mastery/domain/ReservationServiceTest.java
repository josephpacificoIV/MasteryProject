package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.Test;

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
        List<Reservation> reservations = service.findById(String.valueOf(HostRepositoryDouble.HOST));
        //System.out.println(reservations);
        assertEquals(1, reservations.size());

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
    void shouldUpdateReservation() {
        
    }





}