package cmpe275.wiors.repository;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Custom JpaRepository for persisting Employee objects
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    /**
     * Get Employee with name
     *
     * @param name the name of employee we are fetching
     * @return employee with the given name
     */
    Employee findByName(String name);

    /**
     * Get Employee with ID
     *
     * @param ID the name of employee we are fetching
     * @return employee with the given ID
     */
    Employee findById(long id);

    /**
     * Get all employees with given manager
     *
     * @param id id of manager
     * @return List of employees with the given manager
     */
    List<Employee> findAllByManagerId(long id);

    /**
     * Get the number of reports for the given employee
     *
     * @param id id of potential manager
     * @return the number of reports for this employee
     */
    @Query(
        value = "SELECT COUNT(manager_id) FROM employees WHERE manager_id = :id",
        nativeQuery = true
    )
    long reportCount(@Param("id") long id);

    /**
     * Get the number of employees that belong to the given employer
     *
     * @param employerId id of employer
     * @return the number of employees at this employer
     */
    @Query(
        value = "SELECT COUNT(id) FROM employees WHERE employer_id = :id",
        nativeQuery = true
    )
    long employeeCount(@Param("id") String employerId);
}

