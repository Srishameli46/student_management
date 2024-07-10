package com.i2i.sms.dto;

import jakarta.validation.constraints.Pattern;

/**
 * <p>
 * Class representing the address of the student which contains details
 * such as door number, street name, city, state, pin code.
 * </p>
 */
public class CreateAddressRequestDto {
    private String doorNo;
    private String street;
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City name should contain only letters and space")
    private String city;
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name should contain only letters and space")
    private String state;
    @Pattern(regexp = "^\\d{5,6}(?:[-\\s]\\d{4})?$", message = "Pin code should contain only 5 or 6 digits")
    private String pinCode;
    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
