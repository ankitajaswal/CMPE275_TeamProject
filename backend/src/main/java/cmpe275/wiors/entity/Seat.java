package cmpe275.wiors.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "seats")
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="employerid", referencedColumnName = "id", nullable = false)
    private Employer employer;

    @OneToOne
    @JoinColumn(name="employeeid", referencedColumnName = "id", nullable = true)
    private Employee reservee;

    @Column(nullable = false)
    private boolean reserved;


    //-------- Constructors --------

    // default constructor
    public Seat() {}

    // create new seat constructor
    public Seat(Employer owner, boolean status) {
        this.setEmployer(owner);
        this.setResevrved(status);
    }


    //-------- Setters and Getters --------

    // set employer (seat owner)
    public void setEmployer(Employer seatOwner) {
        this.employer = seatOwner;
    }

    // set reservee 
    public void setReservee(Employee r) {
        this.reservee = r;
    }

    // set status
    public void setResevrved(boolean status) {
        this.reserved = status;
    }

    // get seatId
    public long getSeatId() {
        return this.id;
    }

    // get employer (seat owner)
    public Employer getEmployer() {
        return this.employer;
    }

    // get reservee
    public Employee getReservee() {
        return this.reservee;
    }

    // get seat status
    public boolean isReserved() {
        return this.reserved;
    }

}
