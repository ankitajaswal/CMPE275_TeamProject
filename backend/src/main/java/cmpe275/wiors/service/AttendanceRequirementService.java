package cmpe275.wiors.service;

import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.entity.Employee;
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

    public void setEmployerAttendanceRequirement(AttendanceRequirement r) {
        AttendanceRequirement existing = repository.getEmployerRequirement(r.getEmployer().getId());
        if (existing != null) {
            r.setId(existing.getId());
        }
        repository.save(r);
    }

    public AttendanceRequirement getAttendanceRequirementForEmployer(String employerId) {
        return repository.getEmployerRequirement(employerId);
    }

    public int calculateMop(String employerId, Long managerId) {
        int mop = 0;
        AttendanceRequirement empReq = repository.getEmployerRequirement(employerId);
        if (empReq != null) {
            mop = empReq.getNumberOfDays();
        }
        AttendanceRequirement mgrReq = null;
        if (managerId != null) {
            repository.getAttendanceRequirementByCreator(managerId);
        }
        if (mgrReq != null && mgrReq.getNumberOfDays() > mop) {
            mop = mgrReq.getNumberOfDays();
        }
        return mop;
    }
    
}
