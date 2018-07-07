package com.baimurzin.gs.dto;

import com.baimurzin.gs.model.Role;
import lombok.Data;

import java.util.BitSet;
import java.util.Set;

@Data
public class UserDTO {
    private String email;

    private String password;
    private String passwordConfirm;
    private Set<Role> roles;
    private String companyName;
}
