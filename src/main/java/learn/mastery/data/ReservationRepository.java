package learn.mastery.data;

import learn.mastery.Model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String id);


}
