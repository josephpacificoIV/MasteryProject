package learn.mastery.data;

import learn.mastery.Model.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findById(String id);

    Guest findByEmail(String email);

}
