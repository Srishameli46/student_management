package com.i2i.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.sms.models.Grade;

/**
 * <p>
 * The Grade interface represents a specific standard with a section and includes the
 * information about the students enrolled in that grade.
 * This class is designed to encapsulate all relevant information about a grade and
 * to provide actions for accessing and modifying that information, as well as for
 * associating students with the grade.
 * </p>
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    Grade findByStandardAndSection(int standard, String section);
    boolean existsByStandardAndSection(int standard, String section);
}