package learn.mastery.data;

import learn.mastery.Model.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);
}
