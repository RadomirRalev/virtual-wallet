package com.example.demo.models.user;

import com.example.demo.models.card.virtual.VirtualCard;
import com.example.demo.models.card.physical.PhysicalCard;

import javax.persistence.*;
import java.util.Base64;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "enabled")
    private int enabled;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "physical_card")
    private PhysicalCard physicalCard;
    @OneToOne
    @JoinColumn(name = "virtual_card")
    private VirtualCard virtualCard;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Lob
    @Column(name = "picture", columnDefinition = "BLOB")
    private byte[] picture;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PhysicalCard getPhysicalCard() {
        return physicalCard;
    }

    public void setPhysicalCard(PhysicalCard physicalCard) {
        this.physicalCard = physicalCard;
    }

    public VirtualCard getVirtualCard() {
        return virtualCard;
    }

    public void setVirtualCard(VirtualCard virtualCard) {
        this.virtualCard = virtualCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return Base64.getEncoder().encodeToString(picture);
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
