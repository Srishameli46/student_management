package com.i2i.sms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * <p>
 * Class representing a student which contains details about the student
 * such as name, date of birth, associated grade, address.
 * </p>
 */
public class CreateStudentRequestDto {
    @NotBlank(message = "name should not be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Student name should contain only letters and space")
    private String name;
    @Past(message = "Dob should not have future dates")
    @NotNull(message = "Dob can not be null")
    private LocalDate dob;
    @Valid
    private CreateStandardDto grade;
    @Valid
    private CreateAddressRequestDto address;

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

    public @Valid CreateStandardDto getGrade() {
        return grade;
    }

    public void setGrade(@Valid CreateStandardDto grade) {
        this.grade = grade;
    }

    public CreateAddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(CreateAddressRequestDto createAddressRequestDto) {
        this.address = createAddressRequestDto;
    }
}
