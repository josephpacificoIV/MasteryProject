package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;


    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findGuestByEmail(String email) throws DataException {
        return repository.findAllGuest().stream()
                .filter(i -> Objects.equals(i.getEmail(), email))
                .collect(Collectors.toList());
    }


    /*public List<Guest> findAllGuest() {
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
    }*/
}
