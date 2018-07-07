package com.baimurzin.gs.service;

import com.baimurzin.gs.dto.ProductDTO;
import com.baimurzin.gs.model.Product;
import com.baimurzin.gs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductDTO productDTO) {
        System.out.println(1);
        return new Product();
    }


}