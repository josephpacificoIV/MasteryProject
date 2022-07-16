package learn.mastery.domain;

import learn.mastery.Model.Host;
import learn.mastery.data.DataException;
import learn.mastery.data.HostRepositoryDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setup() {
        HostRepositoryDouble repository = new HostRepositoryDouble();
        service = new HostService(repository);
    }

    @Test
    void shouldFindAllHosts() throws DataException {
        List<Host> hosts = service.findAllHosts();
        assertEquals(2, hosts.size());
        System.out.println(hosts.get(1).getEmail());
    }
    @Test
    void shouldFindHostByEmail() throws DataException {
        Host host = service.findByEmail("test2@gmail.com");
        assertEquals("test2@gmail.com", host.getEmail());
    }

}