package cmpe275.wiors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.wiors.entity.Reservation;
import cmpe275.wiors.repository.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepo;

    /**
     * persist a reservation
     * @param reservation
     * @return reservation object
     */
    public Reservation createReservation(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    /**
     * Get all reservations in an org
     * @param employerId
     * @return list of all reservations in an org
     */
    public List<Reservation> getAllReservations(String employerId) {
        return reservationRepo.findByEmployerId(employerId);
    }

    /**
     * Get a reservation by Id
     * @param employerId
     * @param id
     * @return reservation object
     */
    public Reservation getReservationById(String employerId, Long id) {
        return reservationRepo.findByEmployerIdAndId(employerId, id);
    }

    /**
     * deletes a reservation by id in an org
     * @param employerId
     * @param id
     */
    public void deleteReservationById(String employerId, Long id) {
        Reservation r = reservationRepo.findByEmployerIdAndId(employerId, id);
        reservationRepo.delete(r);
    }

    /**
     * delete all reservations in an org
     * @param employerId
     */
    public void deleteAllReservations(String employerId) {
        reservationRepo.deleteAllByEmployerId(employerId);
    }
}
