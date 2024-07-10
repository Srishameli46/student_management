package com.i2i.sms.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;
import com.i2i.sms.repository.SportsActivityRepository;

/**
 * <p>
 * This sports activity class represents basic details such as sports name, tutor name, venue, sport Id.
 * Additionally, it stored information about the student's such as name, studentId, grade details and the address detail.
 * Manage the information by the following operation like creating, retrieving and removing the sports activity along with the associated students.
 * </p>
 */
@Service
public class SportsActivityServiceImpl implements SportsActivityService {
    private static final Logger logger = LogManager.getLogger(SportsActivityServiceImpl.class);
    @Autowired
    private SportsActivityRepository sportsActivityRepository;

    /**
     * <p>
     * Create new sports Activity that students need to participate .
     * This contains the details of the sports such as sport id, sport name, venue and sport tutor which has to be added.
     * </p>
     *
     * @param createSportsRequestDto sports details contains sport name, venue, tutor name.
     * @return SportsResponseDto this provides all the information of that sport.
     */
    public SportsResponseDto addSport(CreateSportsRequestDto createSportsRequestDto) {
        logger.debug("Started to create sports");
        try {
            SportsActivity sportsActivity = new SportsActivity();
            sportsActivity.setSportName(createSportsRequestDto.getSportName());
            sportsActivity.setVenue(createSportsRequestDto.getVenue());
            sportsActivity.setTutorName(createSportsRequestDto.getTutorName());
            return new SportsResponseDto(sportsActivityRepository.save(sportsActivity));
        } catch (Exception e) {
            logger.error("An error occurred while saving the sports: {}", createSportsRequestDto.getSportName(), e);
            throw new StudentException("Failed to save sport " + createSportsRequestDto.getSportName(), e);
        }
    }

    /**
     * <p>
     * Get all the students detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @return details of all the sports activity
     * such as sport id, sport name, venue and sport tutor which has to be added.
     * @throws StudentException when the sports can not be accessed.
     */
    public List<SportsResponseDto> getAllSportsActivities() {
        logger.debug("Started to display student details");
        try {
            List<SportsActivity> allSports = sportsActivityRepository.findAll();
            return allSports.stream().map(SportsResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the all sports", e);
            throw new StudentException("Failed to retrieving all sports details", e);
        }
    }

    /**
     * <p>
     * Remove sport Activity by the sport Id that provided.
     * </p>
     *
     * @param sportId SportId is get from the user that should be allowed only in numerical.
     * @return true if the sportActivity deleted or else return false.
     * @throws StudentException when the sports can not be deleted.
     */
    public boolean removeSportById(String sportId) {
        logger.debug("Started to remove sports");
        try {
            if (sportsActivityRepository.existsById(sportId)) {
                sportsActivityRepository.deleteById(sportId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting the sport id: {}", sportId, e);
            throw new StudentException("Failed to delete sport id " + sportId, e);
        }
    }

    /**
     * <p>
     * Get all the sport detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @param id SportId is get from the user that should be allowed only in numerical.
     * @return details of all the sports details in that particular sports activity.
     * @throws StudentException when the sports can not be accessed.
     */
    public Optional<SportsActivity> getSportDetailsById(String id) {
        logger.debug("Started to retrieve sports");
        Optional<SportsActivity> sports = sportsActivityRepository.findById(id);
        return sports;
    }

    /**
     * <p>
     * Get all the students detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @param sportId SportId is get from the user that should be allowed only in numerical.
     * @return details of all the students in that particular sports activity.
     * @throws StudentException when the sports can not be accessed.
     */
    public List<StudentResponseDto> getStudentsInSport(String sportId) {

        try {
            Optional<SportsActivity> sportsActivity = getSportDetailsById(sportId);
            if (!sportsActivity.isPresent()) {
                logger.warn("No sports activity found with id: {}", sportId);
                return Collections.emptyList();
            }
            List<Student> students = sportsActivityRepository.findStudentsBySports(sportId);
            return students.stream().map(StudentResponseDto::new).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("An error occurred while retrieving the students in sport id: {}", sportId, e);
            throw new StudentException("Failed to retrieve the students in sport id " + sportId, e);
        }
    }

    /**
     * <p>
     * Update sports Activity that students need to participate .
     * This updates the details of the sports such as sport name, venue and sport tutor which has to be added.
     * </p>
     *
     * @param id                     This is the unique sports id that is represented in uuid.
     * @param createSportsRequestDto sports details contains sport name, venue, tutor name.
     * @return SportsResponseDto this provides all the information of that sport.
     * @throws StudentException when the sports can not be updated.
     */
    public SportsResponseDto updateSports(String id, CreateSportsRequestDto createSportsRequestDto) {
        try {
            logger.debug("Started to update sports details");
            Optional<SportsActivity> sports = sportsActivityRepository.findById(id);
            if (sports.isPresent()) {
                SportsActivity sportsActivity = sports.get();
                sportsActivity.setSportName(createSportsRequestDto.getSportName());
                sportsActivity.setVenue(createSportsRequestDto.getVenue());
                sportsActivity.setTutorName(createSportsRequestDto.getTutorName());
                sportsActivity = sportsActivityRepository.save(sportsActivity);
                return new SportsResponseDto(sportsActivity);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating the sports: {}", createSportsRequestDto.getSportName(), e);
            throw new StudentException("Failed to update sport with ID " + id, e);
        }
    }

    /**
     * <p>
     * Check whether the given sports Activity present or not .
     * </p>
     *
     * @param sportId This is the unique sport id represented in uuid.
     * @return true if the sport activity presents else return false.
     */
    public boolean isSportsActivityExist(String sportId) {
        return sportsActivityRepository.existsById(sportId);
    }
}
