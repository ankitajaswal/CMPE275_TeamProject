package cmpe275.wiors.repository;

import cmpe275.wiors.entity.AttendanceRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Custom JpaRepository for persisting AttendanceRequirement objects
 */
public interface AttendanceRequirementRepository extends JpaRepository<AttendanceRequirement, Long> {
}
