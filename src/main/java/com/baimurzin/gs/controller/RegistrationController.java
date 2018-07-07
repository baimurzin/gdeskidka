package com.baimurzin.gs.controller;

import com.baimurzin.gs.dto.UserDTO;
import com.baimurzin.gs.model.Role;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.RoleService;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.UserService;
import com.baimurzin.gs.validator.UserDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String registration(@ModelAttribute("userForm") UserDTO userForm, BindingResult bindingResult, ModelMap model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Set<Role> roleSet = new HashSet<>();
        Role role = new Role();
        role.setName("ROLE_CUSTOMER");
        roleSet.add(roleService.save(role));

        userForm.setRoles(roleSet);
        userService.create(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/";
    }
}
