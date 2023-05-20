package cmpe275.wiors.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.DayOfWeek;

/**
 * This class represent employers. It persists as an entity in the datasource.
 */
@Entity
@Table(name = "attendancerequirements")
public class AttendanceRequirement {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    @JoinColumn(name = "employer", referencedColumnName = "id", nullable = false)
    private Employer employer;

    @ManyToOne()
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = true)
    private Employee creator;

    @Column
    private boolean isGetTogetherDay;

    // in [1,5]
    @Column
    private int numberOfDays;

    // only applicable is isGetTogetherDay is true
    private DayOfWeek dayOfWeek;

    public AttendanceRequirement() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setIsGetTogetherDay(boolean isGetTogetherDay) {
        this.isGetTogetherDay = isGetTogetherDay;
    }

    public boolean isGetTogetherDay() {
        return isGetTogetherDay;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}

