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
        List<Reservation> reservations = service.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");

        assertEquals(1, reservations.size());
        System.out.println(reservations.get(0));
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation test = new Reservation();

        Guest guest = new Guest();
        guest.setId("1");
        test.setGuest(guest);


        Host host = new Host();
        host.setId("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"); // add existing host

        test.setHost(host);
        test.setStart_date(LocalDate.of(2022, 8, 10));
        test.setEnd_date(LocalDate.of(2022, 8, 14));

        Result<Reservation> result = service.add(test);
        assertTrue(result.isSuccess());
    }

}