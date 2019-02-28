package com.baimurzin.gs.mapper;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.model.Customer;
import com.baimurzin.gs.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserDTOToUserMapperFunction implements Function<UserDTO, User> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User apply(UserDTO userDTO) {
        //remove hard code manually created user. User Factory with different type of users.
        Customer customer = new Customer();
        customer.setEmail(userDTO.getEmail());
        customer.setShownName(userDTO.getCompanyName());
        customer.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        customer.setRoles(userDTO.getRoles());
        return customer;
    }
}