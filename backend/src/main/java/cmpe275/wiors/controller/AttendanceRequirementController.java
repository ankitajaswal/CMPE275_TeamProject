package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.EmployeeDto;
import cmpe275.wiors.entity.Address;
import cmpe275.wiors.entity.Reservation;
import cmpe275.wiors.entity.AttendanceRequirement;
import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.ReservationService;
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
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.sql.Date;
import java.util.ArrayList;
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

    @Autowired 
    private ReservationService reservationService;

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
        
        int mop = employeeService.getEmployee(creator).getMop();
        if (mop > numberOfDays && !isGetTogetherDay) {
            return new ResponseEntity<>("numberOfDays must be greater than or equal to creator's MOP",
                HttpStatus.BAD_REQUEST);
        }
        AttendanceRequirement created = service.createAttendanceRequirement(req);
        service.adjustMops(creator, numberOfDays);

        try {
            if (isGetTogetherDay) {
                ArrayList<EmployeeDto> emps = new ArrayList<>();
                try {
                    emps.addAll(creatorEmp.getReports());
                }catch(Exception e) {}
                emps.add(creatorEmp.toDto());
                for (EmployeeDto e: emps) {
                    for (Date date: getDatesForDayOfWeek(DayOfWeek.of(dayOfWeek))) {
                        Reservation r = new Reservation();
                        boolean checkReserved = reservationService.checkReservationByEmployee(employer, creator, date);
                        if (checkReserved) {
                            continue;
                        }
                        List<Seat> vacantSeats = reservationService.getVacantSeats(employer, date);
                        Seat reservedSeat = null;
                        if (vacantSeats.size() > 0) {
                            reservedSeat = vacantSeats.get(0);
                        }
                        else {
                            List<Reservation> reservations = reservationService.getReservationsForDate(employer, date);
                            for (Reservation rr: reservations) {
                                int officePresence = 
                                    reservationService.getOfficePresenceForWeekOf(employer, rr.getReservee().getId(), date);
                                DayOfWeek day = date.toLocalDate().getDayOfWeek();
                                boolean hasGtd = service.hasGtdOnDay(r.getReservee().getId(), day);
                                if (officePresence > employeeService.getEmployee(e.getId()).getMop() && !hasGtd) {
                                    reservedSeat = r.getSeat();
                                    reservationService.deleteReservationById(employer, rr.getId());
                                    break;
                                }
                            }
                        }
                        reservationService.createReservation(new Reservation(employerObj, employeeService.getEmployee(e.getId()), reservedSeat, date));
                    }
                }
            }
        } catch (Exception e) {}

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
        service.updateMops(employerId, numberOfDays);

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
        return new ResponseEntity<>("{\"days\":" + days + "}", HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
        value = "/requirement/employee/{employeeId}",
        method = RequestMethod.GET
    )
    public ResponseEntity<?> getEmployeeAttendanceRequirement(@PathVariable long employeeId) {
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp == null) {
            return new ResponseEntity<>("Invalid employee ID", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("{\"days\":" + employeeService.getEmployee(employeeId).getMop() + "}", HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
        value = "/requirement/employee/{employeeId}/gtd",
        method = RequestMethod.GET
    )
    public ResponseEntity<?> getEmployeeGtds(@PathVariable long employeeId) {
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp == null) {
            return new ResponseEntity<>("Invalid employee ID", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getEmployeeGtds(emp), HttpStatus.OK);
    }

    // @Transactional
    // @RequestMapping(
    //     value = "/attendance/{employerId}",
    //     method = RequestMethod.GET
    // )
    // public ResponseEntity<?> getAttendanceReportingStats(
    //     @PathVariable String employerId,
    //     @RequestParam(value = "managerId", required = false) String managerId,
    //     @RequestParam(value = "date", required = true) String date
    // ) {
    // }

    public static List<Date> getDatesForDayOfWeek(DayOfWeek dayOfWeek) {
        List<Date> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 10; i++) {
            LocalDate nextDate = currentDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            dates.add(Date.valueOf(nextDate));
            currentDate = nextDate.plusWeeks(1);
        }

        return dates;
    }

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
