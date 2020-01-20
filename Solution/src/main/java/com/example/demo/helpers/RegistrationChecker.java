package com.example.demo.helpers;

import com.example.demo.models.registration.RegistrationDTO;

public class RegistrationChecker {

    public static boolean areCardFieldEmpty(RegistrationDTO registrationDTO) {
        return (registrationDTO.getNumber().equals("")
                && registrationDTO.getExpirationDate().equals("") &&
                registrationDTO.getSecurityCode() == 0);
    }

   public static boolean areCardFieldNotEmpty(RegistrationDTO registrationDTO) {
      return (!registrationDTO.getNumber().equals("")
              && !registrationDTO.getExpirationDate().equals("") &&
              registrationDTO.getSecurityCode() != 0);
   }

}
