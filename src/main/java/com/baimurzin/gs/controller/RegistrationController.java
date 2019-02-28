package com.baimurzin.gs.controller;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.model.Role;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.RoleService;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.UserService;
import com.baimurzin.gs.validator.UserDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final SecurityService securityService;

    private final UserDTOValidator userValidator;

    private final RoleService roleService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model) {
        model.addAttribute("userForm", new UserDTO());

        return "company/registration";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity registration(@ModelAttribute("userForm") UserDTO userForm, ModelMap model) {
//        userValidator.validate(userForm, bindingResult);

        if (isExist(userForm)) {
            throw new BadCredentialsException("User with such email already exists ");
        }
        if (!isPasswordEquals(userForm)) {
//            return new ResponseEntity<>("Password confirmation and Password must match ",HttpStatus.BAD_REQUEST);
            throw new BadCredentialsException("Passwords not matches");
        }

        Set<Role> roleSet = new HashSet<>();
        Role role = new Role();
        role.setName("ROLE_CUSTOMER");
        roleSet.add(roleService.save(role));

        userForm.setRoles(roleSet);
        User user = userService.create(userForm);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private boolean isExist(UserDTO userRegistrationForm) {
        return userService.findByEmail(userRegistrationForm.getEmail()) != null;
    }

    private boolean isPasswordEquals(UserDTO userRegistrationForm) {
        return userRegistrationForm.getPassword().equals(userRegistrationForm.getPasswordConfirm());
    }
}
