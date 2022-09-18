package task_manager.application.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CONTACTINFO")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "contactInfo", cascade = CascadeType.ALL)
    private final List<Address> addressList = new ArrayList<>();

    @OneToOne(mappedBy = "contactInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AppUser appUser;

    public ContactInfo() {
    }

    public ContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }


    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void addAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("address null");

        addressList.add(address);
        address.setContactInfo(this);
    }

    public void removeAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("address null");

        addressList.remove(address);
        address.setContactInfo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInfo that = (ContactInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone);
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
