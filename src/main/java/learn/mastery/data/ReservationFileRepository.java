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

public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public static boolean duplicate = false;


    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findById(String id) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, id));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }



    public Reservation findReservationById(List<Reservation> reservations, String id) {
        return reservations.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);

    }


    private String getFilePath(String host) {
        return Paths.get(directory, host + ".csv").toString();
    }


    public Reservation add(Reservation reservation) throws DataException {

        List<Reservation> all = findById(reservation.getId());
        // create next ID
        int nextId = 0;
        for(Reservation r: all){
            nextId = Math.max(nextId, Integer.parseInt(r.getId()));
        }
        nextId++;
        reservation.setId(String.valueOf(nextId));

        //reservation.setId(java.util.UUID.randomUUID().toString());


        //List<Forage> forages = findByDate(forage.getDate());
        for (Reservation r : all) {
            if (
                    ( reservation.getStart_date().isAfter(r.getStart_date()) && reservation.getEnd_date().isBefore(r.getEnd_date()) )
                    || ( reservation.getStart_date().isBefore(r.getStart_date()) &&  reservation.getEnd_date().isBefore(r.getEnd_date()) )
                    || ( reservation.getStart_date().isAfter(r.getStart_date()) &&  reservation.getEnd_date().isAfter(r.getEnd_date()) )
                    || ( reservation.getStart_date().isBefore(r.getStart_date()) &&  reservation.getEnd_date().isAfter(r.getEnd_date()) )
            ){
                System.out.println(String.format("Reservations cannot overlap. %s - %s",
                        reservation.getStart_date(),
                        reservation.getEnd_date()));
                duplicate = true;
                break;
            } else {
                duplicate = false;
            }
        }

        all.add(reservation);
        writeAll(all, reservation.getHost().getId());

        return reservation;
    }
    /*private String getHosts() {
        return Paths.get("./data/", "hosts.csv").toString();
    }*/

    private void writeAll(List<Reservation> reservations, String host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStart_date(),
                reservation.getEnd_date(),
                reservation.getGuest().getId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String id) {
        // 6 fields for reservation, need 6 result.set
        //x 5 fields in a reservation file, need 5 fields[] sets
        // id, start_date, end_date
        Reservation result = new Reservation();

        result.setId(fields[0]);
        result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        guest.setId(fields[3]);
        result.setGuest(guest);

        Host host = new Host();
        host.setId(id);
        result.setHost(host);
        result.setTotal(new BigDecimal(fields[4]));

        return result;
        // return null;
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




}
