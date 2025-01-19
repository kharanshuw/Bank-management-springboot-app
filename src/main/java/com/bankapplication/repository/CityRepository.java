package com.bankapplication.repository;

import com.bankapplication.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City c WHERE c.district.id = :districtId")
    List<City> findCitiesByDistrictId(@Param("districtId") int districtId);


    @Query("SELECT c FROM City c WHERE c.id = :id")
    City findByCityId(@Param("id") int id);


}
