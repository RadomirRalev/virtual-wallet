package com.example.demo.service;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.models.wallet.WalletMapper;
import com.example.demo.repositories.contracts.WalletRepository;
import com.example.demo.services.contracts.UserService;
import com.example.demo.services.implementations.WalletServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.service.Factory.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WalletServiceTests {
    @Mock
    WalletRepository walletRepository;
    @Mock
    UserService userService;
    @Mock
    WalletMapper walletMapper;


    @InjectMocks
    WalletServiceImpl walletService;

    @Test
    public void createWalletShouldCallRepository_Save() {
        //Arrange
        WalletCreationDTO walletCreationDTO = createWalletCreationDTO();
        User user = createUser();
        Wallet wallet = createWallet();
        user.setId(1);
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet);
        user.setWallets(wallets);
        Mockito.when(walletMapper.createWallet(walletCreationDTO))
                .thenReturn(wallet);
        Mockito.when(userService.getById(user.getId()))
                .thenReturn(user);

        //Act
        walletService.createWallet(walletCreationDTO, user.getId());

        //Assert
        Mockito.verify(walletRepository, Mockito.times(1))
                .createWallet(wallet);
    }

    @Test
    public void getWalletByUserIdShould_ReturnWallet() {
        //Arrange
        User user = createUser();
        Wallet wallet = createWallet();
        user.setId(1);
        wallet.setId(2);
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet);
        user.setWallets(wallets);
        Mockito.when(walletRepository.getWalletsbyUserId(user.getId()))
                .thenReturn(wallets);

        //Act
        List<Wallet> returnedWallet = walletService.getWalletsbyUserId(user.getId());

        //Assert
        Assert.assertSame(returnedWallet.get(0), wallet);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getWalletByIdShould_ThrowExceptionWhenNoSuchWallet() {
        Mockito.when(walletService.checkIfWalletIdExists(anyInt()))
                .thenReturn(false);

        //Act
        walletService.getWalletById(anyInt());
    }

    @Test
    public void getWalletByIdShould_ReturnWalletWhenWalletExists() {
        Wallet wallet = createWallet();
        wallet.setId(1);

        Mockito.when(walletService.checkIfWalletIdExists(anyInt()))
                .thenReturn(true);
        Mockito.when(walletRepository.getWalletById(1))
                .thenReturn(wallet);

        //Act
        Wallet walletToCheck =  walletService.getWalletById(1);

        //Assert
        Assert.assertSame(wallet, walletToCheck);

    }

    @Test
    public void updateWalletShouldCallRepository_Update() {
        //Arrange
        Wallet wallet = createWallet();

        //Act
        walletService.updateWallet(wallet);

        //Assert
        Mockito.verify(walletRepository, Mockito.times(1))
                .updateWallet(wallet);
    }

    @Test
    public void getDefaultWalletShould_ReturnWallet() {
        //Arrange
        User user = createUser();
        Wallet wallet = createWallet();
        user.setId(1);
        wallet.setId(2);
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet);
        user.setWallets(wallets);
        Mockito.when(walletRepository.getDefaultWallet(user.getId()))
                .thenReturn(wallets.get(0));

        //Act
        Wallet returnedWallet = walletService.getDefaultWallet(user.getId());

        //Assert
        Assert.assertSame(returnedWallet, wallet);
    }

    @Test
    public void setDefaultWalletShouldCallRepository_Update() {
        //Arrange
        Wallet wallet = createWallet();
        Wallet walletCurrentDefault = createWallet();
        User user = createUser();
        Mockito.when(walletRepository.getDefaultWallet(user.getId()))
                .thenReturn(walletCurrentDefault);
        Mockito.when(walletRepository.disableDefaultWallet(walletCurrentDefault.getId()))
                .thenReturn(walletCurrentDefault);

        //Act
        walletService.setAsDefault(wallet, user);

        //Assert
        Mockito.verify(walletRepository, Mockito.times(1))
                .setAsDefault(wallet);
    }
}
