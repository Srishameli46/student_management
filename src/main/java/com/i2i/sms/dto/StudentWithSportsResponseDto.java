package com.i2i.sms.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing a student which contains details about the student
 * such as ID, name, date of birth, associated grade, address and their sportsActivity.
 * </p>
 */
public class StudentWithSportsResponseDto {
    private String id;
    private String name;
    private LocalDate dob;
    private int age;
    private GradeResponseDto grade;
    private AddressResponseDto address;
    private List<SportsResponseDto> sports = new ArrayList<>();

    public StudentWithSportsResponseDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dob = student.getDob();
        this.age = DateUtils.calculatePeriodDifference(dob);
        this.grade = new GradeResponseDto(student.getGrade());
        this.address = new AddressResponseDto(student.getAddress());
        for(SportsActivity sportsActivity : student.getSportsActivities()) {
            SportsResponseDto sportsResponseDto = new SportsResponseDto(sportsActivity);
            this.sports.add(sportsResponseDto);
        }
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

    public GradeResponseDto getGradeInfo() {
        return grade;
    }

    public void setGradeInfo(GradeResponseDto gradeResponseDto) {
        this.grade = gradeResponseDto;
    }

    public AddressResponseDto getAddressInfo() {
        return address;
    }

    public void setAddressInfo(AddressResponseDto addressResponseDto) {
        this.address = addressResponseDto;
    }

    public List<SportsResponseDto> getSports() {
        return sports;
    }

    public void setSports(List<SportsResponseDto> sports) {
        this.sports = sports;
    }
}
