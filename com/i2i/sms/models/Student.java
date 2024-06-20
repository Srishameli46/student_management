package com.i2i.sms.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing a student which contains details about the student such as ID, name, date of birth, associated grade, address and their sportsActivity.
 * </p>
 */
public class Student {
  private int id;
  private String name;
  private Date dob;
  private Address address;
  private Grade grade;
  private Set<SportsActivity> sportsActivities = new HashSet<>();
   
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }
  
  public Date getDob() {
    return dob;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
 
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  } 

  public Grade getGrade() {
    return grade;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  } 

  public Set<SportsActivity> getSportsActivities() {
    return sportsActivities != null ? sportsActivities : new HashSet<>();
  }

  public void setSportsActivities(Set<SportsActivity> sportsActivities) {
    this.sportsActivities = sportsActivities;
  }

  public void addSportsActivity(SportsActivity sportsActivity) {
    if(null == sportsActivities) {
       sportsActivities = new HashSet<>();
    }
    if(!sportsActivities.contains(sportsActivity)) {
       sportsActivities.add(sportsActivity);
    }
  }

  public void removeSportsActivity(SportsActivity sportsActivity) {
    if(sportsActivities != null && sportsActivities.contains(sportsActivity)) {
      sportsActivities.remove(sportsActivity);
    }
  }

  public String toString() {
    StringBuilder details = new StringBuilder();
    details.append("\nStudent ID        : ").append(id)
           .append("\nStudent Name      : ").append(name) 
           .append("\ntStudent Dob      : ").append(dob)
           .append("\nStudent Age       : ").append(DateUtils.calculatePeriodDifference(dob))
           .append("\nStudent GradeId   : ").append(grade != null? grade.getGradeId() : "No grade Id")
           .append("\nStudent Standard  : ").append(grade != null ? grade.getStandard() : "No Standard")
           .append("\nStudent Section   : ").append(grade != null ? grade.getSection() : "No Section");

     if (null != address) {
        details.append(address.toString());
     } else {
        details.append("\nAddress Details  : No address");
     }
     return details.toString();
   }
}
