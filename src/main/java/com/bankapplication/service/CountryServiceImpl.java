package com.bankapplication.service;

import com.bankapplication.model.Country;
import com.bankapplication.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing countries.
 */
@Service
public class CountryServiceImpl implements CountryService {

    // This sets up the logger for the countryserviceimpl class.
    private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        super();
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves all countries from the repository.
     *
     * @return a list of all countries
     */
    @Override
    public List<Country> getAllCountries() {

        logger.info("getAllCountries method from CountryService called");

        List<Country> list = countryRepository.findAll();


        logger.info("Retrieved {} countries", list.size());

        return list;
    }

}
