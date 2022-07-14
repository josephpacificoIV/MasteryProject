package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;

import java.math.BigDecimal;
import java.sql.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              HostRepository hostRepository,
                              GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findById(String id) {
        // findByDate method
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(Host::getId, i -> i));

        Map<String, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(Guest::getId, i -> i));

        List<Reservation> result = reservationRepository.findById(id);
        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getId()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getId()));
        }

        return result;
    }

    public BigDecimal getTotal(String host_id, String reservation_id) {



        BigDecimal standard = hostRepository.findById(host_id).getStandard_rate();
        BigDecimal weekend = hostRepository.findById(host_id).getWeekend_rate();

        /*if ((start_date == null || end_date == null) ||
                (host.getWeekend_rate() <= 0 || host.getStandard_rate() <= 0)) {
            return BigDecimal.ZERO;
        }*/
        /*BigDecimal kilos = new BigDecimal(kilograms).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);
        return null;*/

        Set<DayOfWeek> weekends = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

        List<Reservation> reservations = reservationRepository.findById(reservation_id);
        Reservation result = reservationRepository.findReservationById(reservations, reservation_id);

        final long weekDaysBetween = result.getStart_date().datesUntil(result.getEnd_date())
                .filter(d -> !weekends.contains(d.getDayOfWeek()))
                .count();
        //BigDecimal weekDayCost = BigDecimal.valueOf(weekDaysBetween * (host.getStandard_rate()) );

        Set<DayOfWeek> weekday = EnumSet.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY);
        final long weekEndsBetween = result.getStart_date().datesUntil(result.getEnd_date())
                .filter(d -> !weekday.contains(d.getDayOfWeek()))
                .count();
        //BigDecimal weekEndCost = BigDecimal.valueOf(weekEndsBetween * (host.getWeekend_rate()) );
        return standard.multiply(new BigDecimal(weekDaysBetween)).add(weekend.multiply(new BigDecimal(weekEndsBetween)));
        /*total = weekEndCost.add(weekDayCost);
        return total;*/
    }





    /*private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        *//*if (reservation.getDate() == null) {
            result.addErrorMessage("Forage date is required.");
        }

        if (reservation.getForager() == null) {
            result.addErrorMessage("Forager is required.");
        }

        if (reservation.getItem() == null) {
            result.addErrorMessage("Item is required.");
        }*//*
        return result;
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {

        if (reservation.getHost().getId() == null
                || hostRepository.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepository.findById(reservation.getGuest().getId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }*/
}
