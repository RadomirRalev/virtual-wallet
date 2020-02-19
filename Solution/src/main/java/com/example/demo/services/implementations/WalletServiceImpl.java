package com.example.demo.services.implementations;

import com.example.demo.exceptions.DefaultWalletDeletionException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.WalletBalancePositiveDeletionException;
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

import static com.example.demo.constants.ExceptionConstants.*;
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
    public boolean checkIfWalletIdExists(int id) {
        return walletRepository.checkIfWalletIdExists(id);
    }

    @Override
    public Wallet updateWallet(Wallet walletToBeUpdated) {
        if (!checkIfWalletIdExists(walletToBeUpdated.getId())) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, walletToBeUpdated.getId());
        }
        return walletRepository.updateWallet(walletToBeUpdated);
    }

    @Override
    public Wallet setAsDefault(Wallet walletToBeUpdated, User user) {
        if (!checkIfWalletIdExists(walletToBeUpdated.getId())) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, walletToBeUpdated.getId());
        }
        if (walletToBeUpdated.isWalletDefault()) {
            throw new DefaultWalletDeletionException(WALLET_ALREADY_DEFAULT);
        }
        Wallet defaultWallet = walletRepository.getDefaultWallet(user.getId());
        walletRepository.disableDefaultWallet(defaultWallet.getId());
        return walletRepository.setAsDefault(walletToBeUpdated);
    }

    @Override
    public Wallet getDefaultWallet(int userId) {
        return walletRepository.getDefaultWallet(userId);
    }

    @Override
    public Wallet deleteWallet(Wallet walletToBeDeleted) {
        if (!checkIfWalletIdExists(walletToBeDeleted.getId())) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, walletToBeDeleted.getId());
        }
        if (walletToBeDeleted.isWalletDefault()) {
            throw new DefaultWalletDeletionException(CANNOT_DELETE_DEFAULT_WALLET, walletToBeDeleted.getId());
        }
        if (isBalancePositive(walletToBeDeleted.getId())) {
            throw new WalletBalancePositiveDeletionException(CANNOT_DELETE_WALLET_WITH_POSITIVE_BALANCE);
        }
        return walletRepository.deleteWallet(walletToBeDeleted);
    }

    public boolean isBalancePositive(int walletId) {
        return walletRepository.checkIfBalanceIsPositive(walletId);
    }
}
