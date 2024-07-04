package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.dto.GradeWithStudentsResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.service.GradeService;

/**
 * <p>
 * Grade controller handles all the operations related to the management based on the user's request.
 * It provide operation like displaying all students enrolled in the specific grade.
 * </p>
 */
@RestController
@RequestMapping("sms/api/1.0/grades")
public class GradeController {
    private static final Logger logger = LogManager.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    /**
     * <p>
     * Display all Grade details along with student information.
     * </p>
     */
    @GetMapping
    public ResponseEntity<List<GradeWithStudentsResponseDto>> displayGrade() {
        System.out.println("DISPLAY CLASSROOM DETAILS");
        logger.info("Displaying Grades along with the students");
        try {
            List<GradeWithStudentsResponseDto> allGrades = gradeService.getAllGrades();
            logger.info("Retrieved Grades along with the students");
            return new ResponseEntity<>(allGrades,HttpStatus.FOUND);
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
