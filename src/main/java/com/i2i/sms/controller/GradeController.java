package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.sms.dto.GradeWithStudentsResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.service.GradeService;

/**
 * <p>
 * Grade controller handles all the operations related to the management based on the user's request.
 * It provide operation like displaying all students enrolled in the specific grade.
 * </p>
 */
@RestController
@RequestMapping("/v1/grades")
public class GradeController {
    private static final Logger logger = LogManager.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    /**
     * <p>
     * Display all Grade details along with student information.
     * </p>
     *
     * @return GradeWithStudentsResponseDto contains the details of the grade like standard, section and gradeId along with student details.
     */
    @GetMapping
    public ResponseEntity<?> displayGrade() {
        System.out.println("DISPLAY CLASSROOM DETAILS");
        logger.info("Displaying Grades along with the students");
        try {
            List<GradeWithStudentsResponseDto> allGrades = gradeService.getAllGrades();
            if (allGrades.isEmpty()) {
                logger.info("No Grades available");
                return new ResponseEntity<>("No grades exists", HttpStatus.NOT_FOUND);
            } else {
                logger.info("Retrieved grades along with their students");
                return new ResponseEntity<>(allGrades, HttpStatus.OK);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Unable to retrieve grades", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * This method is takes the grade and section as the input from the user and
     * displays the list of students in the particular grade and section.
     * </p>
     *
     * @param gradeId The unique identifier of the grade for which the grade details and the students has to be fetched.
     * @return {@link GradeWithStudentsResponseDto} with http status, else appropriate error message.
     */
    @GetMapping("/{gradeId}")
    public ResponseEntity<?> displayStudentsByGradeId(@PathVariable String gradeId) {
        logger.debug("Displaying students in grade id {}", gradeId);
        try {
            if (gradeService.isGradeAvailable(gradeId)) {
                GradeWithStudentsResponseDto grade = gradeService.findStudentsByGradeId(gradeId);
                if (null != grade) {
                    logger.info("Displayed students in grade id {}", gradeId);
                    return new ResponseEntity<>(grade, HttpStatus.OK);
                } else {
                    logger.info("Students not found for grade id {}", gradeId);
                    return new ResponseEntity<>("Students not found for grade id " + gradeId, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Grade Id " + gradeId + " does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
