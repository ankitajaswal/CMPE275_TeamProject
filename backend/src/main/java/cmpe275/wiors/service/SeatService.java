package cmpe275.wiors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.repository.SeatRepository;

@Service
public class SeatService {
    
    @Autowired
    private SeatRepository seatRepo;


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
     * get a single seat
     * @param ownerId
     * @param seatId
     * @return a seat object by id
     */
    public Seat getSeatByEmployerAndId(String ownerId, Long seatId) {
        return seatRepo.findByEmployerIdAndId(ownerId, seatId);
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
