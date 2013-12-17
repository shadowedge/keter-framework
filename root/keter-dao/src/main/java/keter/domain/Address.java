package keter.domain;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {

    @Id @GeneratedValue()
    @Column(name = "ADDRESS_ID")
    private Long id = null;

    @Column(name = "STREET", length = 255, nullable = false)
    private String street;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false, insertable = false)
    @org.hibernate.annotations.ForeignKey(name="FK_USER_ID")
    private User user;

    /**
     * No-arg constructor for JavaBean tools
     */
    public Address() {}

    /**
     * Full constructor
     */
    public Address(String street) {
        this.street = street;
   
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
