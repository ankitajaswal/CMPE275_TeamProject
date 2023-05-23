package cmpe275.wiors.controller;

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

import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.SeatService;
import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.entity.Employer;

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
        Employer owner = employerService.getEmployerById(employerid);
        seatService.createSeat(new Seat(owner));
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


    @Transactional
    @RequestMapping(
        value = "/getSeatById/{ownerId}/{seatId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Seat getSeatById(
        @PathVariable String ownerId,
        @PathVariable Long seatId
    ) {
        return seatService.getSeatByEmployerAndId(ownerId, seatId);
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
