package com.i2i.sms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Grade;
import com.i2i.sms.service.GradeService;

/**
 * <p>
 * Grade controller handles all the operations related to the management based on the user's request.
 * It provide operation like displaying all students enrolled in the specific grade.
 * </p>
 */
public class GradeController {
    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

    private GradeService gradeService = new GradeService();

    /**
     * <p>
     * Display all Grade details along with student information.
     * </p>
     */
    public void displayGrade() {
        System.out.println("DISPLAY CLASSROOM DETAILS");
        logger.info("Displaying Grades along with the students");
        try {
            List<Grade> allGrades = gradeService.getAllGrades();
            if (allGrades.isEmpty()) {
                System.out.println("No classrooms available.\n");
            } else {
                for (Grade grade : allGrades) {
                    System.out.println(grade);
                }
            }
            logger.info("Retrieved Grades along with the students");
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }
}
