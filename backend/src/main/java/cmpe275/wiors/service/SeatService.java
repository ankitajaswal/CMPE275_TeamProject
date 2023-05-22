package cmpe275.wiors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.repository.EmployeeRepository;
import cmpe275.wiors.repository.SeatRepository;

@Service
public class SeatService {
    
    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * Persist a seat
     * 
     * @param seat seat to persist
     * @return the persisted seat
     */
    public Seat createSeat( Seat seat) {
        return seatRepo.save(seat);
    }

    /**
     * 
     * @param seatOwner
     * @return list of seats owned by the specified employer
     */
    public List<Seat> getAllSeats(String ownerId) {
        return seatRepo.findByEmployerId(ownerId);
    }

    /**
     * 
     * @param seatOwner Employer which owns the seats
     * @param status seat status (is reserved)
     * @return list of seats with specified status for employer
     */
    public List<Seat> getAllSeatsByEmployerAndStatus(String ownerId, boolean status) {
        return seatRepo.findByEmployerIdAndReserved(ownerId, status);
    }


    /**
     * get a single seat
     * @param ownerId
     * @param seatId
     * @return a seat object by id
     */
    public Seat getSeatByEmployerAndId(String ownerId, Long seatId) {
        return seatRepo.findByEmployerIdAndId(ownerId, seatId);
    }


    /**
     * update the seat status to vacant
     * @param ownerId
     * @param seatId
     * @return single seat object info
     */
    public Seat updateSeatStatusVacant(String ownerId, Long seatId) {
        Seat s = seatRepo.findByEmployerIdAndId(ownerId, seatId);
        s.setResevrved(false);
        s.setReservee(null);
        return seatRepo.save(s);
    }


    /**
     * update seat status to reserved
     * Populate the reservee field
     * @param ownerId
     * @param seatId
     * @param employeeId
     * @param status
     * @return updated seat object for that seatId
     */
    public Seat updateSeatStatusReserved(String ownerId, Long seatId, Long employeeId) {
        Seat s = seatRepo.findByEmployerIdAndId(ownerId, seatId);
        s.setResevrved(true);
        Employee reservee = employeeRepo.findById(employeeId).get();
        s.setReservee(reservee);
        return seatRepo.save(s);
    }


    /**
     * delete seat by seat id
     * @param ownerId
     * @param seatId
     */
    public void deleteSeatById(String ownerId, Long seatId) {
        Seat s = seatRepo.findByEmployerIdAndId(ownerId, seatId);
        seatRepo.delete(s);
    }


    /**
     * Deletes all seats owned by the employer
     * @param ownerId
     */
    public void deleteAllSeats(String ownerId) {
        seatRepo.deleteAllByEmployerId(ownerId);
    }
}
