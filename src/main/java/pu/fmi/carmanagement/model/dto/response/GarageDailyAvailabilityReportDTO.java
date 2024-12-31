package pu.fmi.carmanagement.model.dto.response;

public class GarageDailyAvailabilityReportDTO {
    private String date;
    private Long requests;
    private Long availableCapacity;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getRequests() {
        return requests;
    }

    public void setRequests(Long requests) {
        this.requests = requests;
    }

    public Long getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Long availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}
