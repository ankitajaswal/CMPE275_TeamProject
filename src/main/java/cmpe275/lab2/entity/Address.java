package cmpe275.lab2.entity;

import javax.persistence.Embeddable;

/*
 * This class includes location information. Embedded in both employees and employers.
 */
@Embeddable
public class Address {

	private String street;
    private String city;
    private String state;
    private String zip;
	
    public Address() {}

    public Address(String street, String city, String state, String zip) {        
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }
}
