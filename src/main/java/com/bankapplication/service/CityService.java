package com.bankapplication.service;


import com.bankapplication.model.City;

import java.util.List;


public interface CityService {
    public List<City> getCitiesByDistrictId(int districtId);
}
