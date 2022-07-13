package learn.mastery.domain;

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

    /*@Test
    void shouldFindTwoPublicMemories() throws DataException {
        List<Host> hosts = service.findPublicMemories();
        assertEquals(2, memories.size());
    }*/

}