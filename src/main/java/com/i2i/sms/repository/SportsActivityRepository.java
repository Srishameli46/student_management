package com.i2i.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This sports activity class represents basic details such as sports name, tutor name, venue, sport Id.
 * Additionally, it stored information about the student's such as name, studentId, grade details and the address detail.
 * Manage the information by the following operation like creating, retrieving and removing the sports activity .
 * </p>
 */
@Repository
public interface SportsActivityRepository extends JpaRepository<SportsActivity, String> {
    public static final String FIND_STUDENTS_IN_SPORTS = "SELECT s FROM Student s JOIN s.sportsActivities sa WHERE sa.sportId = :sportId";

    @Query(FIND_STUDENTS_IN_SPORTS)
    List<Student> findStudentsBySports(@Param("sportId") String sportId);
}
