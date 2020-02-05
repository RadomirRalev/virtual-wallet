package com.example.demo.models.role;

import com.example.demo.models.user.UserRegistrationDTO;

import static com.example.demo.constants.SQLQueryConstants.ROLE_USER;

public class RoleMapper {

    public static Role createRole(UserRegistrationDTO userRegistrationDTO) {
        Role role = new Role();
        role.setUsername(userRegistrationDTO.getUsername());
        role.setRole(ROLE_USER);
        return role;
    }

}
