package cmpe275.lab2.service;

import cmpe275.lab2.entity.Employer;
import cmpe275.lab2.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service wrapper class for Employers
 */
@Service
public class EmployerService {
    @Autowired
    private EmployerRepository repository;

    /**
     * Persists employer
     *
     * @param e employer to persist
     * @return persisted employer
     */
    public Employer createEmployer(Employer e) {
        return repository.save(e);
    }

    /**
     * Checks if employer with the given name exists
     *
     * @param name name to check
     * @return true if employer with name exists
     */
    public boolean employerWithNameExists(String name) {
        return repository.findByName(name) != null;
    }

    /**
     * Gets an employer
     *
     * @param id id of employer
     * @return employer
     */
    public Employer getEmployerById(String id) {
        Optional<Employer> optionalEmployer = repository.findById(id);
        return optionalEmployer.orElse(null);
    }

    /**
     * Given a name, creates an ID. If name has multiple words an acronym is made out of the name.
     *  if the name has one word, the ID is the name. If an ID is duplicate, append a number.
     *
     *  @param name name to create ID from
     *  @return new ID
     */
    public String createIdFromName(String name) {
        String id = "";
        String[] strArr = name.split(" ");
        if (strArr.length > 1) {
            for (String s: strArr) {
                id += s.charAt(0);
            }
        } else {
            id = name;
        }
        int c = 0;
        id = id.toUpperCase();
        while (getEmployerById(id) != null) { // while ID exists in DB
            c++;
        }
        if (c > 0) {
            id += c;
        }
        return id;
    }
    
    /**
     * Updates an employer
     *
     * @param id id of employer
     * @return employer
     */
    public Employer updateEmployer(Employer e) {
        return repository.save(e);
    }

    /**
     * Deletes an employer
     *
     * @param id id of employer
     */
    public void deleteEmployer(String id) {
        repository.deleteById(id);
        return;
    }
}
