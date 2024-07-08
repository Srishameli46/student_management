package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.CreateStudentRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithAllDetailsDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.service.StudentService;
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
@RequestMapping("sms/api/v1/students")
public class StudentController {

    private static final Logger logger = LogManager.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    /**
     * <p>
     * Create student details such as name, Date of birth and standard.
     * </p>
     *
     * @param createStudentRequestDto This contains student name, date of birth, standard and address details.
     * @return StudentResponseDto that includes all the details of the student.
     **/
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentRequestDto createStudentRequestDto) {
        if (!DataValidationUtils.validString(createStudentRequestDto.getName())) {
            return new ResponseEntity<>("Invalid name format", HttpStatus.BAD_REQUEST);
        }
        if (!DateUtils.isValidPastDate(createStudentRequestDto.getDob())) {
            return new ResponseEntity<>("Invalid Date format", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.isValidGrade(createStudentRequestDto.getStandard())) {
            return new ResponseEntity<>("Invalid standard", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getStreet())) {
            return new ResponseEntity<>("Invalid street name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getCity())) {
            return new ResponseEntity<>("Invalid city name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getState())) {
            return new ResponseEntity<>("Invalid state name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validPinCode(createStudentRequestDto.getAddress().getPinCode())) {
            return new ResponseEntity<>("Invalid pin code", HttpStatus.BAD_REQUEST);
        }
        logger.info("Starting to create a new student");
        try {
            StudentResponseDto studentResponseDto = studentService.addStudent(createStudentRequestDto);
            return new ResponseEntity<>(studentResponseDto, HttpStatus.CREATED);
        } catch (StudentException e) {
            logger.error("Error creating student {}" , createStudentRequestDto.getName(), e);
            return new ResponseEntity<>("Error creating student " + createStudentRequestDto.getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Display student details such as name, Date of birth, Age, Id Along with associate grades.
     * </p>
     *
     * @return StudentResponseDto that contains all details of the students.
     **/
    @GetMapping
    public ResponseEntity<?> displayStudent() {
        logger.info("Displaying all students with their details.");
        try {
            List<StudentResponseDto> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                return new ResponseEntity<>("Students not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(students, HttpStatus.OK);
            }
        } catch (StudentException e) {
            logger.error("Error in retrieving students", e);
            return new ResponseEntity<>("Unable to retrieve students.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Search students by their Id.
     * </p>
     *
     * @param id This is the unique id that is uuid.
     * @return StudentWithSportsResponseDto provides student details along with sports activity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> searchStudent(@PathVariable String id) {
        logger.info("Search student id {}", id);
        try {
            StudentWithAllDetailsDto foundStudent = studentService.searchStudentById(id);
            if (null != foundStudent) {
                logger.info("Searched student ID: {} founded", id);
                return new ResponseEntity<>(foundStudent, HttpStatus.OK);
            } else {
                logger.info("Searched student ID: {} not founded", id);
                return new ResponseEntity<>("Student id  " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Unable to get student id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Remove students by their Id.
     * </p>
     *
     * @param id This is the unique id that must be numerical.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeStudent(@PathVariable String id) {
        System.out.println("\nRemove Student by Id");
        try {
            if (studentService.removeStudentById(id)) {
                logger.info("Removed student Id: {}", id);
                return new ResponseEntity<>("Removed student id " + id, HttpStatus.OK);
            } else {
                logger.info("Student Id: {} not found", id);
                return new ResponseEntity<>("Student id " + id + " not found to delete ", HttpStatus.NOT_FOUND);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Unable to delete student id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Update student details.
     * </p>
     *
     * @param id                      This is the unique id that must be uuid.
     * @param createStudentRequestDto {@link CreateStudentRequestDto}
     * @return StudentResponseDto that includes all the updated details of the student.
     **/
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody CreateStudentRequestDto createStudentRequestDto) {
        if (!DataValidationUtils.validString(createStudentRequestDto.getName())) {
            return new ResponseEntity<>("Invalid name format", HttpStatus.BAD_REQUEST);
        }
        if (!DateUtils.isValidPastDate(createStudentRequestDto.getDob())) {
            return new ResponseEntity<>("Invalid Date format", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.isValidGrade(createStudentRequestDto.getStandard())) {
            return new ResponseEntity<>("Invalid standard", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getStreet())) {
            return new ResponseEntity<>("Invalid street name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getCity())) {
            return new ResponseEntity<>("Invalid city name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createStudentRequestDto.getAddress().getState())) {
            return new ResponseEntity<>("Invalid state name", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validPinCode(createStudentRequestDto.getAddress().getPinCode())) {
            return new ResponseEntity<>("Invalid pin code", HttpStatus.BAD_REQUEST);
        }
        logger.info("Starting to update student with ID {}", id);
        try {
            StudentResponseDto studentResponseDto = studentService.updateStudent(id, createStudentRequestDto);
            if(null == studentResponseDto){
                return new ResponseEntity<>("Student id " + id + " not exists", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
        } catch (StudentException e) {
            logger.error("Error updating student with ID {}", id, e);
            return new ResponseEntity<>("Student not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Insert student into the sports activity according to their preference.
     * </p>
     *
     * @param id                            This is the unique student id that must be uuid.
     * @param createStudentSportsRequestDto This contains the preferred sport ids.
     * @return SportsResponseDto This gives the details of all the sports activity.
     */
    @PostMapping("/{id}/sports")
    public ResponseEntity<?> addStudentToSports(@PathVariable String id, @RequestBody CreateStudentSportsRequestDto createStudentSportsRequestDto) {
        logger.info("Assigning student id {} to sport ids", id);
        try {
            StudentWithAllDetailsDto student = studentService.searchStudentById(id);
            List<SportsResponseDto> sports = studentService.addStudentToSportActivity(id, createStudentSportsRequestDto);
            if (null == student) {
                logger.info("Student id {} not found", id);
                return new ResponseEntity<>("Student id " + id + "not found", HttpStatus.NOT_FOUND);
            } else if (sports.isEmpty()) {
                logger.info("Invalid sport ids {} ", createStudentSportsRequestDto.getSportIds());
                return new ResponseEntity<>("Invalid sport ids" + createStudentSportsRequestDto.getSportIds(), HttpStatus.NOT_FOUND);
            } else {
                logger.info("Student id {} added to sports activities", id);
                return new ResponseEntity<>("Student id " + id + " added to sports activities", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            logger.error("Error assigning student to sport", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

