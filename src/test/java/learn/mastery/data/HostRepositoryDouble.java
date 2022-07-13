package learn.mastery.data;

import learn.mastery.Model.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HostRepositoryDouble implements HostRepository{

    private final ArrayList<Host> hosts = new ArrayList<>();
    public HostRepositoryDouble() {
        hosts.add(new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "Test Last",
                "test1@gmail.com",
                "18008889090",
                "Test Address",
                "Test City",
                "NY",
                "Test postal",
                new BigDecimal(340),
                new BigDecimal(425)));
        hosts.add(new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8",
                "Test Last",
                "test2@gmail.com",
                "18008889090",
                "Test Address 2",
                "Test City 2",
                "NY",
                "Test postal 2",
                new BigDecimal(295),
                new BigDecimal("368.75")));

    }


    @Override
    public List<Host> findAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findById(String id) {
        return findAll().stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(i -> Objects.equals(i.getEmail(), email))
                .findFirst()
                .orElse(null);
    }

}
