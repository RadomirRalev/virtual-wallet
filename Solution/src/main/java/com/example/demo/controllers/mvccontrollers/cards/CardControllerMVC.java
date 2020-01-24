package com.example.demo.controllers.mvccontrollers.cards;

        import com.example.demo.services.CardDetailsService;
        import com.example.demo.services.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardControllerMVC {
    private CardDetailsService cardDetailsService;

    @Autowired
    public CardControllerMVC(CardDetailsService cardDetailsService) {
        this.cardDetailsService = cardDetailsService;
    }

    @GetMapping("/cardregistration")
    public String account(Model model) {
        return "cardregistration";
    }
}
