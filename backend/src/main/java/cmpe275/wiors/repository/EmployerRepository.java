package cmpe275.wiors.repository;

import cmpe275.wiors.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Custom JpaRepository for persisting Employee objects
 */
public interface EmployerRepository extends JpaRepository<Employer,String> {

    /**
     * Get Employer with name
     *
     * @param name the name of employer we are fetching
     * @return employer with the given name
     */
    Employer findByName(String name);

    Employer findByEmail(String email);
}
