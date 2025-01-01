package pu.fmi.carmanagement.model.dto.response;

import java.util.Set;

public class ResponseCarDTO {
    private Long id;
    private String make;
    private String model;
    private Integer productionYear;
    private String licensePlate;
    private Set<ResponseGarageDTO> garages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<ResponseGarageDTO> getGarages() {
        return garages;
    }

    public void setGarages(Set<ResponseGarageDTO> garages) {
        this.garages = garages;
    }
}
