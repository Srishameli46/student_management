package com.i2i.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This student interface represents basic details such as students name, date of birth and student Id.
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retrieving and removing the students.
 * </p>
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
}