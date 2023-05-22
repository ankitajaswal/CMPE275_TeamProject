package cmpe275.wiors.repository;

import cmpe275.wiors.entity.AttendanceRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Custom JpaRepository for persisting AttendanceRequirement objects
 */
public interface AttendanceRequirementRepository extends JpaRepository<AttendanceRequirement, Long> {

    @Query(
        value = "SELECT * FROM attendancerequirements WHERE employer = :id and is_employer_rule = true",
        nativeQuery = true
    )
    public AttendanceRequirement getEmployerRequirement(String id);

    @Query(
        value = "SELECT * FROM attendancerequirements WHERE creator = :id and is_get_together_day = :isgtd",
        nativeQuery = true
    )
    public AttendanceRequirement getAttendanceRequirementByCreator(long id, boolean isgtd);

    public AttendanceRequirement findById(long id);
}
