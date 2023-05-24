package cmpe275.wiors.entity;

public class BulkReservation {
    
    private String email;
    private String startDate;
    private String endDate;

    // -------- Setter and Getters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public String getEmail() {
        return this.email;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

}
