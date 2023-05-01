package cmpe275.lab2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.Column;

/**
 * This class represent employers. It persists as an entity in the datasource.
 */
@Entity
@Table(name = "employers")
public class Employer {

    @Id
    @Column
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Embedded
    private Address address;

    public Employer() {}

    /**
     * Converts Employer to EmployerDto object
     *
     * @return new EmployerDto object
     */
    public EmployerDto toDto() {
        EmployerDto dto = new EmployerDto();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        return dto;
    }

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

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
