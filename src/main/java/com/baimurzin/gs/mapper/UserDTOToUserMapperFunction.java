package com.baimurzin.gs.mapper;

import com.baimurzin.gs.dto.UserDTO;
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
        return User.builder()
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .roles(userDTO.getRoles())
                .build();
    }
}