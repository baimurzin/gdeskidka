package com.baimurzin.gs.controller;

import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseSecuredController {

    protected final SecurityService securityService;
    protected final UserService userService;

    protected User getLoggedUser() {
        String loggedInUsername = securityService.findLoggedInUsername();
        return userService.findByEmail(loggedInUsername);
    }
}