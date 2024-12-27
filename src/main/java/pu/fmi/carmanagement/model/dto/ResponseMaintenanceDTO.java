package pu.fmi.carmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMaintenanceDTO {
    private Long id;
    private Long carId;
    private Long carName;
    private String serviceType;
    private String scheduledDate;
    private Long garageId;
    private Long garageName;
}
