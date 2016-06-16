package de.sofiworx.examples.jmm.business.user.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ulrich Cech
 */
@Embedded
public class Address implements Serializable {

    private static final long serialVersionUID = -6392568923602251625L;

    private String street;

    private String streetNo;

    private String zipCode;

    private String city;


    public Address() {
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(streetNo, address.streetNo) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, streetNo, zipCode, city);
    }
}
