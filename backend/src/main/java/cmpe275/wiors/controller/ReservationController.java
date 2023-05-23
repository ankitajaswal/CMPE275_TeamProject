package cmpe275.wiors.controller;

import java.sql.Date;
import java.util.List;
import java.time.DayOfWeek;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.entity.Reservation;
import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.AttendanceRequirementService;
import cmpe275.wiors.service.ReservationService;

@RestController
@RequestMapping("/reservationService")
public class ReservationController {
    
    @Autowired 
    private ReservationService reservationService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttendanceRequirementService attendanceRequirementService;

    /**
     * Create reservation and presist it
     * @param employerId
     * @param employeeId
     * @param seatId
     * @param date
     * @param time
     * @return
     */
    @Transactional
    @RequestMapping(
        value = "/createReservation/{employerId}/{employeeId}/{date}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createReservation(
        @PathVariable String employerId,
        @PathVariable Long employeeId,
        @PathVariable Date date
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        Employee reservee = employeeService.getEmployee(employeeId);
        boolean checkReserved = reservationService.checkReservationByEmployee(employerId, employeeId, date);
        if (checkReserved) {
            return new ResponseEntity<>(HttpStatus.CONFLICT) ;
        }
        List<Seat> vacantSeats = reservationService.getVacantSeats(employerId, date);
        Seat reservedSeat = null;
        if (vacantSeats.size() > 0) {
            reservedSeat = vacantSeats.get(0);
        }
        else {
            List<Reservation> reservations = reservationService.getReservationsForDate(employerId, date);
            for (Reservation r: reservations) {
                int officePresence = 
                    reservationService.getOfficePresenceForWeekOf(employerId, r.getReservee().getId(), date);
                DayOfWeek day = date.toLocalDate().getDayOfWeek();
                boolean hasGtd = attendanceRequirementService.hasGtdOnDay(r.getReservee().getId(), day);
                if (officePresence > reservee.getMop() && !hasGtd) {
                    reservedSeat = r.getSeat();
                    reservationService.deleteReservationById(employerId, r.getId());
                    break;
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationService.createReservation(new Reservation(employer, reservee, reservedSeat, date));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Get all reservations in an employer's org
     * @param employerId
     * @return
     */
    @Transactional
    @RequestMapping(
        value = "/getAllReservations/{employerId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Reservation> getAllReservations(
        @PathVariable String employerId
    ) {
        return reservationService.getAllReservations(employerId);
    }


    /**
     * Get reservation by id in an employer's org
     * @param employerId
     * @param reservationId
     * @return
     */
    @Transactional
    @RequestMapping(
        value = "/getReservation/{employerId}/{reservationId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Reservation getReservationById(
        @PathVariable String employerId,
        @PathVariable Long reservationId
    ) {
        return reservationService.getReservationById(employerId, reservationId);
    }


    /**
     * Delete reservation by id in an employer's org
     * @param employerId
     * @param reservtionId
     */
    @Transactional
    @RequestMapping(
        value = "/deleteReservationById/{employerId}/{reservationId}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteReservationById(
        @PathVariable String employerId,
        @PathVariable Long reservationId
    ) {
        reservationService.deleteReservationById(employerId, reservationId);
    }
}
