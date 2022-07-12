package learn.mastery.domain;

import learn.mastery.data.HostRepository;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }


}
