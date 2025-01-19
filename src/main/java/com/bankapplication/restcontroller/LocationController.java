package com.bankapplication.restcontroller;

import com.bankapplication.model.City;
import com.bankapplication.model.Country;
import com.bankapplication.model.District;
import com.bankapplication.model.State;
import com.bankapplication.service.CityService;
import com.bankapplication.service.CountryService;
import com.bankapplication.service.DistrictService;
import com.bankapplication.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LocationController is a REST controller that handles HTTP requests related to
 * location data. It provides endpoints to retrieve information about countries,
 * states, districts, and cities.
 * <p>
 * Responsibilities: - Fetch and return a list of all countries. - Fetch and
 * return a list of states by country ID. - Fetch and return a list of districts
 * by state ID. - Fetch and return a list of cities by district ID.
 * <p>
 * Example usage: - GET /api/countries: Returns a list of all countries. - GET
 * /api/states/{countryId}: Returns a list of states for the specified country
 * ID. - GET /api/district/{stateid}: Returns a list of districts for the
 * specified state ID. - GET /api/city/{districtid}: Returns a list of cities
 * for the specified district ID.
 * <p>
 * Note: - This controller relies on the CountryService, StateService,
 * DistrictService, and CityService to fetch data. - Ensure that the services
 * are properly injected and configured.
 */
@RestController
@RequestMapping("/api")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private CountryService countryService;

    private StateService stateService;

    private CityService cityService;

    private DistrictService districtService;

    @Autowired
    public LocationController(CountryService countryService, StateService stateService, CityService cityService,
                              DistrictService districtService) {
        super();
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.districtService = districtService;
    }

    /**
     * Endpoint to get the list of all countries.
     *
     * @return List of Country objects.
     */
    @GetMapping("/countries")
    public List<Country> getCountries() { // Fetch the list of all countries from the service layer
        List<Country> countries = countryService.getAllCountries();

        // Log an informational message indicating the endpoint was called successfully
        logger.info("/countries endpoint called successfully");
        logger.info("length of country is " + countries.size());

        return countries;
    }

    /**
     * Endpoint to get the list of states by country ID.
     *
     * @param countryId the ID of the country
     * @return List of State objects
     */
    @GetMapping("/states/{countryId}")
    public List<State> getStateByCountry(@PathVariable int countryId) {
        // Log an informational message indicating the endpoint was called
        logger.info("Fetching states for country ID: {}", countryId);

        // Fetch the list of states by country ID from the service layer
        List<State> list = stateService.getStatesByCountryId(countryId);

        // Log an informational message indicating the successful retrieval of states
        logger.info("Successfully fetched {} states for country ID: {}", list.size(), countryId);

        // Return the list of states
        return list;
    }

    /**
     * Endpoint to get the list of cities by district ID.
     *
     * @param districtid the ID of the district
     * @return List of City objects
     */
    @GetMapping("/cities/{districtId}")
    public List<City> getCityByDistrict(@PathVariable int districtId) {
        // Log an informational message indicating the endpoint was called
        logger.info("Fetching cities for district ID: {}", districtId);

        // Fetch the list of cities by district ID from the service layer
        List<City> cities = cityService.getCitiesByDistrictId(districtId);


        // Log an informational message indicating the successful retrieval of cities
        logger.info("Successfully fetched cities for district ID: {}", districtId);

        // Return the list of cities
        return cities;
    }

    /**
     * Endpoint to get the list of districts by state ID.
     *
     * @param stateid the ID of the state
     * @return List of District objects
     */

    @GetMapping("/districts/{stateId}")
    public List<District> getDistrictsByStates(@PathVariable int stateId) {
        // Log an informational message indicating the endpoint was called
        logger.info("Fetching districts for state ID: {}", stateId);

        // Fetch the list of districts by state ID from the service layer

        List<District> districts = districtService.getDistrictsByStateId(stateId);

        // Log an informational message indicating the successful retrieval of districts
        logger.info("Successfully fetched {} districts for state ID: {}", districts.size(), stateId);

        return districts;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return responseEntity;

    }

}
