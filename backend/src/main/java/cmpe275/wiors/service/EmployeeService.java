package cmpe275.wiors.service;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.EmployeeDto;
import cmpe275.wiors.mail.MailSender;
import cmpe275.wiors.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

/**
 * Service wrapper class for Employees
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    /**
     * Persist an employee
     *
     * @param employee employee to persist
     * @return the persisted employee
     */
    public Employee createEmployee(Employee employee) {    	
        return repository.save(employee);
    }

    /**
     * Update an employee
     *
     * @param employee employee to update
     * @return the updated employee
     */
    public Employee updateEmployee(Employee employee) {
        // repository.deleteById(employee.getId());    	
    	if (employee.getManager() != null)
    		employee.setManagerId(employee.getManager().getId());
        return repository.save(employee);
    }

    /**
     * Get an employee
     *
     * @param id id of employee to get
     * @return the employee
     */
    public Employee getEmployee(long id) {
        return repository.findById(id);
    }

    /**
     * Get an employee in Dto form
     *
     * @param id id of employee to get
     * @return the employee in dto form
     */
    public EmployeeDto getEmployeeDto(long id) {
        Employee e = repository.findById(id);
        return (e != null) ? e.toDto() : null;
    }

    /**
     * Get all employees that report to the given employee
     *
     * @param the manager of the id
     * @return list of employees
     */
    public List<EmployeeDto> getReports(long id) {
        List<EmployeeDto> r = new ArrayList<>();
        for (Employee e: repository.findAllByManagerId(id)) {
            r.add(e.toDto());
        }
        return r;
    }

    /**
     * Deleted employee
     *
     * @param id id of employee to delete
     */
    public void deleteEmployee(long id) {
        repository.deleteById(id);
        return;
    }

    /**
     * Checks if the given id has reports
     *
     * @param id id of employee
     * @return true if the given employee has reports
     */
    public boolean hasReports(long id) {
        return repository.reportCount(id) > 0;
    }

    /**
     * Checks if the given employer has employees
     *
     * @param employerId id of employer
     * @return true if the given employer has employees
     */
    public boolean employerHasEmployees(String employerId) {
        return repository.employeeCount(employerId) > 0;
    }

    public Employee getEmployeeByEmail(String email) {
        return repository.findByEmail(email);
    }

}
