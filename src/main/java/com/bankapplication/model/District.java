package com.bankapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", districtName='" + districtName + '\'' +
                ", state=" + state +
                '}';
    }
}
