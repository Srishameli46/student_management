package com.i2i.sms.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.GradeWithStudentsResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Grade;
import com.i2i.sms.repository.GradeRepository;

/**
 * <p>
 * The Grade class represents a specific standard with a section and includes the
 * information about the students enrolled in that grade.
 * <p>
 * It captures the unique ID for the grade, the standard, and the section identifier.
 * <p>
 * This class encapsulate all relevant information about grade and to provide actions like,
 * - Accessing and modifying that information, as well as for associating students with the grade.
 * </p>
 */
@Service
public class GradeServiceImpl implements GradeService {
    private static final Logger logger = LogManager.getLogger(GradeServiceImpl.class);
    @Autowired
    private GradeRepository gradeRepository;
    private int sectionCount = 0;
    private final String[] sections = {"A", "B"};

    /**
     * <p>
     * Get standard, set section and if the standard already exists, just add the student details along with the grade list.
     * If the grade not present, it will create new grade with provided standard and allocated section.
     * </p>
     *
     * @param standard The standard given for the student as 1 to 12.
     * @return grade details which includes section, standard, and gradeId.
     *
     */
    public Grade addGrade(int standard) {
        try {
            String section = allocateSection();
            Grade grade;
            if (gradeRepository.existsByStandardAndSection(standard, section)) {
                grade = gradeRepository.findByStandardAndSection(standard, section);
            } else {
                grade = new Grade();
                grade.setStandard(standard);
                grade.setSection(section);
                grade = gradeRepository.save(grade);
            }
            return grade;
        } catch (Exception e) {
            logger.error("An error occurred while saving the standard: {}", standard, e);
            throw new StudentException("Failed to save standard  " + standard, e);
        }
    }


    /**
     * <p>
     * Allocate section for each standards.
     * </p>
     * <p>
     * Ex : Id=1, Section=A
     * Ex : Id=22, Section=B
     * Ex : Id=12, Section=A
     * </p>
     *
     * @return Section as String
     */
    private String allocateSection() {
        // For first Id it allocated 'A' section, next Id with 'B' section in iterative manner
        String section = sections[sectionCount % sections.length];
        sectionCount++;
        return section;
    }

    /**
     * <p>
     * Display all Grades along with student details.
     * </p>
     *
     * @return all the standard and sections within the grade along with their students.
     */
    public List<GradeWithStudentsResponseDto> getAllGrades() {
        try {
            List<Grade> allGrades = gradeRepository.findAll();
            return allGrades.stream().map(GradeWithStudentsResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the grades", e);
            throw new StudentException("Failed to get all details ", e);
        }
    }

    /**
     * <p>
     * Retrieve Grade details along with student details by gradeId.
     * </p>
     * @param gradeId It is the unique integer.
     * @return the standard and section within the gradeId along with their students.
     */
    public Grade getById(int gradeId) throws StudentException{
        try {
            return gradeRepository.findById(gradeId)
                    .orElseThrow(() -> new EntityNotFoundException("Grade not found"));
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the grade id: {}", gradeId, e);
            throw new StudentException("Failed to retrieved grade with ID " + gradeId, e);
        }
    }
}
