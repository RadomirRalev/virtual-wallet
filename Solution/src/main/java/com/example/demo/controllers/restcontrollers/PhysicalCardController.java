package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.services.PhysicalCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;

@RestController
@RequestMapping("api/physical-card")

public class PhysicalCardController {

    private PhysicalCardService physicalCardService;

    @Autowired
    public PhysicalCardController(PhysicalCardService physicalCardService) {
        this.physicalCardService = physicalCardService;
    }

    @GetMapping("/{id}")
    public PhysicalCard getById(@PathVariable int id) {
        try {
            return physicalCardService.getById(id);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/number/{number}")
    public PhysicalCard getByNumber(@PathVariable String number) {
        try {
            return physicalCardService.getByNumber(number);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/number/{number}")
    public void deleteUser(@PathVariable String number) {
        try{
            physicalCardService.setStatusPhysicalCard(number, DISABLE);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
