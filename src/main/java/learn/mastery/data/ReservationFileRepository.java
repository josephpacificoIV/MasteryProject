package learn.mastery.data;

import learn.mastery.Model.Guest;
import learn.mastery.Model.Host;
import learn.mastery.Model.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;


    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Host> findByEmail(String email) {

        List<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getHosts()))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields, email));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }


        return result.stream()
                .filter(i -> Objects.equals(i.getEmail(), email))
                .collect(Collectors.toList());

    }

    public Reservation findById(List<Reservation> reservations, String id) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);

    }




    private String getFilePath(String host) {
        return Paths.get(directory, host + ".csv").toString();
    }
    private String getHosts() {
        return Paths.get("./data/", "hosts.csv").toString();
    }

    /*private void writeAll(List<Reservation> reservations, String host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host))) {

            writer.println(HEADER);

            for (Reservation guest : reservations) {
                writer.println(serialize(guest));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }*/

    /*private String serialize(Reservation guest) {
        return String.format("%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getStart_date(),
                guest.getEnd_date(),
                guest.getGuest(),
                guest.getTotal());
    }*/

    private Host deserialize(String[] fields, String email) {
        // 6 fields for reservation, need 6 result.set
        //x 5 fields in a reservation file, need 5 fields[] sets
        // id, start_date, end_date
        Host result = new Host();

        result.setId(fields[0]);
        result.setLast_name(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostal_code(fields[7]);
        result.setStandard_rate(new BigDecimal(fields[8]));
        result.setWeekend_rate(new BigDecimal(fields[9]));

        if(Objects.equals(result.getEmail(), email)){
            System.out.println("We found this host: ");

        }


        /*result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));

        // guest_id comes from guests.csv
        Guest guest = new Guest();
        guest.setId(fields[3]);
        result.setGuest(guest);

        // getTotal() method in Host
        *//*Host host1 = new Host();
        BigDecimal total = host1.setTotal();
        result.setTotal(total);*//*

        // or

        // getTotal() method in Reservation
        result.setTotal(BigDecimal.valueOf(Long.parseLong(fields[4])));


        Host host = new Host();
        host.setId(id);
        // need a new method that will find host in hosts.csv

        //

        result.setHost(host);*/

        return result;
       // return null;
    }


}
