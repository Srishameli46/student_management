package com.i2i.sms.service;

import java.util.ArrayList;
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
     * This contains the details of the sports such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
     * </p>
     *
     * @param createSportsRequestDto sports details contains sport name, venue, tutor name.
     * @return SportsInfo this provides all the information of that sport.
     */
    public SportsResponseDto addSport(CreateSportsRequestDto createSportsRequestDto) {
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
     * such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
     */
    public List<SportsResponseDto> getAllSportsActivities() {
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
     */
    public boolean removeSportById(String sportId) {
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
     */
    public Optional<SportsActivity> getSportDetailsById(String id) {
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
     */
    public List<StudentResponseDto> getStudentsInSport(String sportId) {
        try {
            Optional<SportsActivity> sportsActivity = getSportDetailsById(sportId);
            if (sportsActivity.isPresent()) {
                List<Student> students = sportsActivityRepository.findStudentsBySports(sportId);
                return students.stream().map(StudentResponseDto::new).collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the students in sport id: {}", sportId, e);
            throw new StudentException("Failed to retrieve the students in sport id " + sportId, e);
        }
    }

    public boolean isSportsActivityExist(String sportId) {
        return sportsActivityRepository.existsById(sportId);
    }
}
