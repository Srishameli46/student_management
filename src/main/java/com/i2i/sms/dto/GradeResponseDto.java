package com.i2i.sms.dto;

import com.i2i.sms.models.Grade;

public class GradeResponseDto {

    private int gradeId;

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

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
