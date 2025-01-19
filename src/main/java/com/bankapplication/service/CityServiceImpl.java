package com.bankapplication.service;

import com.bankapplication.model.City;
import com.bankapplication.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * CityServiceImpl is a service class that implements the CityService interface.
 * It provides methods to interact with the CityRepository and perform operations related to cities.
 * <p>
 * Responsibilities:
 * - Fetch and return a list of cities by district ID.
 */
@Service
public class CityServiceImpl implements CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    private CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        super();
        this.cityRepository = cityRepository;
    }

    /**
     * Retrieves a list of cities by district ID.
     *
     * @param districtId the ID of the district
     * @return List of City objects
     */
    @Override
    public List<City> getCitiesByDistrictId(int districtId) {

        // Log an informational message indicating the method was called
        logger.info("Fetching cities for district ID: {}", districtId);

        // Fetch the list of cities by district ID from the repository
        List<City> list = cityRepository.findCitiesByDistrictId(districtId);

//		
//		for(City city2 : list)
//		{
//		    System.out.println(city2.getCityName());
//		}

        // Log an informational message indicating the successful retrieval of cities
        logger.info("Successfully fetched {} cities for district ID: {}", list.size(), districtId);

        return list;
    }

}
