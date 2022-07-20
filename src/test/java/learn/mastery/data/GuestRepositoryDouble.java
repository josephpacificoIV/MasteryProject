package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestRepositoryDouble implements GuestRepository{

    private final ArrayList<Guest> guests = new ArrayList<>();

    public final static Guest GUEST =  new Guest(
            "1",
            "Test 1",
            "guest 1",
            "guest1@gmail.com",
            "18007778888",
            "NY");

    public GuestRepositoryDouble() {
        guests.add(new Guest("2", "Test 2", "guest 2", "guest2@gmail.com", "28007778888", "NY"));
        guests.add(new Guest("3", "Test 3", "guest 3", "guest3@gmail.com", "38007778888", "NY"));

    }


    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findById(String id) {
        return findAll().stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findByEmail(String email) {
        return findAll().stream()
                .filter(i -> Objects.equals(i.getId(), email))
                .findFirst()
                .orElse(null);
    }


}
