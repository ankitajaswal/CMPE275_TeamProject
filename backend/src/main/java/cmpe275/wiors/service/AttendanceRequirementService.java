package cmpe275.wiors.service;

import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.repository.AttendanceRequirementRepository;
import cmpe275.wiors.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.DayOfWeek;
import java.util.ArrayList;
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
        AttendanceRequirement t = repository.getAttendanceRequirementByCreator(r.getCreator().getId(), r.isGetTogetherDay());

        AttendanceRequirement used, existing = null;
        if (t != null) {
            existing = repository.findById(t.getId());
        }
        if (existing != null) {
            existing.setIsGetTogetherDay(r.isGetTogetherDay());
            existing.setNumberOfDays(r.getNumberOfDays());
            existing.setIsEmployerRule(r.isEmployerRule());
            existing.setDayOfWeek(r.getDayOfWeek());
            used = existing;
            repository.delete(used);
            repository.flush();
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

    public AttendanceRequirement getAttendanceRequirementByCreator(long id, boolean isGtd) {
        return repository.getAttendanceRequirementByCreator(id, isGtd);
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
            mgrReq = repository.getAttendanceRequirementByCreator(managerId, false);
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

    public List<DayOfWeek> getEmployeeGtds(Employee e) {
        List<DayOfWeek> reqs = new ArrayList<>();
        if (e.getManagerId() != null) {
            AttendanceRequirement mgrGtd = repository.getAttendanceRequirementByCreator(e.getManagerId(), true);
            if (mgrGtd != null) {
                reqs.add(mgrGtd.getDayOfWeek());
            }
        }
        AttendanceRequirement eGtd = repository.getAttendanceRequirementByCreator(e.getId(), true);
        if (eGtd != null) {
            reqs.add(eGtd.getDayOfWeek());
        }
        return reqs;
    }
    
    public void updateMops(String employerId, int mop) {
        for (Employee e: employeeRepository.getTopLevelEmployees(employerId)) {
            adjustMops(e.getId(), mop);
        }
    }
}
