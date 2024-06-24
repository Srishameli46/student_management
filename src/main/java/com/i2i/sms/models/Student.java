package com.i2i.sms.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing a student which contains details about the student 
 *    such as ID, name, date of birth, associated grade, address and their sportsActivity.
 * </p>
 */

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "dob")
  @Temporal(TemporalType.DATE)
  private Date dob;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "grade_id", nullable = false)
  private Grade grade;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)  @JoinTable(name = "student_sports_activity",
          joinColumns = @JoinColumn(name = "student_id"),
          inverseJoinColumns = @JoinColumn(name = "sport_id"))
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
           .append("\nStudent Dob      : ").append(dob)
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
