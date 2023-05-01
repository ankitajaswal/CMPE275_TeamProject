package cmpe275.lab2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.Column;

/**
 * This class represents the shallow form of Employer. Used for embedding in Employer objects.
 */
public class EmployerDto {

    private String id;
    private String name;
    private String description;

    public EmployerDto() {}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
