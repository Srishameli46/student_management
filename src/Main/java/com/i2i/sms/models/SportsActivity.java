package com.i2i.sms.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.i2i.sms.models.Student;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Class representing a sports activity which contains details about the sports such as sport ID,sport name, venue, tutor name, start date.
 * </p>
 */
public class SportsActivity {
  private int sportId;
  private String sportName;
  private String venue;
  private String tutorName;
  private Date startDate;
  private Set<Student> students = new HashSet<>();

  public int getSportId() {
    return sportId;
  }

  public void setSportId(int sportId) {
    this.sportId = sportId;
  }

  public String getSportName() {
    return sportName;
  }

  public void setSportName(String sportName) {
     this.sportName = sportName;
  }

  public String getVenue() {
     return venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public String getTutorName() {
    return tutorName;
  }

  public void setTutorName(String tutorName) {
    this.tutorName = tutorName;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Set<Student> getStudents() {
    return students != null ? students : new HashSet<>();
  }
  
  public void setStudents(Set<Student> students) { 
    this.students = students;
  }

  public void addStudent(Student student) {
    if (!students.contains(student)) {
       students.add(student);
       student.addSportsActivity(this);
    }
  }

  public void removeStudent(Student student) {
    if (students.contains(student)) {
        students.remove(student);
        student.removeSportsActivity(this);
    }
  }

  public String toString() {
    StringBuilder details = new StringBuilder();
       details.append("....SPORTS ACTIVITY....\n")
            .append(" Sports Id : ").append(sportId).append("\n")
            .append(" Sports Name : ").append(sportName).append("\n")
            .append(" Venue : ").append(venue).append("\n")
            .append(" Tutor Name : ").append(tutorName).append("\n")
            .append(" Start Date : ").append(startDate).append("\n");
    return details.toString();
  }
}
