package pu.fmi.carmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Car extends BaseEntity {
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(name = "production_year", nullable = false)
    private Integer productionYear;
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Garage> garages;
}
