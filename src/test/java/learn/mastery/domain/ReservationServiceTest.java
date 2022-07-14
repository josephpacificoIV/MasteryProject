package learn.mastery.domain;

import learn.mastery.Model.Reservation;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.Test;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());


    @Test
    void shouldFindHostByEmail(){
        Reservation reservation = new Reservation();
        service.findByHost("2e72f86c-b8fe-4265-b4f1-304dea8762db");

    }

}