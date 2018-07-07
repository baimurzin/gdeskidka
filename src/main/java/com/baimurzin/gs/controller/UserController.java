package com.baimurzin.gs.controller;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//
//    @ResponseBody
//    @RequestMapping(value = "/company/register", method = RequestMethod.POST)
//    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {
//        if (Strings.isEmpty(userDTO.getEmail())
//                || Strings.isEmpty(userDTO.getPassword())
//                || Strings.isEmpty(userDTO.getPasswordRepeat())) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        if(userService.create(userDTO).getId() != null) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }
}
