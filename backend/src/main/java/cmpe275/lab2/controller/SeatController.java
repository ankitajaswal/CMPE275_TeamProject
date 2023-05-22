package cmpe275.lab2.controller;

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

import cmpe275.lab2.service.EmployerService;
import cmpe275.lab2.service.SeatService;
import cmpe275.lab2.entity.Seat;
import cmpe275.lab2.entity.Employer;

@RestController
@RequestMapping("/seatService")
public class SeatController {
    
    @Autowired 
    private SeatService seatService;
    @Autowired
    private EmployerService employerService;


    /**
     * creates a seat
     * @param employerid
     * @return Response entity in JSON form after seat is created
     */
    @Transactional
    @RequestMapping(
        value = "/createSeat/{employerid}", 
        method = RequestMethod.POST, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createSeat(
        @PathVariable String employerid
    ) {
        Boolean status = false;
        Employer owner = employerService.getEmployerById(employerid);
        seatService.createSeat(new Seat(owner, status));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * returns all seats owned by an employer
     * @param ownerId
     * @return list of seats
     */
    @Transactional
    @RequestMapping(
        value = "/getAllSeats/{ownerId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Seat> getAllSeats(
        @PathVariable String ownerId 
    ) {
        return seatService.getAllSeats(ownerId);
    }


    /**
     * returns all seats for employees by reserved status
     * @param ownerId
     * @param status
     * @return list of seats
     */
    @Transactional
    @RequestMapping(
        value = "/getAllSeatsByStatus/{ownerId}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Seat> getAllSeatsByStatus(
        @PathVariable String ownerId,
        @PathVariable Boolean status
    ) {
        return seatService.getAllSeatsByEmployerAndStatus(ownerId, status);
    }


    /**
     * updates the seat status to reflect vacant
     * @param ownerId
     * @param seatId
     * @return the updates Seat object
     */
    @Transactional
    @RequestMapping(
        value = "/updateSeatStatusVacant/{ownerId}/{seatId}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Seat updateSeatStatus(
        @PathVariable String ownerId,
        @PathVariable long seatId
    ) {
        return seatService.updateSeatStatusVacant(ownerId, seatId);
    }


    /**
     * updates the seat status to reserved
     * @param ownerId
     * @param seatId
     * @param employeeId
     * @return reserved seat object
     */
    @Transactional
    @RequestMapping(
        value = "/updateSeatStatusReserved/{ownerId}/{seatId}/{employeeId}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Seat updateSeatStatusReserved(
        @PathVariable String ownerId,
        @PathVariable Long seatId,
        @PathVariable Long employeeId
    ) {
        return seatService.updateSeatStatusReserved(ownerId, seatId, employeeId);
    }


    /**
     * Deletes a single seat by id
     * @param ownerId
     * @param seatId
     */
    @Transactional
    @RequestMapping(
        value = "/deleteSeatById/{ownerId}/{seatId}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteSeatById(
        @PathVariable String ownerId,
        @PathVariable Long seatId
    ) {
        seatService.deleteSeatById(ownerId, seatId);
    }


    /**
     * Delete all seats owned by an employer
     * @param ownerId
     */
    @Transactional
    @RequestMapping(
        value = "/deleteAllSeats/{ownerId}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteAllSeats(
        @PathVariable String ownerId
    ) {
        seatService.deleteAllSeats(ownerId);
    }
}
