package com.i2i.sms.dto;

import com.i2i.sms.models.Grade;

/**
 * <p>
 * Class representing a gradeDto, which can have standard and section
 * The standard is the numerical value lies between 1 to 12.
 * The section can either be 'A' or 'B'.
 * </p>
 */
public class GradeDto {
    private int standard;

    private String section;
    public GradeDto(Grade grade) {
        this.standard = grade.getStandard();
        this.section = grade.getSection();
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
