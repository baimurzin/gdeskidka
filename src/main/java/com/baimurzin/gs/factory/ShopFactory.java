package com.baimurzin.gs.factory;

import com.baimurzin.gs.dto.ShopDTO;
import com.baimurzin.gs.model.*;
import com.baimurzin.gs.repository.AddressRepository;
import com.baimurzin.gs.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopFactory {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PhotoRepository photoRepository;

    public enum ShopTypes {
        ONLINE, OFFLINE
    }

    public Shop createShop(ShopDTO shopDTO) {
        Shop shop = null;
        ShopTypes shopType = ShopTypes.valueOf(shopDTO.getShopType().toUpperCase());
        if (shopType.equals(ShopTypes.OFFLINE)) {
            shop = new OfflineShop();
            //address
            OfflineAddress offlineAddress = new OfflineAddress();
            offlineAddress.setAddress(shopDTO.getAddress());

            shop.setAddress(offlineAddress);
        } else if (shopType.equals(ShopTypes.ONLINE)) {
            shop = new OnlineShop();

            //address
            OnlineAddress onlineAddress = new OnlineAddress();
            onlineAddress.setAddress(shopDTO.getAddress());

            shop.setAddress(onlineAddress);
        } else {
            throw new UnsupportedOperationException("Currently this type not available: " + shopDTO.getShopType());
        }
        addressRepository.save(shop.getAddress());
        shop.setName(shopDTO.getName());
        Photo photo = new Photo();
        photo.setBaseUrl(shopDTO.getPhoto());
        photo.setMain(true);
        shop.setPhoto(photo);
        photoRepository.save(photo);
        return shop;
    }
}
