package cmpe275.wiors.entity;

import javax.persistence.Transient;

public class User {

    private String email;
    private String employerId;
    private Long employeeId;
    private boolean isEmployer;

    @Transient
    private String token;

    public User() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setIsEmployer(boolean isEmployer) {
        this.isEmployer = isEmployer;
    }

    public boolean isEmployer() {
        return isEmployer;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
