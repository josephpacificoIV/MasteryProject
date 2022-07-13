package learn.mastery.data;

import learn.mastery.Model.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAllGuest();

    Guest findGuestById(String id);

    Guest findGuestByEmail(String email);

}
