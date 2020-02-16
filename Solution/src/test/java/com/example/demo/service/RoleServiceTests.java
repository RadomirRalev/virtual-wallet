package com.example.demo.service;

import com.example.demo.models.role.Role;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.service.Factory.createRole;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTests {
    @Mock
    RoleRepository roleRepository;


    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    public void createRoleShouldCallRepository_Save() {
        //Arrange
        Role role = createRole();

        //Act
        roleService.createRole(role);

        //Assert
        Mockito.verify(roleRepository, Mockito.times(1))
                .createRole(role);
    }

}
