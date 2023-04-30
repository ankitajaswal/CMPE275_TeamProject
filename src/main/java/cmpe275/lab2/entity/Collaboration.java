package cmpe275.lab2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import org.springframework.context.annotation.DependsOn;
import java.util.List;

/**
 * This class represents collaborations between different employees. It persists as an entity in the datasource.
 */
@Entity
@Table(name = "collaboration")
public class Collaboration {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    @JoinColumn(name = "employer1_id", referencedColumnName = "id", nullable = false)
    private Employer employer1;

    @ManyToOne()
    @JoinColumn(name = "employee1_id", referencedColumnName = "id", nullable = false)
    private Employee employee1;

    @ManyToOne()
    @JoinColumn(name = "employer2_id", referencedColumnName = "id", nullable = false)
    private Employer employer2;

    @ManyToOne()
    @JoinColumn(name = "employee2_id", referencedColumnName = "id", nullable = false)
    private Employee employee2;

    public Collaboration () {}

    public Collaboration (Employer employer1, Employee employee1, Employer employer2, Employee employee2) {
        this.employer1 = employer1;
        this.employee1 = employee1;
        this.employer2 = employer2;
        this.employee2 = employee2;
    }

    private void setId(long id) {
        this.id = id;
    }

    private long getId() {
        return id;
    }

    public void setEmployer1(Employer employer1) {
        this.employer1 = employer1;
    }

    public Employer getEmployer1() {
        return employer1;
    }

    public void setEmployee1(Employee employee1) {
        this.employee1 = employee1;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public void setEmployer2(Employer employer2) {
        this.employer2 = employer2;
    }

    public Employer getEmployer2() {
        return employer2;
    }

    public void setEmployee2(Employee employee2) {
        this.employee2 = employee2;
    }

    public Employee getEmployee2() {
        return employee2;
    }
}

