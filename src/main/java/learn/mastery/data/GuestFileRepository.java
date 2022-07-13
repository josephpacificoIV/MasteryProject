package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestFileRepository implements GuestRepository {

    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAllGuest() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Guest findGuestById(String id) {
        return findAllGuest().stream()
                .filter(i -> i.getGuest_id().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findGuestByEmail(String email) {
        return findAllGuest().stream()
                .filter(i -> Objects.equals(i.getEmail(), email))
                .findFirst()
                .orElse(null);
    }




    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getGuest_id(),
                guest.getFirst_name(),
                guest.getLast_name(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuest_id(fields[0]);
        result.setFirst_name(fields[1]);
        result.setLast_name(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }

    protected void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if (guests == null) {
                return;
            }

            for (Guest guest : guests) {
                writer.println(serialize(guest));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }


}
