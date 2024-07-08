package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.service.SportsActivityService;
import com.i2i.sms.utils.DataValidationUtils;
import com.i2i.sms.utils.DateUtils;


/**
 * <p>
 * Sports activity controller handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new sports activity details, adding students to their related sports along with the other operations,includes
 * - retrieving the whole sports details along with their associated student and grade details.
 * - removing the student from all associated sport activities or remove the whole sport.
 * </p>
 */
@RestController
@RequestMapping("sms/api/v1/sportsActivities")
public class SportsActivityController {
    private static final Logger logger = LogManager.getLogger(SportsActivityController.class);
    @Autowired
    private SportsActivityService sportsActivityService;

    /**
     * <p>
     * Create new sport activity that students will participate.
     * This includes sport Id, sport name, venue, tutor name.
     * </p>
     *
     * @param createSportsRequestDto This provides the details of the sport activity like name, venue, tutor name.
     * @return SportsResponseDto This gives details of the sports activity.
     */
    @PostMapping
    public ResponseEntity<?> addSports(@RequestBody CreateSportsRequestDto createSportsRequestDto) {
        logger.info("Starting to create a new sport activity");
        if (!DataValidationUtils.validString(createSportsRequestDto.getSportName())) {
            return new ResponseEntity<>("Invalid sport name format", HttpStatus.BAD_REQUEST);
        }
        if (!DataValidationUtils.validString(createSportsRequestDto.getTutorName())) {
            return new ResponseEntity<>("Invalid tutor name format", HttpStatus.BAD_REQUEST);
        }
        try {
            SportsResponseDto sportsActivityInfo = sportsActivityService.addSport(createSportsRequestDto);
            if (null == sportsActivityInfo) {
                logger.info("Failed to create sport activity {}", createSportsRequestDto.getSportName());
                return new ResponseEntity<>("Failed to create sport activity " + createSportsRequestDto.getSportName(), HttpStatus.BAD_REQUEST );
            } else {
                logger.info("Created sports activity {}", createSportsRequestDto.getSportName());
                return new ResponseEntity<>(sportsActivityInfo, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            logger.error("Error creating sport activity {}",createSportsRequestDto.getSportName(), e);
            return new ResponseEntity<>("Unable to create sport activity " + createSportsRequestDto.getSportName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Display all sport activity that students will participate.
     * This includes sport Id, sport name, venue, tutor name and the start date.
     * </p>
     *
     * @return SportsResponseDto This gives details of all the sports activity.
     */
    @GetMapping
    public ResponseEntity<?> displayAllSports() {
        logger.info("Displaying all sports activity details");
        try {
            List<SportsResponseDto> sports = sportsActivityService.getAllSportsActivities();
            if (sports.isEmpty()) {
                logger.info("No sports details available.");
                return new ResponseEntity<>("No sports available", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(sports, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error displaying sports", e);
            return new ResponseEntity<>("Error in retrieving sports activities", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Display students details in the particular sport activity.
     * This provides student details along with their associated grade and address details.
     * </p>
     *
     * @param id This is the unique sport id that must be uuid.
     * @return StudentResponseDto contains details of the student along with grade.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> displayStudentsInSport(@PathVariable String id) {
        logger.info("Displaying students in sport id {}", id);
        try {
            if(sportsActivityService.isSportsActivityExist(id)) {
                List<StudentResponseDto> students = sportsActivityService.getStudentsInSport(id);
                if (students.isEmpty()) {
                    logger.info("No students available in sport id {}", id);
                    return new ResponseEntity<>("No students available in sport id " + id ,HttpStatus.OK);
                } else {
                    logger.info("Retrieved students in sport id {}", id);
                    return new ResponseEntity<>(students, HttpStatus.OK);
                }
            } else {
                logger.info("No sport id {}", id);
                return new ResponseEntity<>("No sport id " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error displaying students in sport", e);
            return new ResponseEntity<>("Error in retrieving students in sport id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * <p>
     * Remove sports details in the particular sport activity.
     * This will remove the information includes sport Id, sport name, venue, tutor name.
     * </p>
     *
     * @param id This is the unique sport id that must be uuid.
     */
    @DeleteMapping("/{id}")
    private ResponseEntity<?> removeSportById(@PathVariable String id) {
        try {
            if (sportsActivityService.removeSportById(id)) {
                logger.info("Removed sport id {}", id);
                return new ResponseEntity<>("Removed sport id " + id, HttpStatus.OK);
            } else {
                logger.info("Failed to remove sport id {}", id);
                return new ResponseEntity<>("Sport id " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Error in removing sport activities", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
