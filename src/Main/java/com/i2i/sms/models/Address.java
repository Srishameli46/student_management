package com.i2i.sms.models;

import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing the address of the student which contains details 
 *    such as door number, street name, city, state, pin code and associate studentId.
 * </p>
 */
public class Address {
  private int addressId;
  private String doorNo;
  private String street;
  private String city;
  private String state;
  private String pinCode;
  private Student student;

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

  public Student getStudent() {
    return this.student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public String toString() {
    StringBuilder details = new StringBuilder();
      details.append("\nAddress :")
             .append(doorNo).append(", ")
             .append(street).append(", ")
             .append(city).append(", ")
             .append(state).append(", ")
             .append(pinCode).append("\n");
    return details.toString();
  }  
}
