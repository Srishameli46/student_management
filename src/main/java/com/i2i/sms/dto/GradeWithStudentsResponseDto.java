package com.i2i.sms.dto;

import java.util.ArrayList;
import java.util.List;

import com.i2i.sms.models.Grade;
import com.i2i.sms.models.Student;

public class GradeWithStudentsResponseDto {
    private int gradeId;
    private int standard;
    private String section;
    private List<StudentResponseDto> students = new ArrayList<>();

    public GradeWithStudentsResponseDto(Grade grade) {
        this.gradeId = grade.getGradeId();
        this.standard = grade.getStandard();
        this.section = grade.getSection();
        for(Student student : grade.getStudents()) {
            StudentResponseDto studentResponseDto = new StudentResponseDto(student);
            this.students.add(studentResponseDto);
        }
    }

    public List<StudentResponseDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponseDto> students) {
        this.students = students;
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
