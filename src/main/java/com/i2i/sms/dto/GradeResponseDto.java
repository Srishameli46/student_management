package com.i2i.sms.dto;

import com.i2i.sms.models.Grade;

/**
 * <p>
 * Class representing a gradeResponseDto, which can have standard and section.
 * The gradeId is the uuid value.
 * The standard is the numerical value lies between 1 to 12.
 * The section can either be 'A' or 'B'.
 * </p>
 */
public class GradeResponseDto {

    private String gradeId;
    private int standard;
    private String section;

    public GradeResponseDto(Grade grade) {
        this.gradeId = grade.getGradeId();
        this.standard = grade.getStandard();
        this.section = grade.getSection();
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
