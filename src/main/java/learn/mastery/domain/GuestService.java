package learn.mastery.domain;

import learn.mastery.Model.Guest;
import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;


    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) throws DataException {
        return repository.findAll().stream()
                .filter(i -> i.getEmail().startsWith(email))
                .findFirst()
                .orElse(null);
    }

    public List<Guest> findAll() throws DataException {
        return repository.findAll();
    }

    public Guest findById(String id) throws DataException {
        return repository.findById(id);
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
