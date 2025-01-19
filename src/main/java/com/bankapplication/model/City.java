package com.bankapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<UserDetails> userDetailsList = new ArrayList<>(); // List of UserDetails

    public void addUserDetail(UserDetails userDetails) {
        userDetailsList.add(userDetails);
        userDetails.setCity(this); // Set bi-directional relationship
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<UserDetails> getUserDetailsList() {
        return userDetailsList;
    }

    public void setUserDetailsList(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }


    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", district=" + district +
                ", userDetailsList=" + userDetailsList +
                '}';
    }
}

    
