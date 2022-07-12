package learn.mastery.domain;

import learn.mastery.data.GuestRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;


    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }


}
