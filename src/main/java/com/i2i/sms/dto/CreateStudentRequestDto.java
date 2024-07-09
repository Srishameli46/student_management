package com.i2i.sms.dto;

import java.time.LocalDate;

/**
 * <p>
 * Class representing a student which contains details about the student
 * such as name, date of birth, associated grade, address.
 * </p>
 */
public class CreateStudentRequestDto {
    private String name;
    private LocalDate dob;
    private int standard;
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

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public CreateAddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(CreateAddressRequestDto createAddressRequestDto) {
        this.address = createAddressRequestDto;
    }
}
