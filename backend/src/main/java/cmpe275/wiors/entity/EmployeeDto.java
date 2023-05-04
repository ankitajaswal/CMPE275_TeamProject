package cmpe275.wiors.entity;

/**
 * This class represents the shallow form of Employee. Used for embedding in Employee objects.
 */
public class EmployeeDto {

    private long id;
    private String employerId;
    private String name;
    private String email;
    private String title;
 
    public EmployeeDto() {}

    /**
     * Override default equals to just check ID
     *
     * @param Object thing to check if it is equals
     * @return true if objects have the same ID
     */
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeDto)) return false;
        EmployeeDto e = (EmployeeDto)o;

        return id == e.getId();
    }

    /**
     * Override default hashCode method to force the use of equals in hashtables
     *
     * @return int 0, dummy value
     */
    public int hashCode() {
        return 0;
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

}
