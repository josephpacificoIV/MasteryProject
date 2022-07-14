package learn.mastery.data;

import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Host> findByEmail(String email);

    Reservation findById(List<Reservation> reservations, String id);

}
