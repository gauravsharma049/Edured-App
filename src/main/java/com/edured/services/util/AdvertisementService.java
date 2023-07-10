package com.edured.services.util;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edured.model.files.Advertisement;
import com.edured.repository.files.AdvertisementRepository;
@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement getSingleAdvertisement(){
        List<Advertisement> advertisements = advertisementRepository.getRandomAdvertisements(1);
        if(advertisements.size()>0)
            return advertisements.get(0);
        else
            return null;
    }
    public Advertisement saveAdvertisement(Advertisement advertisement){
        return advertisementRepository.save(advertisement);
    }
    public List<Advertisement> getAllAdvertisements(){
        return advertisementRepository.findAll();
    }
    public List<Advertisement> getTwoAdvertisements(){
        return advertisementRepository.getRandomAdvertisements(2);
    }
    public List<Advertisement> getAdvertisements(int n){
        return advertisementRepository.getRandomAdvertisements(n);
    }
}
