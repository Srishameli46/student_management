package com.i2i.sms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CreateStandardDto  {
    @Min(value = 1, message = "Standard should not be lesser than 1")
    @Max(value = 12, message = "Standard should not exceed 12")
    int standard;

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
}
