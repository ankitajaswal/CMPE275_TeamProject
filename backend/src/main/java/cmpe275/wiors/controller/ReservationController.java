package cmpe275.wiors.controller;

import java.sql.Date;
import java.util.List;

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
import cmpe275.wiors.service.ReservationService;
import cmpe275.wiors.service.SeatService;

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
    private SeatService seatService;


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
        value = "/createReservation/{employerId}/{employeeId}/{seatId}/{date}/{time}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createReservation(
        @PathVariable String employerId,
        @PathVariable Long employeeId,
        @PathVariable Long seatId,
        @PathVariable Date date,
        @PathVariable String time
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        Employee reservee = employeeService.getEmployee(employeeId);
        Seat seat = seatService.getSeatByEmployerAndId(employerId, seatId);
        
        reservationService.createReservation(new Reservation(employer, reservee, seat, date, time));
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
        return getAllReservations(employerId);
    }


    /**
     *  Get reservation by id in an employer's org
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
        return getReservationById(employerId, reservationId);
    }


    @Transactional
    @RequestMapping(
        value = "/deleteReservationById/{employerId}/{reservationId}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteReservationById(
        @PathVariable String employerId,
        @PathVariable Long reservtionId
    ) {
        reservationService.deleteReservationById(employerId, reservtionId);
    }
}
