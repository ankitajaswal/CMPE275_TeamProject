package cmpe275.wiors.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Transient;
import org.springframework.context.annotation.DependsOn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class represent employees. It persists as an entity in the datasource.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String employerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_google")
    private Boolean is_google;

    @Column(name = "is_verified")
    private Boolean is_verified;    
    
    @Column
    private String email;

    @Column
    private String title;

    @Embedded
    private Address address;

    @Column
    @JsonIgnore
    @XmlTransient
    private Long managerId;

    @Column
    @JsonIgnore
    @XmlTransient
    private String password;

    @Column
    private int mop;

    @Transient
    private EmployerDto employer;

    @Transient
    private EmployeeDto manager;
 
    @Transient
    private List<EmployeeDto> reports;

    @Transient
    private List<EmployeeDto> collaborators;

    public Employee() {}

    /**
     * Converts Employee to EmployeeDto object
     *
     * @return new EmployeeDto object
     */
    public EmployeeDto toDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(id);
        dto.setEmployerId(employerId);
        dto.setName(name);
        dto.setEmail(email);
        dto.setTitle(title);
        return dto;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setManager(EmployeeDto manager) {
        this.manager = manager;
    }

    public EmployeeDto getManager() {
        return manager;
    }

    public void setReports(List<EmployeeDto> reports) {
        this.reports = reports;
    }

    public List<EmployeeDto> getReports() {
        return reports;
    }

    public void setCollaborators(List<EmployeeDto> collaborators) {
        this.collaborators = collaborators;
    }

    public List<EmployeeDto> getCollaborators() {
        return collaborators;
    }

    public void setMop(int mop) {
        this.mop = mop;
    }

    public int getMop() {
        return mop;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public Boolean getIsVerified() {
        return this.is_verified;
    }

    public void setIsVerified(Boolean is_verified) {
    	this.is_verified = is_verified;
    }

    public Boolean getIsGoogle() {
        return this.is_google;
    }

    public void setIsGoogle(Boolean is_google) {
        this.is_google = is_google;
    }
    
}
