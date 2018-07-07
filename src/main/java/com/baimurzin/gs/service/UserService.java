package com.baimurzin.gs.service;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.mapper.UserDTOToUserMapperFunction;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOToUserMapperFunction userDTOToUserMapperFunction;

    public User create(UserDTO userDTO) {
        User user = userDTOToUserMapperFunction.apply(userDTO);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
