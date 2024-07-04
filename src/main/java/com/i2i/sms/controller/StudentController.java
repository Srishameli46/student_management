package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.CreateStudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithSportsResponseDto;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.utils.DataValidationUtils;
import com.i2i.sms.utils.DateUtils;


/**
 * <p>
 * Student controller handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new student details, grade details and address along with the other operations,includes
 * - retrieving the whole student details along with their associated grade and sports activity
 * - removing the student from all associated grade, address detail and the sports activities.
 * </p>
 */
@RestController
@RequestMapping("sms/api/1.0/students")
public class StudentController {

    private static final Logger logger = LogManager.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    /**
     * <p>
     * Get and create student details such as name, Date of birth and standard.
     * </p>
     **/
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody CreateStudentRequestDto createStudentRequestDto) {
        if (!DataValidationUtils.validString(createStudentRequestDto.getName())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(!DateUtils.isValidDate(createStudentRequestDto.getDob())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.isValidGrade(createStudentRequestDto.getStandard())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getCreateAddressRequestDto().getStreet())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getCreateAddressRequestDto().getCity())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getCreateAddressRequestDto().getState())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validPinCode(createStudentRequestDto.getCreateAddressRequestDto().getPinCode())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        logger.info("Starting to create a new student");
        try {
            StudentResponseDto studentResponseDto = studentService.addStudent(createStudentRequestDto);
            return new ResponseEntity<>(studentResponseDto, HttpStatus.CREATED);
        } catch (StudentException e) {
            logger.error("Error creating student", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Display student details such as name, Date of birth, Age, Id Along with associate grades.
     * </p>
     **/
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> displayStudent() {
        logger.info("Displaying all students with their details.");
        try {
            return new ResponseEntity<>(studentService.getAllStudents(),HttpStatus.FOUND);
        } catch (StudentException e) {
            logger.error("Error in retrieving students", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Search students by their Id.
     * </p>
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentWithSportsResponseDto> searchStudent(@PathVariable int id) {
        logger.info("Search student id {}", id);
        try {
            StudentWithSportsResponseDto foundStudent = studentService.searchStudentById(id);
            if (null != foundStudent) {
                logger.info("Searched student ID: {} founded", id);
                return new ResponseEntity<>(foundStudent, HttpStatus.FOUND);
            } else {
                System.out.println("Student not found\n");
                logger.info("Searched student ID: {} not founded", id);
                return new ResponseEntity<>(foundStudent, HttpStatus.NOT_FOUND);
            }
        } catch (StudentException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Remove students by their Id.
     * </p>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeStudent(@PathVariable int id) {
        System.out.println("\nRemove Student by Id");
        try {
            studentService.removeStudentById(id);
            logger.info("Removed student Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

