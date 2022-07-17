package learn.mastery.data;

import learn.mastery.Model.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation findReservationById(List<Reservation> reservations, String id);

    List<Reservation> findReservationsByGuestId(List<Reservation> reservations, String id);

    List<Reservation> findById(String id);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

}
