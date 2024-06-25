package com.i2i.sms.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class representing a grade, which can have multiple students.
 * This class provides methods to manage the students associated with their standard and section.
 * </p>
 */

@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @Column(name = "standard")
    private int standard;

    @Column(name = "section")
    private String section;

    @OneToMany(mappedBy = "grade", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getStandard() {
        return standard;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setStudent(Set<Student> students) {
        this.students = students;
    }

    public Set<Student> getStudent() {
        return students != null ? students : new HashSet<>();
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setGrade(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setGrade(null);
    }

    public String toString() {
        StringBuilder gradeDetails = new StringBuilder("CLASSROOM DETAILS\nStandard: " + standard + "\nSection: " + section + "\nGradeId: " + gradeId + "\n----------Students---------");
        if (students.isEmpty()) {
            gradeDetails.append("\nNo students enrolled\n");
        } else {
            for (Student student : students) {
                gradeDetails.append(student.toString()).append("\n");
            }
        }
        return gradeDetails.toString();
    }
}
  
  