package cmpe275.wiors.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.Reservation;
import cmpe275.wiors.entity.Seat;
import cmpe275.wiors.repository.ReservationRepository;
import cmpe275.wiors.repository.SeatRepository;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepo;
    @Autowired 
    private SeatRepository seatRepo;

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


    /**
     * gets all seats not reserved on a date 
     * @param employerId
     * @param date
     * @return list of vacant seats 
     */
    public List<Seat> getVacantSeats( String employerId, Date date) {
        List<Seat> seats = seatRepo.findByEmployerId(employerId);
        List<Reservation> reservations = reservationRepo.findByEmployerIdAndDate(employerId, date);
        for (Reservation reservation : reservations) {
            Seat s = reservation.getSeat();
            seats.remove(s);            
        }
        return seats;
    }

    
    /**
     * Check to see if employee has already reserved a seat for this day
     * @param employerId
     * @param employeeId
     * @param date
     * @return
     */
    public boolean checkReservationByEmployee(String employerId, Long employeeId, Date date ) {
        List<Reservation> reservations = reservationRepo.findByEmployerIdAndDate(employerId, date);
        for (Reservation reservation : reservations) {
            Employee e = reservation.getReservee();
            Long eId = e.getId();
            if (eId == employeeId) {
                return true;
            }
        }
        return false;
    }

}
