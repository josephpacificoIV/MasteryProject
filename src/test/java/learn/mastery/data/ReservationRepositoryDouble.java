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
        reservation.setGuest_id(GuestRepositoryDouble.GUEST.getId());
        reservation.setTotal(new BigDecimal("400"));
        reservation.setGuest(GuestRepositoryDouble.GUEST);

        reservation.setHost(HostRepositoryDouble.HOST);
        reservations.add(reservation);

        Reservation reservation1 = new Reservation();
        reservation1.setId("2");
        reservation1.setStart_date(LocalDate.of(2022, 10, 12));
        reservation1.setEnd_date(LocalDate.of(2022, 10, 14));
        reservation1.setGuest(GuestRepositoryDouble.GUEST);
        reservation1.setGuest_id(GuestRepositoryDouble.GUEST.getId());
        Host host2 = new Host();
        host2.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        reservation1.setHost(host2);
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
    public List<Reservation> findReservationsByGuestId(List<Reservation> reservations, String id) {
        return null;
    }

    @Override
    public List<Reservation> findById(String id) {
        return reservations.stream()
                .filter(i -> i.getHost().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {

        List<Reservation> all = findById(reservation.getHost().getId());
        for (int i = 0; i < all.size(); i++) {
            if (Objects.equals(all.get(i).getId(), reservation.getId())) {
                all.set(i, reservation);
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean deleteById(String host_id, String reservation_id) throws DataException {
        List<Reservation> all = findById(host_id);
        for(int i = 0; i < all.size(); i++) {
            if (Objects.equals(all.get(i).getId(), reservation_id)) { // not instance of the reservation, just the id
                all.remove(i); // at i, we found the right reservation ID to remove
                return true;
            }
        }
        return false;

    }


}
