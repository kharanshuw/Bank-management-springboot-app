package com.bankapplication.service;

import com.bankapplication.model.State;

import java.util.List;

public interface StateService {
    public List<State> getStatesByCountryId(int countryId);
}
