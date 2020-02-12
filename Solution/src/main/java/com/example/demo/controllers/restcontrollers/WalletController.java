package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidWalletException;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.models.wallet.WalletUpdateDTO;
import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/wallet")
public class WalletController {
    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping("/{userId}")
    public Wallet create(@RequestBody @Valid WalletCreationDTO walletCreationDTO, @PathVariable int userId) {
        try {
            return walletService.createWallet(walletCreationDTO, userId);
        } catch (DuplicateEntityException | InvalidWalletException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public List<Wallet> getWalletsByUserId(@PathVariable int id) {
        try {
            return walletService.getWalletsbyUserId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/wallet/{id}")
    public Wallet updateWalletName(@PathVariable int id, @RequestBody @Valid WalletUpdateDTO walletUpdateDTO) {
        Wallet walletToUpdate = walletService.getWalletById(id);
        walletToUpdate.setName(walletUpdateDTO.getName());
        try {
            return walletService.updateWallet(walletToUpdate);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/wallet/setasdefault/{id}")
    public Wallet setWalletAsDefault(@PathVariable int id, @RequestBody @Valid WalletUpdateDTO walletUpdateDTO) {
        Wallet walletToUpdate = walletService.getWalletById(id);
        walletToUpdate.setName(walletUpdateDTO.getName());
        try {
            return walletService.updateWallet(walletToUpdate);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
