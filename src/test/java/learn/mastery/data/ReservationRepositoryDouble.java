package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        reservation.setStart_date(LocalDate.of(2021, 10, 12));
        reservation.setEnd_date(LocalDate.of(2021, 10, 14));
        reservation.setGuest(new Guest());
        reservation.setTotal(new BigDecimal(400));
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findByHost(String id) {
        return null;
    }

    @Override
    public Reservation findById(List<Reservation> reservations, int id) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .collect(Collectors.toList());
    }


}
