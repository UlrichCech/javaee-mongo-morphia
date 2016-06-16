package de.sofiworx.examples.jmm.business.user.entity;

import de.sofiworx.examples.jmm.platform.persistence.PersistentEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static org.mongodb.morphia.utils.IndexType.ASC;

/**
 * @author Ulrich Cech
 */
@Entity(value = "users", noClassnameStored = true)
@Indexes({
        @Index(fields = @Field(value="firstname", type = ASC), options = @IndexOptions(unique = true))
})
public class User extends PersistentEntity {

    private static final long serialVersionUID = -1790104482978817721L;

    private String firstname;

    private String lastname;

    private Date birthdate;

    private String email;

    @Embedded
    private Address address = new Address();


    public User() {
    }


    public String getFullName() {
        return this.lastname + ", " + this.firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(birthdate, user.birthdate) &&
                Objects.equals(email, user.email) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstname, lastname, birthdate, email, address);
    }
}
