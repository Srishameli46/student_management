package com.i2i.sms.service;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.CreateAddressRequestDto;
import com.i2i.sms.dto.CreateStudentRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithAllDetailsDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.Student;
import com.i2i.sms.repository.StudentRepository;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * This student class represents basic details such as students name, date of birth and student Id.
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retrieving and removing the students.
 * </p>
 */
@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SportsActivityService sportsActivityService;

    /**
     * <p>
     * Add the Student details which will be associated with the Grade.
     * </p>
     *
     * @param createStudentRequestDto This createStudentRequestDto contains details like student name, date of birth, standard and address.
     * @return the details of the single student.
     * @throws StudentException when the student can not be created.
     */
    public StudentResponseDto addStudent(CreateStudentRequestDto createStudentRequestDto) {
        try {
            logger.debug("Started to create student details");
            if (createStudentRequestDto == null || createStudentRequestDto.getAddress() == null) {
                throw new StudentException("Invalid input data", null);
            }
            Address address = convertToEntity(createStudentRequestDto.getAddress());
            Grade grade = gradeService.addGrade(createStudentRequestDto.getStandard());
            Student student = new Student();
            student.setName(createStudentRequestDto.getName());
            student.setDob(createStudentRequestDto.getDob());
            student.setAddress(address);
            address.setStudent(student);
            student.setGrade(grade);
            student = studentRepository.save(student);
            return new StudentResponseDto(student);
        } catch (Exception e) {
            logger.error("An error occurred while saving the student: {}", createStudentRequestDto.getName(), e);
            throw new StudentException("Failed to save student with ID " + createStudentRequestDto.getName(), e);
        }
    }

    /**
     * <p>
     * Display all students along with their grade details and address.
     * </p>
     *
     * @return all student details with associated grade and address.
     * @throws StudentException when the students can not be accessed.
     */
    public List<StudentResponseDto> getAllStudents() {
        logger.debug("Started to retrieve student details");
        try {
            List<Student> allStudents = studentRepository.findAll();
            return allStudents.stream().map(StudentResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the student:", e);
            throw new StudentException("Failed to get all students", e);
        }
    }

    /**
     * <p>
     * Display student details by their student Id.
     * </p>
     *
     * @param id Student unique Id given in integer alone.
     * @return details of the student by the id given to search.
     * @throws StudentException when the student can not be searched.
     */
    public StudentWithAllDetailsDto searchStudentById(String id) {
        logger.debug("Started to search student details");
        try {
            Optional<Student> student = studentRepository.findById(id);
            if (student.isPresent()) {
                return new StudentWithAllDetailsDto(student.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while searching the student id: {}", id, e);
            throw new StudentException("Failed to save student with ID " + id, e);
        }
    }

    /**
     * <p>
     * Remove the student by the student Id in the Student details, Grade details, address and their related sports activities.
     * </p>
     *
     * @param id Student unique id given in integer alone.
     * @return the checking parameter whether the id removed or not by true or false.
     * @throws StudentException when the student can not be deleted.
     */
    public boolean removeStudentById(String id) {
        logger.debug("Started to delete student details");
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student studentToDelete = studentOptional.get();
                studentToDelete.getGrade().getStudents().remove(studentToDelete);
                for (SportsActivity sportsActivity : studentToDelete.getSportsActivities()) {
                    sportsActivity.getStudents().remove(studentToDelete);
                }
                String addressId = studentToDelete.getAddress().getAddressId();
                addressService.deleteById(addressId);
                studentRepository.deleteById(studentToDelete.getId());
                logger.debug("Successfully deleted student with ID: {}", id);
                return true;
            } else {
                logger.warn("Student with ID: {} not found", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting the student with ID: {}", id, e);
            throw new StudentException("Failed to delete student with ID " + id, e);
        }
    }

    /**
     * <p>
     * Update the Student details which will be associated with the Grade.
     * </p>
     *
     * @param id                      The id of the student to update.
     * @param createStudentRequestDto The updated student details.
     * @return the details of the updated student.
     * @throws StudentException when the student can not be updated.
     */
    public StudentResponseDto updateStudent(String id, CreateStudentRequestDto createStudentRequestDto) {
        try {
            logger.debug("Started to update student details");
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student studentToUpdate = studentOptional.get();
                studentToUpdate.setName(createStudentRequestDto.getName());
                studentToUpdate.setDob(createStudentRequestDto.getDob());
                if (studentToUpdate.getGrade().getStandard() != createStudentRequestDto.getStandard()) {
                    Grade grade = gradeService.addGrade(createStudentRequestDto.getStandard());
                    studentToUpdate.setGrade(grade);
                }
                studentToUpdate.getAddress().setDoorNo(createStudentRequestDto.getAddress().getDoorNo());
                studentToUpdate.getAddress().setStreet(createStudentRequestDto.getAddress().getStreet());
                studentToUpdate.getAddress().setCity(createStudentRequestDto.getAddress().getCity());
                studentToUpdate.getAddress().setState(createStudentRequestDto.getAddress().getState());
                studentToUpdate.getAddress().setPinCode(createStudentRequestDto.getAddress().getPinCode());
                studentToUpdate = studentRepository.save(studentToUpdate);
                return new StudentResponseDto(studentToUpdate);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating the student: {}", createStudentRequestDto.getName(), e);
            throw new StudentException("Failed to update student with ID " + id, e);
        }
    }

    /**
     * <p>
     * Convert the Address Dto information into address details.
     * </p>
     *
     * @param createAddressRequestDto address details of the student .
     * @return the checking parameter whether the id removed or not by true or false.
     */
    private Address convertToEntity(CreateAddressRequestDto createAddressRequestDto) {
        Address address = new Address();
        address.setDoorNo(createAddressRequestDto.getDoorNo());
        address.setStreet(createAddressRequestDto.getStreet());
        address.setCity(createAddressRequestDto.getCity());
        address.setState(createAddressRequestDto.getState());
        address.setPinCode(createAddressRequestDto.getPinCode());
        return address;
    }

    /**
     * <p>
     * Insert students to the sport Activity by the associate student id and sport id.
     * </p>
     *
     * @param studentId                     student id is the studentId get from the student details.
     * @param createStudentSportsRequestDto this contains student details along with grade and their sports details.
     * @return sports details that the student allowed to participate.
     * @throws StudentException when the student can not be assigned to sports.
     */
    public List<SportsResponseDto> addStudentToSportActivity(String studentId, CreateStudentSportsRequestDto createStudentSportsRequestDto) {
        try {
            List<String> sportIds = createStudentSportsRequestDto.getSportIds();
            Optional<Student> foundStudent = studentRepository.findById(studentId);
            if (!foundStudent.isPresent()) {
                logger.warn("No sports activity found with id: {}", sportIds);
                return Collections.emptyList();
            }

            Student student = foundStudent.get();
            Set<SportsActivity> sportsActivities = student.getSportsActivities();
            for (String sportId : sportIds) {
                Optional<SportsActivity> sportsActivityDetail = sportsActivityService.getSportDetailsById(sportId);
                if (sportsActivityDetail.isPresent()) {
                    SportsActivity sportsActivity = sportsActivityDetail.get();
                    sportsActivities.add(sportsActivity);
                } else {
                    return new ArrayList<>();
                }
            }
            studentRepository.save(student);
            return sportsActivities.stream().map(SportsResponseDto::new).collect(Collectors.toList());


        } catch (Exception e) {
            logger.error("An error occurred while assigning the sports to student id: {}", studentId, e);
            throw new StudentException("Failed to assign sport to student id  " + studentId, e);
        }
    }
}