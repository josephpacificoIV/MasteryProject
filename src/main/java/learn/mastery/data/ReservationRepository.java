package learn.mastery.data;

import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(String host);


}
