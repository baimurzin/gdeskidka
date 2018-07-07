package com.baimurzin.gs.controller;

import com.baimurzin.gs.dto.ProductDTO;
import com.baimurzin.gs.model.Product;
import com.baimurzin.gs.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createNewProduct(ProductDTO productDTO, HttpServletRequest request) {
        Product product = productService.create(productDTO);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

}
