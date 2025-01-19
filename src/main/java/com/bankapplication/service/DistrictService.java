package com.bankapplication.service;

import com.bankapplication.model.District;

import java.util.List;


public interface DistrictService {
    public List<District> getDistrictsByStateId(int stateId);
}
