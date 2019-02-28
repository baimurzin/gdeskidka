package com.baimurzin.gs.controller;

import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class CustomerController extends BaseSecuredController {

    public CustomerController(SecurityService securityService, UserService userService) {
        super(securityService, userService);
    }

    @RequestMapping("/customer")
    public ResponseEntity showCustomerMainPage(ModelMap modelMap) {
        return new ResponseEntity<User>(getLoggedUser(), HttpStatus.OK);
    }

    @RequestMapping("/company")
    public ResponseEntity showCustomerMainPage2(ModelMap modelMap) {
        return new ResponseEntity<User>(getLoggedUser(), HttpStatus.OK);
    }

    @RequestMapping("/user/info")
    public ResponseEntity getUserInfo() {
        return new ResponseEntity(getLoggedUser(), HttpStatus.OK);
    }

}
