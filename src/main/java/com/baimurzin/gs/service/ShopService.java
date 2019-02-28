package com.baimurzin.gs.service;

import com.baimurzin.gs.model.Shop;
import com.baimurzin.gs.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private ShopRepository repository;

    @Autowired
    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public Shop create(Shop shop) {
        return repository.save(shop);
    }

    public Shop update(Shop shop) {
        return repository.save(shop);
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    public Shop get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Shop> getAll() {
        return repository.findAll();
    }
}
