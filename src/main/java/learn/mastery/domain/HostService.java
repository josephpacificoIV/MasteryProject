package learn.mastery.domain;

import learn.mastery.Model.Host;
import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findById(String id) {
        return repository.findAll().stream()
                .filter(i -> i.getId().startsWith(id))
                .collect(Collectors.toList());
    }

    public List<Host> findAllHosts() throws DataException {
        return repository.findAll();
    }





}
