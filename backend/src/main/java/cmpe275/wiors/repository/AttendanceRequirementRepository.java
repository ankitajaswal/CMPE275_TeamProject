package cmpe275.wiors.repository;

import cmpe275.wiors.entity.AttendanceRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Custom JpaRepository for persisting AttendanceRequirement objects
 */
public interface AttendanceRequirementRepository extends JpaRepository<AttendanceRequirement, Long> {

    @Query(
        value = "SELECT * FROM attendancerequirements WHERE employerId = :id and isEmployerRule = 'true'",
        nativeQuery = true
    )
    public AttendanceRequirement getEmployerRequirement(String id);
}
