package pu.fmi.carmanagement.model.dto.response;

import java.time.YearMonth;

public class MonthlyRequestsReportDTO {
    private YearMonthDTO yearMonth;
    private long requests;

    public YearMonthDTO getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = new YearMonthDTO(yearMonth);
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }
}
