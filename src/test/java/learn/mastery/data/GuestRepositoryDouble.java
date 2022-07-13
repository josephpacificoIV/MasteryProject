package learn.mastery.data;

import learn.mastery.Model.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestRepositoryDouble implements GuestRepository{

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(new Guest("1", "Test 1", "Test 1", "test1@gmail.com", "18007778888", "NY"));
        guests.add(new Guest("2", "Test 2", "Test 2", "test2@gmail.com", "28007778888", "NY"));
        guests.add(new Guest("3", "Test 3", "Test 3", "test3@gmail.com", "38007778888", "NY"));

    }


    @Override
    public List<Guest> findAllGuest() {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findGuestById(String id) {
        return findAllGuest().stream()
                .filter(i -> Objects.equals(i.getGuest_id(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findGuestByEmail(String email) {
        return findAllGuest().stream()
                .filter(i -> Objects.equals(i.getGuest_id(), email))
                .findFirst()
                .orElse(null);
    }


}
