package com.i2i.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final Map<String, Integer> sectionCounts = new HashMap<String, Integer>() {
        {
            put("A", 0);
            put("B", 0);
        }
    };
    private static final int maximumStudentPerSection = 4;

    /**
     * <p>
     * Get standard, set section and if the standard already exists, just add the student details along with the grade list.
     * If the grade not present, it will create new grade with provided standard and allocated section.
     * </p>
     *
     * @param standard The standard given for the student as 1 to 12.
     * @return grade details which includes section, standard, and gradeId.
     * @throws StudentException when the grade can not be created.
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
            throw new StudentException("Failed to save standard " + standard, e);
        }
    }

    /**
     * <p>
     * Allocate section for each standard.
     * </p>
     * <p>
     * Students will allocate for 'A' section when it reaches count 4,
     * it starts with 'B' section.
     * </p>
     *
     * @return Section as String
     * @throws StudentException when both sections are full.
     */
    private String allocateSection() {
        if (sectionCounts.get("A") < maximumStudentPerSection) {
            sectionCounts.put("A", sectionCounts.get("A") + 1);
            return "A";
        } else if (sectionCounts.get("B") < maximumStudentPerSection) {
            sectionCounts.put("B", sectionCounts.get("B") + 1);
            return "B";
        } else {
            throw new StudentException("Both sections are full");
        }
    }

    /**
     * <p>
     * Display all Grades along with student details.
     * </p>
     *
     * @return all the standard and sections within the grade along with their students.
     * @throws StudentException when the grade can not be accessed.
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
     * This method gets all the students in a particular grade and section.
     * </p>
     *
     * @param id The grade id for which the students have to be fetched.
     * @return GradeWithStudentResponseDto {@link GradeWithStudentsResponseDto} .
     * @throws StudentException when the grade can not be accessed.
     */
    public GradeWithStudentsResponseDto findStudentsByGradeId(String id) {
        try {
            logger.debug("Finding students in grade id {}", id);
            Optional<Grade> existingGrade = gradeRepository.findById(id);
            if (existingGrade.isPresent()) {
                Grade grade = existingGrade.get();
                return new GradeWithStudentsResponseDto(grade);
            }
            return null;
        } catch (Exception e) {
            throw new StudentException("Unable to find students in grade id " + id, e);
        }
    }

    /**
     * <p>
     * Checks if a grade exists for a given grade ID.
     * </p>
     *
     * @param gradeId The ID of the grade to check.
     * @return true if the grade exists, false otherwise.
     */
    public boolean isGradeAvailable(String gradeId) {
        return gradeRepository.existsById(gradeId);
    }
}
