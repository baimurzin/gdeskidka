package com.baimurzin.gs.controller;

import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"CUSTOMER"})
public class CustomerController extends BaseSecuredController {

    public CustomerController(SecurityService securityService, UserService userService) {
        super(securityService, userService);
    }

    @RequestMapping("/customer")
    public String showCustomerMainPage(ModelMap modelMap) {
        modelMap.put("user", getLoggedUser());
        return "company/customer";
    }
}
