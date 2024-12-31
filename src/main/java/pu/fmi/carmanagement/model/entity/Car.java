package pu.fmi.carmanagement.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cars")
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
    @JoinTable(name = "cars_garages",
      joinColumns = @JoinColumn(name = "car_id"),
      inverseJoinColumns = @JoinColumn(name = "garage_id")
    )
    private Set<Garage> garages;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Set<Garage> getGarages() {
        return garages;
    }

    public void setGarages(Set<Garage> garages) {
        this.garages = garages;
    }
}
