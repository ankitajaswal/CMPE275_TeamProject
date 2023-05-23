package cmpe275.wiors.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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


    //-------- Constructors --------

    // default constructor
    public Seat() {}

    // create new seat constructor
    public Seat(Employer owner) {
        this.setEmployer(owner);
    }


    //-------- Setters and Getters --------

    // set employer (seat owner)
    public void setEmployer(Employer seatOwner) {
        this.employer = seatOwner;
    }

    // get seatId
    public long getSeatId() {
        return this.id;
    }

    // get employer (seat owner)
    public Employer getEmployer() {
        return this.employer;
    }

}
