package com.example.demo.models.confirmIdentity;

import org.springframework.web.multipart.MultipartFile;

public class ConfirmIdentityRegistrationDTO {

    private MultipartFile front_picture;
    private MultipartFile rear_picture;
    private MultipartFile selfie;

    public ConfirmIdentityRegistrationDTO() {
    }

    public MultipartFile getFront_picture() {
        return front_picture;
    }

    public void setFront_picture(MultipartFile front_picture) {
        this.front_picture = front_picture;
    }

    public MultipartFile getRear_picture() {
        return rear_picture;
    }

    public void setRear_picture(MultipartFile rear_picture) {
        this.rear_picture = rear_picture;
    }

    public MultipartFile getSelfie() {
        return selfie;
    }

    public void setSelfie(MultipartFile selfie) {
        this.selfie = selfie;
    }
}
