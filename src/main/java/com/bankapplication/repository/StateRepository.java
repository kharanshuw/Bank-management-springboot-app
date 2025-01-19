package com.bankapplication.repository;

import com.bankapplication.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    @Query("SELECT s FROM State s WHERE s.country.id = :countryId")
    public List<State> findByCountryId(@Param("countryId") int countryId);
}
