package com.i2i.sms.dto;

import com.i2i.sms.models.Address;

/**
 * <p>
 * Class representing the address of the student which contains details
 * such as door number, street name, city, state, pin code.
 * </p>
 */
public class AddressDto {
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private String pinCode;

    public AddressDto(Address address) {
        this.doorNo = address.getDoorNo();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.pinCode = address.getPinCode();
    }

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
