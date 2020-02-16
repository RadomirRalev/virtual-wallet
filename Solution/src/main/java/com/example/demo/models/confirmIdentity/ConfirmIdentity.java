package com.example.demo.models.confirmIdentity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name = "confirm_identy")

public class ConfirmIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Lob
    @Column(name = "front_picture", columnDefinition = "BLOB")
    @JsonIgnore
    private byte[] front_picture;
    @Lob
    @Column(name = "rear_picture", columnDefinition = "BLOB")
    @JsonIgnore
    private byte[] rear_picture;
    @Lob
    @Column(name = "selfie", columnDefinition = "BLOB")
    @JsonIgnore
    private byte[] selfie;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "have_request")
    private boolean haveRequest;

    public ConfirmIdentity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFront_picture() {
        return Base64.getEncoder().encodeToString(front_picture);
    }

    public void setFront_picture(byte[] front_picture) {
        this.front_picture = front_picture;
    }

    public String getRear_picture() {
        return Base64.getEncoder().encodeToString(rear_picture);
    }

    public void setRear_picture(byte[] rear_picture) {
        this.rear_picture = rear_picture;
    }

    public String getSelfie() {
        return Base64.getEncoder().encodeToString(selfie);
    }

    public void setSelfie(byte[] selfie) {
        this.selfie = selfie;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isHaveRequest() {
        return haveRequest;
    }

    public void setHaveRequest(boolean dataOk) {
        this.haveRequest = dataOk;
    }
}
