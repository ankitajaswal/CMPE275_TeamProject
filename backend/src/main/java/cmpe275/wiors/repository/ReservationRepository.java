package cmpe275.wiors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpe275.wiors.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * get all reservation in an org
     * @param employerId
     * @return list of reservations in an org
     */
    List<Reservation> findByEmployerId(String employerId);

    /**
     * get a reservation in an org by reservation id
     * @param employerId
     * @param id
     * @return specified reservation object
     */
    Reservation findByEmployerIdAndId(String employerId, Long id);

    /**
     * Delete all reservations in an employer's org
     * @param employerId
     */
    void deleteAllByEmployerId(String employerId);
    
}
