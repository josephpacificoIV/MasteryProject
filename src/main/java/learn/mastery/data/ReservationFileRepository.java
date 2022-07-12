package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;


    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHost(String host) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, host));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }



    private String getFilePath(String host) {
        return Paths.get(directory, host + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, String host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host))) {

            writer.println(HEADER);

            for (Reservation guest : reservations) {
                writer.println(serialize(guest));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation guest) {
        return String.format("%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getStart_date(),
                guest.getEnd_date(),
                guest.getGuest_id(),
                guest.getTotal());
    }

    private Reservation deserialize(String[] fields, String host) {
        /*Reservation result = new Reservation();
        result.setId(fields[0]);
        result.setDate(host);
        result.setKilograms(Double.parseDouble(fields[3]));

        Forager forager = new Forager();
        forager.setId(fields[1]);
        result.setForager(forager);

        Guest guest = new Guest();
        guest.setGuest_id(fields[3]);

        result.setTotal(guest);
        return result;*/
        return null;
    }


}
