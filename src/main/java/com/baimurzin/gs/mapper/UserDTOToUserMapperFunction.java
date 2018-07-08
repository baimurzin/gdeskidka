package com.baimurzin.gs.mapper;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.util.HelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserDTOToUserMapperFunction implements Function<UserDTO, User> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User apply(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .roles(userDTO.getRoles())
                .build();
    }
}