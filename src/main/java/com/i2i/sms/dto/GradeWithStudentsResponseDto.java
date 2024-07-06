package com.i2i.sms.dto;

import java.util.ArrayList;
import java.util.List;

import com.i2i.sms.models.Grade;
import com.i2i.sms.models.Student;

/**
 * <p>
 * Class representing a grade, which can have standard and section
 * The standard is the numerical value lies between 1 to 12.
 * The section can either be 'A' or 'B'.
 * The student which contains details
 *   such as ID, name, date of birth, associated grade, address.
 * </p>
 */
public class GradeWithStudentsResponseDto {
    private String gradeId;
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
