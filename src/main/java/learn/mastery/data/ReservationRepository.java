package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(String id);

    Reservation findById(List<Reservation> reservations, int id);


}
