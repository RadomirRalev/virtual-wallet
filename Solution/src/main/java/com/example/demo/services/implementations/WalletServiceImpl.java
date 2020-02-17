package com.example.demo.services.implementations;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.models.wallet.WalletMapper;
import com.example.demo.repositories.contracts.WalletRepository;
import com.example.demo.services.contracts.UserService;
import com.example.demo.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.WALLET_WITH_ID_NOT_EXISTS;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;
    private UserService userService;
    private WalletMapper walletMapper;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository,UserService userService, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletMapper = walletMapper;
    }

    @Override
    public Wallet createWallet(WalletCreationDTO walletCreationDTO, int userId) {
        walletCreationDTO.setUserId(userId);
        Wallet wallet = walletMapper.createWallet(walletCreationDTO);
        User user = userService.getById(userId);
        if (user.getWallets().isEmpty()) {
            wallet.setWalletDefault(ENABLE);
        }
        return walletRepository.createWallet(wallet);
    }

    @Override
    public List<Wallet> getWalletsbyUserId(int userId) {
        return walletRepository.getWalletsbyUserId(userId);
    }

    @Override
    public Wallet getWalletById(int id) {
        if (!checkIfWalletIdExists(id)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
        }
        return walletRepository.getWalletById(id);
    }

    @Override
    public boolean checkIfWalletIdExists(int id) { return walletRepository.checkIfWalletIdExists(id); }

    @Override
    public Wallet updateWallet(Wallet walletToBeUpdated) {
        return walletRepository.updateWallet(walletToBeUpdated);
    }

    @Override
    public Wallet setAsDefault(Wallet walletToBeUpdated, User user) {
        Wallet defaultWallet = walletRepository.getDefaultWallet(user.getId());
        walletRepository.disableDefaultWallet(defaultWallet.getId());
        return walletRepository.setAsDefault(walletToBeUpdated);
    }

    @Override
    public Wallet getDefaultWallet(int userId) {
        return walletRepository.getDefaultWallet(userId);
    }

}
