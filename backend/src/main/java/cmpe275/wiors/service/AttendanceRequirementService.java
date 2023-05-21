package cmpe275.wiors.service;

import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.repository.AttendanceRequirementRepository;
import cmpe275.wiors.repository.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Persists attendance requirement
     *
     * @param r attendance requirement to persist
     * @return persisted attendance requirement
     */
    public AttendanceRequirement createAttendanceRequirement(AttendanceRequirement r) {
        System.out.println(r.getCreator().getId());
        AttendanceRequirement t = repository.getAttendanceRequirementByCreator(r.getCreator().getId());

        AttendanceRequirement used, existing = repository.findById(t.getId());
        if (existing != null) {
            existing.setIsGetTogetherDay(r.isGetTogetherDay());
            existing.setNumberOfDays(r.getNumberOfDays());
            existing.setIsEmployerRule(r.isEmployerRule());
            existing.setDayOfWeek(r.getDayOfWeek());
            used = existing;
        } else {
            used = r;
        }
        return repository.save(used);
    }

    public void setEmployerAttendanceRequirement(AttendanceRequirement r) {
        AttendanceRequirement existing = repository.getEmployerRequirement(r.getEmployer().getId());
        if (existing != null) {
            r.setId(existing.getId());
        }
        repository.save(r);
    }

    public AttendanceRequirement getAttendanceRequirementByCreator(long id) {
        return repository.getAttendanceRequirementByCreator(id);
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
    
    public void adjustMops(Long employeeId, int mop) {
        if (employeeId == null) return;
        Employee e = employeeRepository.findById(employeeId).orElse(null);
        if (e == null) return;
        if (mop > e.getMop()) {
            e.setMop(mop);
            employeeRepository.save(e);
        }
        for (Employee te: employeeRepository.findAllByManagerId(employeeId)) {
            adjustMops(te.getId(), mop);
        }
    }
}
