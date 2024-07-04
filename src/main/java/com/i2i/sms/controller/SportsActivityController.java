package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.service.SportsActivityService;

/**
 * <p>
 * Sports activity controller handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new sports activity details, adding students to their related sports along with the other operations,includes
 * - retrieving the whole sports details along with their associated student and grade details.
 * - removing the student from all associated sport activities or remove the whole sport.
 * </p>
 */
@RestController
@RequestMapping("sms/api/1.0/sportsActivities")
public class SportsActivityController {
    private static final Logger logger = LogManager.getLogger(SportsActivityController.class);
    @Autowired
    private SportsActivityService sportsActivityService;

    /**
     * <p>
     * Insert student into the sports activity according to their preference.
     * </p>
     */
    @PostMapping("/{id}/sports")
    public ResponseEntity<List<SportsResponseDto>> addStudentToSports(@PathVariable int id, @RequestBody CreateStudentSportsRequestDto createStudentSportsRequestDto) {
        logger.info("Assigning student id {} to sport ids", id);
        try {
            return new ResponseEntity<>(sportsActivityService.addStudentToSportActivity(id, createStudentSportsRequestDto), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error assigning student to sport", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Create new sport activity that students will participate.
     * This includes sport Id, sport name, venue, tutor name and the start date.
     * </p>
     */
    @PostMapping
    public ResponseEntity<SportsResponseDto> addSports(@RequestBody CreateSportsRequestDto createSportsRequestDto) {
        logger.info("Starting to create a new sport activity");
        try {
            SportsResponseDto sportsActivityInfo = sportsActivityService.addSport(createSportsRequestDto);
            return new ResponseEntity<>(sportsActivityInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating sport activity", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Display all sport activity that students will participate.
     * This includes sport Id, sport name, venue, tutor name and the start date.
     * </p>
     */
    @GetMapping
    public ResponseEntity<List<SportsResponseDto>> displayAllSports() {
        logger.info("Displaying all sports activity details");
        try {
            List<SportsResponseDto> sports = sportsActivityService.getAllSportsActivities();
            if (sports.isEmpty()) {
                logger.info("No sports details available.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sports, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error displaying sports", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * <p>
     * Display students details in the particular sport activity.
     * This provides student details along with their associated grade and address details.
     * </p>
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<StudentResponseDto>> displayStudentsInSport(@PathVariable int id) {
        logger.info("Displaying students in sport id {}", id);
        try {
            List<StudentResponseDto> students = sportsActivityService.getStudentsInSport(id);
            if (students.isEmpty()) {
                logger.info("No students Available in sport id {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error displaying students in sport", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Remove sports details in the particular sport activity.
     * This will remove the information includes sport Id, sport name, venue, tutor name and the start date.
     * </p>
     */
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> removeSportById(@PathVariable int id) {
        try {
            if (sportsActivityService.removeSportById(id)) {
                System.out.println("Sport id " + id + " removed successfully");
                logger.info("Removed sport id {}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                System.out.println("Failed to remove sport");
                logger.info("failed to remove sport id {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
