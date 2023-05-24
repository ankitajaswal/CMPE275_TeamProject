package cmpe275.wiors.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.web.bind.annotation.RequestParam;

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

    @Column
    @JsonIgnore
    @XmlTransient
    private String password;

    @Column
    private String email;
    
    
    @Column(name = "is_google")
    private Boolean is_google;

    @Column(name = "is_verified")
    private Boolean is_verified;
    

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    
    public Boolean isIsGoogle() {
        return is_google;
    }

    public void setIsGoogle(Boolean is_google) {
        this.is_google = is_google;
    }

    public Boolean isIsVerified() {
        return is_verified;
    }

    public void setIsVerified(Boolean is_verified) {
        this.is_verified = is_verified;
    }
    
}
