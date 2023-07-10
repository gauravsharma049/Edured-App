package com.edured.repository.files;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edured.model.files.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Query(value = "SELECT * FROM Advertisement ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    public List<Advertisement> getRandomAdvertisements(@Param("count") int count);
}
