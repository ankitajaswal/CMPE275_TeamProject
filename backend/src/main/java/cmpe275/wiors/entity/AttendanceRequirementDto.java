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
 * This class represent the shallow form of attendance requirements. 
 */
public class AttendanceRequirementDto {

    private long id;
    private String employer;
    private long creator;
    private boolean isGetTogetherDay;
    private int numberOfDays;
    private DayOfWeek dayOfWeek;

    public AttendanceRequirementDto() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployer() {
        return employer;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getCreator() {
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

