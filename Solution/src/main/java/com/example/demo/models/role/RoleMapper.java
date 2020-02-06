package com.example.demo.models.role;

import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import static com.example.demo.constants.SQLQueryConstants.ROLE_USER;

public class RoleMapper {

    public static Role createRole(User user) {
        Role role = new Role();
        role.setUsername(user.getUsername());
        role.setRole(ROLE_USER);
        role.setUserId(user.getId());
        return role;
    }

}
