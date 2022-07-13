package learn.mastery.data;

import learn.mastery.Model.Host;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository{

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;


    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Host findById(String id) {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLast_name(),
                host.getEmail(),
                host.getPhone(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostal_code(),
                host.getStandard_rate(),
                host.getWeekend_rate());
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(fields[0]);
        result.setLast_name(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostal_code(fields[7]);
        result.setStandard_rate(Double.parseDouble(fields[8]));
        result.setWeekend_rate(Double.parseDouble(fields[9]));
        return result;
    }

    protected void writeAll(List<Host> hosts) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if (hosts == null) {
                return;
            }

            for (Host host : hosts) {
                writer.println(serialize(host));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }


}
