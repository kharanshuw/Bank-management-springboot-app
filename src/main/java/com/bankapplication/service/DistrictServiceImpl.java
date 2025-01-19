package com.bankapplication.service;

import com.bankapplication.model.District;
import com.bankapplication.repository.DistrictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * This service class called DistrictServiceImpl
 * provides methods to manage districts.
 */
@Service
public class DistrictServiceImpl implements DistrictService {

    private static final Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);
    public DistrictRepository districtRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        super();
        this.districtRepository = districtRepository;
    }


    /**
     * Retrieves a list of districts by the given state ID.
     *
     * @param stateId the ID of the state
     * @return a list of districts in the specified state
     */
    @Override
    public List<District> getDistrictsByStateId(int stateId) {
        logger.info("getDistrictsByStateId method called with stateId: {}", stateId);

        List<District> districts = districtRepository.findDistrictsByStateId(stateId);

        logger.info("Retrieved {} districts for stateId: {}", districts.size(), stateId);


        return districts;
    }

}
