package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.GradeWithStudentsResponseDto;
import com.i2i.sms.models.Grade;

/**
 * <p>
 * The Grade interface represents a specific standard with a section and includes the
 * information about the students enrolled in that grade.
 * This class encapsulate all relevant information about grade and to provide actions like,
 * - Accessing and modifying that information, as well as for associating students with the grade.
 * </p>
 */
public interface GradeService {
    Grade addGrade(int standard);
    List<GradeWithStudentsResponseDto> getAllGrades();
    Grade getById(int gradeId);
}
