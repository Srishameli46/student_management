package com.i2i.sms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

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

    @JsonIgnore
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

}
