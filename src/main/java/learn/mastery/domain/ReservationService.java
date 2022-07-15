package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;
import learn.mastery.data.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
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



    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        // if valid reservation is valid, calculate total
        BigDecimal total =
                calculateTotal(reservation.getHost(),
                        reservation.getStart_date(),
                        reservation.getEnd_date());
        reservation.setTotal(total);

        result.setPayload(reservationRepository.add(reservation));

        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        /*// check for duplicates
        validateDuplicate(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }*/

        /*validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);*/

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

    private void validateDuplicate(Reservation reservation, Result<Reservation> result){
        /*List<Forage> forages = forageRepository.findByDate(reservation.getDate());
        for (Forage f : forages) {
            if (Objects.equals(reservation.getDate(), f.getDate())
                    && Objects.equals(reservation.getForager().getId(), f.getForager().getId())
                    && Objects.equals(reservation.getItem().getId(), f.getItem().getId())) {
                result.addErrorMessage(String.format("Forager %s %s (%s) with Item: %s is a duplicate.",
                        reservation.getForager().getFirstName(),
                        reservation.getForager().getLastName(),
                        reservation.getForager().getState(),
                        reservation.getItem().getName()));
                duplicate = ForageFileRepository.duplicate;
                break;
            }
        }*/
        List<Reservation> reservations = reservationRepository.findById(reservation.getHost().getId());
        for (Reservation r : reservations) {
            if (( reservation.getStart_date().isAfter(r.getStart_date()) && reservation.getEnd_date().isBefore(r.getEnd_date()) )
                            || ( reservation.getStart_date().isBefore(r.getStart_date()) &&  reservation.getEnd_date().isBefore(r.getEnd_date()) )
                            || ( reservation.getStart_date().isAfter(r.getStart_date()) &&  reservation.getEnd_date().isAfter(r.getEnd_date()) )
                            || ( reservation.getStart_date().isBefore(r.getStart_date()) &&  reservation.getEnd_date().isAfter(r.getEnd_date()) ) ){
                result.addErrorMessage(String.format("Reservations cannot overlap. %s - %s",
                        reservation.getStart_date(),
                        reservation.getEnd_date()));
                duplicate = ReservationFileRepository.duplicate;
                break;
            }
        }


    }

    /*private void validateFields(Reservation reservation, Result<Reservation> result) {
        // No future dates.
        if (reservation.getStart_date().isAfter(LocalDate.now())) {
            result.addErrorMessage("Forage date cannot be in the future.");
        }

        if (reservation.getKilograms() <= 0 || reservation.getKilograms() > 250.0) {
            result.addErrorMessage("Kilograms must be a positive number less than 250.0");
        }

    }*/


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
        //BigDecimal weekEndCost = BigDecimal.valueOf(weekEndsBetween * (host.getWeekend_rate()) );
        return standard.multiply(new BigDecimal(weekDaysBetween)).add(weekend.multiply(new BigDecimal(weekEndsBetween)));
        /*total = weekEndCost.add(weekDayCost);
        return total;*/
    }



}
