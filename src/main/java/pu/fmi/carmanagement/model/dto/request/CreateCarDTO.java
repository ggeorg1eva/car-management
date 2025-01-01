package pu.fmi.carmanagement.model.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public class CreateCarDTO {
    @Size(max = 30)
    @NotBlank
    private String make;
    @Size(max = 30)
    @NotBlank
    private String model;
    @Positive
    @NotNull
    private Integer productionYear;
    @NotBlank
    @Pattern(regexp = "^$|.{8}")
    //either null, empty or 8 characters - the standard BG license plate size
    private String licensePlate;
    private Set<Long> garageIds;

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

    public Set<Long> getGarageIds() {
        return garageIds;
    }

    public void setGarageIds(Set<Long> garageIds) {
        this.garageIds = garageIds;
    }
}
