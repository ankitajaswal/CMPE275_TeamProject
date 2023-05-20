package cmpe275.wiors.service;

import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.repository.AttendanceRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service wrapper class for AttendanceRequirements
 */
@Service
public class AttendanceRequirementService {
    @Autowired
    private AttendanceRequirementRepository repository;

    /**
     * Persists attendance requirement
     *
     * @param r attendance requirement to persist
     * @return persisted attendance requirement
     */
    public AttendanceRequirement createAttendanceRequirement(AttendanceRequirement r) {
        return repository.save(r);
    }

}
