package com.i2i.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithSportsResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
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
public class SportsActivityServiceImpl implements SportsActivityService{
    private static final Logger logger = LogManager.getLogger(SportsActivityServiceImpl.class);
    @Autowired
    private SportsActivityRepository sportsActivityRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AddressService addressService;

    /**
     * <p>
     * Create new sports Activity that students need to participate .
     * This contains the details of the sports such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
     * </p>
     * @param createSportsRequestDto sports details contains sport name, venue, tutor name, start date.
     * @return SportsInfo this provides all the information of that sport.
     */
    public SportsResponseDto addSport(CreateSportsRequestDto createSportsRequestDto) {
        try {
            SportsActivity sportsActivity = new SportsActivity();
            sportsActivity.setSportName(createSportsRequestDto.getSportName());
            sportsActivity.setVenue(createSportsRequestDto.getVenue());
            sportsActivity.setTutorName(createSportsRequestDto.getTutorName());
            sportsActivity.setStartDate(createSportsRequestDto.getStartDate());
            return new SportsResponseDto(sportsActivityRepository.save(sportsActivity));
        } catch (Exception e) {
            logger.error("An error occurred while saving the sports: {}", createSportsRequestDto.getSportName(), e);
            throw new StudentException("Failed to save sport  " + createSportsRequestDto.getSportName(), e);
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
    public boolean removeSportById(int sportId) {
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
     * Insert students to the sport Activity by the associate student id and sport id.
     * </p>
     *
     * @param studentId student id is the studentId get from the student details.
     * @param createStudentSportsRequestDto this contains student details along with grade and their sports details.
     * @return sports details that the student allowed to participate.
     */
    public List<SportsResponseDto> addStudentToSportActivity(int studentId, CreateStudentSportsRequestDto createStudentSportsRequestDto) {
        try {
            List<Integer> sportIds = createStudentSportsRequestDto.getSportIds();
            StudentWithSportsResponseDto studentWithSportsResponseDto = studentService.searchStudentById(studentId);

            Grade grade = gradeService.getById(studentWithSportsResponseDto.getGradeInfo().getGradeId());
            Address address = addressService.getById(studentWithSportsResponseDto.getAddressInfo().getAddressId());

            Student student = new Student();
            student.setId(studentWithSportsResponseDto.getId());
            student.setName(studentWithSportsResponseDto.getName());
            student.setDob(studentWithSportsResponseDto.getDob());
            student.setGrade(grade);
            student.setAddress(address);


            List<SportsActivity> sportsActivities = new ArrayList<>();
            for (Integer sportId : sportIds) {
                SportsActivity sportsActivity = getSportDetailsById((int) sportId);
                sportsActivity.addStudent(student);
                sportsActivity = sportsActivityRepository.save(sportsActivity);
                sportsActivities.add(sportsActivity);
            }
            return sportsActivities.stream().map(SportsResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("An error occurred while assigning the sports to student id: {}", studentId, e);
            throw new StudentException("Failed to assign sport to student id  " + studentId, e);
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
    private SportsActivity getSportDetailsById ( int id){
        return sportsActivityRepository.getById(id);
    }

    /**
     * <p>
     * Get all the students detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @param sportId SportId is get from the user that should be allowed only in numerical.
     * @return details of all the students in that particular sports activity.
     */
    public List<StudentResponseDto> getStudentsInSport(int sportId) {
        try {
            List<Student> students = sportsActivityRepository.findStudentsBySports(sportId);
            return students.stream().map(StudentResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the students in sport id: {}", sportId, e);
            throw new StudentException("Failed to retrieve the students in sport id " + sportId, e);
        }
    }
}
