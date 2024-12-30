package pu.fmi.carmanagement.model.dto.response;

import java.time.YearMonth;

public class YearMonthDTO {
    private int year;
    private String month;
    private boolean leapYear;
    private int monthValue;

    public YearMonthDTO(YearMonth yearMonth) {
        year = yearMonth.getYear();
        month = yearMonth.getMonth().name();
        leapYear = yearMonth.isLeapYear();
        monthValue = yearMonth.getMonthValue();
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public boolean isLeapYear() {
        return leapYear;
    }

    public int getMonthValue() {
        return monthValue;
    }
}
