package cmpe275.wiors.repository;

import cmpe275.wiors.entity.Collaboration;
import cmpe275.wiors.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Custom JpaRepository for persisting Collaboration objects
 */
public interface CollaborationRepository extends JpaRepository<Collaboration,String> {
        
        /**
         * Gets Collaborations where employeeId1 is equal to id
         *
         * @param id id to check employeeId1 against
         * @return list of Collaboration objects 
         */
        List<Collaboration> findByEmployee1Id(long id);

        /**
         * Gets Collaborations where employeeId2 is equal to id
         *
         * @param id id to check employeeId1 against
         * @return list of Collaboration objects 
         */
        List<Collaboration> findByEmployee2Id(long id);

        /**
         * Deletes all Collaborations with employeeId1 set to id
         *
         * @param id employeeId1's id
         */
        void deleteAllByEmployee1Id(long id);

        /**
         * Deletes all Collaborations with employeeId2 set to id
         *
         * @param id employeeId2's id
         */
        void deleteAllByEmployee2Id(long id);
}
