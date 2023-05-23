package cmpe275.wiors.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="employerid", referencedColumnName = "id", nullable = false)
    private Employer employer;

    @OneToOne
    @JoinColumn(name = "employeeid", referencedColumnName = "id", nullable = false)
    private Employee reservee;

    @OneToOne
    @JoinColumn(name = "seatid", referencedColumnName = "id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private Date date;


    //-------- Constructors --------

    // default constructor
    public Reservation() {}

    //  create reservation constructor
    public Reservation(Employer employer, Employee reservee, Seat seat, Date date) {
        this.setEmployer(employer);
        this.setReservee(reservee);
        this.setSeat(seat);
        this.setDate(date);
    }


    //-------- Setters and Getters --------

    // set employer
    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    // set reservee
    public void setReservee(Employee reservee) {
        this.reservee = reservee;
    }

    // set seat
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    // set date
    public void setDate(Date date) {
        this.date = date;
    }

    // get reservation id
    public long getId() {
        return this.id;
    }

    // get employer
    public Employer getEmployer() {
        return this.employer;
    }

    // get reservee
    public Employee getReservee() {
        return this.reservee;
    }

    // get seat 
    public Seat getSeat() {
        return this.seat;
    }

    // get date
    public Date getDate() {
        return this.date;
    }
}
