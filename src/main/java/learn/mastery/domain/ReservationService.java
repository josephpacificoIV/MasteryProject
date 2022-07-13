package learn.mastery.domain;

import learn.mastery.Model.Reservation;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;

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



    private Result<Reservation> validate(Reservation reservation) {

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

        /*if (reservation.getDate() == null) {
            result.addErrorMessage("Forage date is required.");
        }

        if (reservation.getForager() == null) {
            result.addErrorMessage("Forager is required.");
        }

        if (reservation.getItem() == null) {
            result.addErrorMessage("Item is required.");
        }*/
        return result;
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {

        if (reservation.getHost().getId() == null
                || hostRepository.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepository.findGuestById(reservation.getGuest().getGuest_id()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }
}
