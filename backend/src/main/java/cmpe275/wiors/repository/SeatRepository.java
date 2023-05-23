package cmpe275.wiors.repository;

import cmpe275.wiors.entity.Seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    /**
     * Get all seats owned by an employer - regardless of seat status 
     * 
     * @param employer the employer which owns the seats
     * @return all seats owned by the specified employer
     */
    List<Seat> findByEmployerId( String employerId);


    /**
     * Get single seat info owned by an employer
     * @param employerId
     * @param id
     * @return single seat object owned by specified employer
     */
    Seat findByEmployerIdAndId(String employerId, Long id);


    /**
     * Delete all seats by employerId
     * @param employerId
     */
    void deleteAllByEmployerId(String employerId);


}
