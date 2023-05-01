package cmpe275.lab2.service;

import cmpe275.lab2.entity.Collaboration;
import cmpe275.lab2.entity.Employee;
import cmpe275.lab2.entity.EmployeeDto;
import cmpe275.lab2.repository.CollaborationRepository;
import cmpe275.lab2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Service wrapper class for Collaborations
 */
@Service
public class CollaborationService {

    @Autowired
    private CollaborationRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Persist a collaboration
     *
     * @param collaboration collaboration to persist
     */
    public void addCollaboration(Collaboration collaboration) {
        repository.save(collaboration);
    }

    /**
     * Delete a collaboration
     *
     * @param collaboration collaboration to delete
     */
    public void deleteCollaboration(Collaboration collaboration) {
        repository.deleteAllByEmployee1Id(collaboration.getEmployee1().getId());
        repository.deleteAllByEmployee2Id(collaboration.getEmployee2().getId());
    }

    /**
     * Delete all collaborations with the given employee
     *
     * @param id id of employee to delete collaborations for
     */
    public void deleteAllCollaborationsWithEmployee(long id) {
        repository.deleteAllByEmployee1Id(id);
        repository.deleteAllByEmployee2Id(id);
    }
    
    /**
     * Get all all employees that collaborate with the given employee
     *
     * @param id id of employee
     * @return List of EmployeeDto objects representing the given user's collaborations
     */
    public List<EmployeeDto> getCollaborators(long id) {
        List<Collaboration> c1 = repository.findByEmployee1Id(id);
        List<Collaboration> c2 = repository.findByEmployee2Id(id);
        Set<EmployeeDto> r = new HashSet<>();
        for (Collaboration c: c1) {
            r.add(employeeRepository.findById(c.getEmployee2().getId()).toDto());
        }
        for (Collaboration c: c2) {
            r.add(employeeRepository.findById(c.getEmployee1().getId()).toDto());
        }
        return new ArrayList<>(r);
    }
}
