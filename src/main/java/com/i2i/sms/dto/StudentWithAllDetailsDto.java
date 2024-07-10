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
public class StudentWithAllDetailsDto {
    private String id;
    private String name;
    private LocalDate dob;
    private int age;
    private GradeDto grade;
    private AddressDto address;
    private List<SportsResponseDto> sports = new ArrayList<>();

    public StudentWithAllDetailsDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dob = student.getDob();
        this.age = DateUtils.calculatePeriodDifference(dob);
        this.grade = new GradeDto(student.getGrade());
        this.address = new AddressDto(student.getAddress());
        for (SportsActivity sportsActivity : student.getSportsActivities()) {
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

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<SportsResponseDto> getSports() {
        return sports;
    }

    public void setSports(List<SportsResponseDto> sports) {
        this.sports = sports;
    }


}
