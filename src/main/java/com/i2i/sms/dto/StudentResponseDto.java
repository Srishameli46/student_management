package com.i2i.sms.dto;

import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

import java.util.Date;

public class StudentResponseDto {
    private int id;
    private String name;
    private Date dob;
    private int age;
    private AddressResponseDto addressResponseDto;
    private GradeResponseDto gradeResponseDto;

    public StudentResponseDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dob = student.getDob();
        this.age = DateUtils.calculatePeriodDifference(dob);
        this.addressResponseDto = new AddressResponseDto(student.getAddress());
        this.gradeResponseDto = new GradeResponseDto(student.getGrade());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressResponseDto getAddressInfo() {
        return addressResponseDto;
    }

    public void setAddressInfo(AddressResponseDto addressResponseDto) {
        this.addressResponseDto = addressResponseDto;
    }

    public GradeResponseDto getGradeInfo() {
        return gradeResponseDto;
    }

    public void setGradeInfo(GradeResponseDto grade) {
        this.gradeResponseDto = grade;
    }
}
