package com.i2i.sms.models;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class representing a sports activity which contains details about the sports
 *    such as sport ID,sport name, venue, tutor name.
 * </p>
 */

@Entity
@Table(name = "sports_activity")
public class SportsActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sport_id")
    private String sportId;

    @Column(name = "sport_name", nullable = false, length = 25)
    private String sportName;

    @Column(name = "venue", nullable = false, length = 20)
    private String venue;

    @Column(name = "tutor_name", nullable = false, length = 30)
    private String tutorName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_sports_activity",
            joinColumns = @JoinColumn(name = "sport_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
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

    public Set<Student> getStudents() {
        return students != null ? students : new HashSet<>();
    }

    public void setStudents(Set<Student> students) {
       this.students = students;
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
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
                .append(" Tutor Name : ").append(tutorName).append("\n");
        return details.toString();
    }
}
