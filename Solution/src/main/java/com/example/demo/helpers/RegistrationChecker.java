package com.example.demo.helpers;

import com.example.demo.models.card.CardDTO;

public class RegistrationChecker {

    public static boolean areCardFieldEmpty(CardDTO cardDTO) {
        return (cardDTO.getNumber().equals("")
                && cardDTO.getExpirationDate().equals("") &&
                cardDTO.getSecurityCode() == 0);
    }

   public static boolean areCardFieldNotEmpty(CardDTO cardDTO) {
      return (!cardDTO.getNumber().equals("")
              && !cardDTO.getExpirationDate().equals("") &&
              cardDTO.getSecurityCode() != 0);
   }
}
