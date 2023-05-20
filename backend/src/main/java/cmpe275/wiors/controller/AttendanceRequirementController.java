package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.Address;
import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.AttendanceRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.List;

/**
 * The main rest controller for attendance requirements
 */
@RestController
public class AttendanceRequirementController {

    @Autowired
    private AttendanceRequirementService service;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;

    @Transactional
	@RequestMapping(
        value = "/requirement",
        method = RequestMethod.POST,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> createRequirement(
        @RequestParam(value = "employer", required = true) String employer,
        @RequestParam(value = "creator", required = true) Long creator,
        @RequestParam(value = "isGetTogetherDay", required = false, defaultValue = "false") boolean isGetTogetherDay,
        @RequestParam(value = "numberOfDays", required = true) Integer numberOfDays,
        @RequestParam(value = "dayOfWeek", required = false) Integer dayOfWeek,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        AttendanceRequirement req = new AttendanceRequirement();
        Employer employerObj = employerService.getEmployerById(employer);
        if (!validStr(employer) || employerObj == null) {
            return new ResponseEntity<>("Invalid employer ID", HttpStatus.BAD_REQUEST);
        }
        req.setEmployer(employerService.getEmployerById(employer));
        Employee creatorEmp = employeeService.getEmployee(creator);
        if (creator == null || creatorEmp == null) {
            return new ResponseEntity<>("Invalid employee ID for creator", HttpStatus.NOT_FOUND);
        }
        req.setCreator(creatorEmp);
        req.setIsGetTogetherDay(isGetTogetherDay);
        req.setNumberOfDays(numberOfDays);
        req.setIsEmployerRule(false);
        if (isGetTogetherDay && dayOfWeek == null) {
            return new ResponseEntity<>("If isGetTogetherDay is true, dayOfWeek must be an Integer in [1,5]",
                    HttpStatus.BAD_REQUEST);
        } else if (dayOfWeek != null) { // could be null if isGetTogetherDay is false
            req.setDayOfWeek(DayOfWeek.of(dayOfWeek));
        }

        if (isGetTogetherDay && numberOfDays != 1) {
            return new ResponseEntity<>("If isGetTogetherDay is true, numberOfDays must equal 1",
                    HttpStatus.BAD_REQUEST);
        }
        if (isGetTogetherDay && (dayOfWeek < 1 || dayOfWeek > 5)) {
            return new ResponseEntity<>("dayOfWeek must be in [1,5]", HttpStatus.BAD_REQUEST);
        }

        AttendanceRequirement created = service.createAttendanceRequirement(req);

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(created.toDto());
	}

    @Transactional
	@RequestMapping(
        value = "/requirement/employer/{employerId}",
        method = RequestMethod.POST
    )
	public ResponseEntity<?> setEmployerRequirement(
        @PathVariable String employerId,
        @RequestParam(value = "numberOfDays", required = true) Integer numberOfDays
    ) {
        AttendanceRequirement req = new AttendanceRequirement();
        Employer employer = employerService.getEmployerById(employerId);
        if (!validStr(employerId) || employer == null) {
            return new ResponseEntity<>("Invalid employer ID", HttpStatus.NOT_FOUND);
        }

        req.setEmployer(employer);
        req.setNumberOfDays(numberOfDays);
        req.setIsEmployerRule(true);

        service.setEmployerAttendanceRequirement(req);

        return new ResponseEntity<>(HttpStatus.OK);
	}

    @Transactional
    @RequestMapping(
        value = "/requirement/employer/{employerId}",
        method = RequestMethod.GET
    )
    public ResponseEntity<?> getEmployerAttendanceRequirement(@PathVariable String employerId) {
        if (employerService.getEmployerById(employerId) == null) {
            return new ResponseEntity<>("Invalid employer ID", HttpStatus.NOT_FOUND);
        }
        AttendanceRequirement r = service.getAttendanceRequirementForEmployer(employerId);
        int days = 0;
        if (r != null) {
            days = r.getNumberOfDays();
        }
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
        value = "/requirement/employee/{employeeId}",
        method = RequestMethod.GET
    )
    public ResponseEntity<?> getEmployeeAttendanceRequirement(@PathVariable long employeeId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
