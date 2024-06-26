package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dao.GradeDao;
import com.i2i.sms.models.Grade;

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
public class GradeService {
    private GradeDao gradeDao = new GradeDao();
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
     */
    public Grade addGrade(int standard) {
        String section = allocateSection();
        Grade grade = new Grade();
        if (gradeDao.isStandardAndSectionAvailable(standard, section)) {
            grade = gradeDao.getGradeByStandardAndSection(standard, section);
        } else {
            grade.setStandard(standard);
            grade.setSection(section);
            grade = gradeDao.insertGrade(grade);
        }
        return grade;
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
    public String allocateSection() {
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
    public List<Grade> getAllGrades() {
        return gradeDao.retrieveAllGrades();
    }

}
