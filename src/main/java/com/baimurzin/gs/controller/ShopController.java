package com.baimurzin.gs.controller;


import com.baimurzin.gs.model.Shop;
import com.baimurzin.gs.service.SecurityService;
import com.baimurzin.gs.service.ShopService;
import com.baimurzin.gs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/shop", produces = "application/json; charset=utf-8")
public class ShopController extends BaseSecuredController{

    private final ShopService service;

    @Autowired
    public ShopController(SecurityService securityService,
                          UserService userService,
                          ShopService service) {
        super(securityService, userService);
        this.service = service;
    }


    @PostMapping
    public ResponseEntity create(@RequestBody Shop shop) {
        if(shop == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.create(shop));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Shop shop){
        if(shop == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.update(shop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean isDeleted = service.delete(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity get(Long id) {
        return id == null ? ResponseEntity.ok(service.getAll())
                : ResponseEntity.ok(service.get(id));
    }

}
