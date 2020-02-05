package com.example.demo.services;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.models.wallet.WalletMapper;
import com.example.demo.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.WALLET_WITH_ID_NOT_EXISTS;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;
    private WalletMapper walletMapper;
    private UserService userService;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository,
                             WalletMapper walletMapper,
                             UserService userService) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.userService = userService;
    }

    @Override
    public Wallet createWallet(WalletCreationDTO walletCreationDTO, int userId) {
        walletCreationDTO.setUserId(userId);
        Wallet wallet = walletMapper.mapWallet(walletCreationDTO);
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
    public Wallet getById(int id) {
        if (checkIfWalletIdExists(id)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
        }
        return walletRepository.getById(id);
    }

    @Override
    public boolean checkIfWalletIdExists(int id) { return walletRepository.checkIfWalletIdExists(id); }

    @Override
    public Wallet updateWallet(Wallet walletToBeUpdated) {
        return walletRepository.updateWallet(walletToBeUpdated);
    }

    public Wallet setAsDefault(Wallet walletToBeUpdated) {
        Wallet defaultWallet = walletRepository.getDefaultWallet();
        walletRepository.disableDefaultWallet(defaultWallet.getId());
        return walletRepository.setAsDefault(walletToBeUpdated);
    }
}
