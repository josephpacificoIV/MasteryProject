package learn.mastery.data;

import learn.mastery.Model.Guest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{

    public final static Guest GUEST = new Guest("1", "Test", "Test", "test@gmail.com", "18007778888", "NY" );
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }

    @Override
    public List<Guest> findAll() {
        return findAll().stream()
                .filter(i -> i.getGuest_id() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findById(String id) {
        return null;
    }
}
