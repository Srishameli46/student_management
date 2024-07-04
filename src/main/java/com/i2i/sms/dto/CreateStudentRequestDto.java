package com.i2i.sms.dto;

import java.util.Date;
import java.util.List;

public class CreateStudentRequestDto {
    private String name;
    private Date dob;
    private int standard;
    private CreateAddressRequestDto createAddressRequestDto;

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

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public CreateAddressRequestDto getCreateAddressRequestDto() {
        return createAddressRequestDto;
    }

    public void setCreateAddressRequestDto(CreateAddressRequestDto createAddressRequestDto) {
        this.createAddressRequestDto = createAddressRequestDto;
    }
}
