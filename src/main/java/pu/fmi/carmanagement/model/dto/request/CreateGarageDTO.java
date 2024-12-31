package pu.fmi.carmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class CreateGarageDTO {
    @NotBlank
    @Length(max = 80)
    private String name;
    @NotBlank
    @Length(max = 80)
    private String location;
    @NotBlank
    @Length(max = 30)
    private String city;
    @Positive
    @NotNull
    private Long capacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
