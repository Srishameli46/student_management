package com.i2i.sms.dto;
import com.i2i.sms.models.Address;

public class AddressResponseDto {

    private int addressId;
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private String pinCode;

    public AddressResponseDto(Address address) {
        this.addressId = address.getAddressId();
        this.doorNo = address.getDoorNo();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.pinCode = address.getPinCode();
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
