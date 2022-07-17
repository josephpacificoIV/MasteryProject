package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public boolean duplicate;

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

    public Reservation findReservationByEmail(List<Reservation> reservations, String email) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getGuest().getEmail(), email))
                .findFirst()
                .orElse(null);

    }

    public Reservation findReservationById(List<Reservation> reservations, String email, String id) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getGuest().getEmail(), email))
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);

    }



    public Result<Reservation> validateDomain(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        System.out.printf("%n-- Date Range is available --%n");
        // if reservation is valid, calculate total
        BigDecimal total =
                calculateTotal(reservation.getHost(),
                        reservation.getStart_date(),
                        reservation.getEnd_date());

        System.out.printf("%nSummary%n========%nStart: %s%nEnd: %s%nTotal: $%s%n",
                reservation.getStart_date(),
                reservation.getEnd_date(),
                total);
        reservation.setTotal(total);

        // create nextId
        List<Reservation> all = reservationRepository.findById(reservation.getHost().getId());
        int nextId = 0;
        for(Reservation r: all){
            nextId = Math.max(nextId, Integer.parseInt(r.getId()));
        }
        nextId++;
        reservation.setId(String.valueOf(nextId));


        return result;
    }

    public Result<Reservation> add(Result<Reservation> result, Reservation reservation) throws DataException {

        result.setPayload(reservationRepository.add(reservation));

        return result;
    }

    public Result<Reservation> update(Reservation reservation, Result<Reservation> result) throws DataException {
        /*result.setPayload(reservationRepository.update(reservation));

        return result;*/
        if (result.isSuccess()) {
            if (reservationRepository.update(reservation)) {
                result.setPayload(reservation);
            } else {
                String message = String.format("Reservation id %s was not found.", reservation.getId());
                result.addErrorMessage(message);
            }
        }
        return result;
    }

    public Result<Reservation> validateUpdate(Reservation reservation) throws DataException {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }
        System.out.printf("%n-- Date Range is available --%n");
        // if reservation is valid, calculate total
        BigDecimal total =
                calculateTotal(reservation.getHost(),
                        reservation.getStart_date(),
                        reservation.getEnd_date());

        System.out.printf("%nSummary%n========%nStart: %s%nEnd: %s%nTotal: $%s%n",
                reservation.getStart_date(),
                reservation.getEnd_date(),
                total);
        reservation.setTotal(total);

        /*// create nextId
        List<Reservation> all = reservationRepository.findById(reservation.getHost().getId());
        int nextId = 0;
        for(Reservation r: all){
            nextId = Math.max(nextId, Integer.parseInt(r.getId()));
        }
        nextId++;
        reservation.setId(String.valueOf(nextId));*/


        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        // check for overlapping dates
        validateOverlap(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        //validateChildrenExist(reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (reservation.getStart_date() == null) {
            result.addErrorMessage("Reservation start date is required.");
        }

        if (reservation.getEnd_date() == null) {
            result.addErrorMessage("Reservation end date is required.");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
        }
        return result;
    }

    private Result<Reservation> validateOverlap(Reservation reservation, Result<Reservation> result){

        List<Reservation> all = reservationRepository.findById(reservation.getHost().getId());

        for (Reservation r : all) {
            if ( !(reservation.getStart_date().isAfter(r.getEnd_date()) || reservation.getEnd_date().isBefore(r.getStart_date())) )
             {
                result.addErrorMessage(String.format("Reservations cannot overlap. %s - %s",
                        reservation.getStart_date(),
                        reservation.getEnd_date()));
                break;

            }
        }

        return result;

    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        // No past dates.
        if ( reservation.getStart_date().isBefore(LocalDate.now()) ||
                reservation.getEnd_date().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation date range must be in the future.");
        }

        if ( reservation.getStart_date().isAfter(reservation.getEnd_date()) ||
                reservation.getEnd_date().isBefore(reservation.getStart_date())) {
            result.addErrorMessage("Start Date must be before End Date.");
        }


    }


    /*private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {

        if (reservation.getForager().getId() == null
                || foragerRepository.findById(reservation.getForager().getId()) == null) {
            result.addErrorMessage("Forager does not exist.");
        }

        if (itemRepository.findById(reservation.getItem().getId()) == null) {
            result.addErrorMessage("Item does not exist.");
        }
    }*/

    public BigDecimal calculateTotal(Host host, LocalDate start_date, LocalDate end_date) {

        BigDecimal standard = hostRepository.findById(host.getId()).getStandard_rate();
        BigDecimal weekend = hostRepository.findById(host.getId()).getWeekend_rate();

        /*if ((start_date == null || end_date == null) ||
                (host.getWeekend_rate() <= 0 || host.getStandard_rate() <= 0)) {
            return BigDecimal.ZERO;
        }*/
        /*BigDecimal kilos = new BigDecimal(kilograms).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);
        return null;*/

        Set<DayOfWeek> weekends = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

        //List<Reservation> reservations = reservationRepository.findById(host.getId());
        //Reservation result = reservationRepository.findReservationById(reservations, reservation_id);

        final long weekDaysBetween = start_date.datesUntil(end_date)
                .filter(d -> !weekends.contains(d.getDayOfWeek()))
                .count();
        //BigDecimal weekDayCost = BigDecimal.valueOf(weekDaysBetween * (host.getStandard_rate()) );

        Set<DayOfWeek> weekday = EnumSet.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY);
        final long weekEndsBetween = start_date.datesUntil(end_date)
                .filter(d -> !weekday.contains(d.getDayOfWeek()))
                .count();
        BigDecimal total = standard.multiply(new BigDecimal(weekDaysBetween)).add(weekend.multiply(new BigDecimal(weekEndsBetween)));
        System.out.printf("%nWeekdays(%s) @ $%s / per day%nWeekEndDays(%s) @ $%s / per day%nTotal: $%s%n",
                weekDaysBetween,
                host.getStandard_rate(),
                weekEndsBetween,
                host.getWeekend_rate(),
                total);
        //BigDecimal weekEndCost = BigDecimal.valueOf(weekEndsBetween * (host.getWeekend_rate()) );
        return total;
        /*total = weekEndCost.add(weekDayCost);
        return total;*/
    }




}
