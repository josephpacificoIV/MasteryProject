package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setStart_date(LocalDate.of(2021, 10, 12));
        reservation.setEnd_date(LocalDate.of(2021, 10, 14));
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setGuest_id(GuestRepositoryDouble.GUEST.getId());
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setTotal(new BigDecimal("400"));
        reservations.add(reservation);

        Reservation reservation1 = new Reservation();
        reservation1.setId("2");
        reservation1.setStart_date(LocalDate.of(2022, 10, 12));
        reservation1.setEnd_date(LocalDate.of(2022, 10, 14));
        reservation1.setGuest(GuestRepositoryDouble.GUEST);
        reservation1.setGuest_id(GuestRepositoryDouble.GUEST.getId());
        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        reservation1.setHost(host);
        reservation1.setTotal(new BigDecimal("400"));

        reservations.add(reservation1);
    }


    @Override
    public Reservation findReservationById(List<Reservation> reservations, String id) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reservation> findById(String id) {
        return reservations.stream()
                .filter(i -> i.getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return findById(reservation.getId()) != null;
    }


}
