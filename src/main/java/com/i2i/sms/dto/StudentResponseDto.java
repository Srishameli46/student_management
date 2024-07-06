package com.i2i.sms.dto;

import java.time.LocalDate;

import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing a student which contains details about the student
 * such as ID, name, date of birth, associated grade, address and their sportsActivity.
 * </p>
 */
public class StudentResponseDto {
    private String id;
    private String name;
    private LocalDate dob;
    private int age;
    private AddressDto address;
    private GradeDto grade;

    public StudentResponseDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dob = student.getDob();
        this.age = DateUtils.calculatePeriodDifference(dob);
        this.address = new AddressDto(student.getAddress());
        this.grade = new GradeDto(student.getGrade());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }
}
