package com.i2i.sms.dto;

import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentWithSportsResponseDto {
    private int id;
    private String name;
    private Date dob;
    private int age;
    private GradeResponseDto gradeResponseDto;
    private AddressResponseDto addressResponseDto;
    private List<SportsResponseDto> sports = new ArrayList<>();

    public StudentWithSportsResponseDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dob = student.getDob();
        this.age = DateUtils.calculatePeriodDifference(dob);
        this.gradeResponseDto = new GradeResponseDto(student.getGrade());
        this.addressResponseDto = new AddressResponseDto(student.getAddress());
        for(SportsActivity sportsActivity : student.getSportsActivities()) {
            SportsResponseDto sportsResponseDto = new SportsResponseDto(sportsActivity);
            this.sports.add(sportsResponseDto);
        }
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

    public GradeResponseDto getGradeInfo() {
        return gradeResponseDto;
    }

    public void setGradeInfo(GradeResponseDto gradeResponseDto) {
        this.gradeResponseDto = gradeResponseDto;
    }

    public AddressResponseDto getAddressInfo() {
        return addressResponseDto;
    }

    public void setAddressInfo(AddressResponseDto addressResponseDto) {
        this.addressResponseDto = addressResponseDto;
    }

    public List<SportsResponseDto> getSports() {
        return sports;
    }

    public void setSports(List<SportsResponseDto> sports) {
        this.sports = sports;
    }
}
