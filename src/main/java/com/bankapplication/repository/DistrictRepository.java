package com.bankapplication.repository;

import com.bankapplication.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query("SELECT d FROM District d WHERE d.state.id = :stateId")
    public List<District> findDistrictsByStateId(@Param("stateId") int stateId);


}
