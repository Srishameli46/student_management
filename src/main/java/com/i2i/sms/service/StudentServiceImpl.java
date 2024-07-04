package com.i2i.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.CreateAddressRequestDto;
import com.i2i.sms.dto.CreateStudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithSportsResponseDto;
import com.i2i.sms.exception.StudentException;
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
public class StudentServiceImpl implements StudentService{
    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeServiceImpl gradeServiceImpl;

    /**
     * <p>
     * Add the Student details which will be associated with the Grade.
     * </p>
     * @param createStudentRequestDto
     *       This createStudentRequestDto contains details like student name, date of birth, standard and address.
     *
     * @return the details of the single student.
     */
    public StudentResponseDto addStudent(CreateStudentRequestDto createStudentRequestDto) {
        try {
            logger.debug("Started to create student details");
            if (createStudentRequestDto == null || createStudentRequestDto.getCreateAddressRequestDto() == null) {
                throw new StudentException("Invalid input data", null);
            }

            if (!DateUtils.isValidDate(createStudentRequestDto.getDob())) {
                throw new StudentException("Invalid date of birth", null);
            }
            Address address = convertToEntity(createStudentRequestDto.getCreateAddressRequestDto());
            Grade grade = gradeServiceImpl.addGrade(createStudentRequestDto.getStandard());
            Student student = new Student();
            student.setName(createStudentRequestDto.getName());
            student.setDob(createStudentRequestDto.getDob());
            student.setAddress(address);
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
     */
    public List<StudentResponseDto> getAllStudents() {
        logger.debug("Started to retrieve student details");
        try {
            List<Student> allStudents = studentRepository.findAll();
            return allStudents.stream().map(StudentResponseDto::new).collect(Collectors.toList());
        } catch (Exception e){
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
     */
    public StudentWithSportsResponseDto searchStudentById(int id) {
        logger.debug("Started to search student details");
        try {
            Student student = studentRepository.getById(id);
            if (null != student) {
                return new StudentWithSportsResponseDto(student);
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
     * @ return  the checking parameter whether the id removed or not by true or false.
     */
    public void removeStudentById(int id) {
        logger.debug("Started to delete student details");
        try {
            Optional<Student> student = studentRepository.findById(id);
            if(student.isPresent()){
                Student studentToDelete = student.get();
                studentToDelete.getGrade().getStudents().remove(studentToDelete);
                studentRepository.delete(studentToDelete);
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting the student id: {}", id, e);
            throw new StudentException("Failed to save student with ID " + id, e);
        }
    }

    /**
     * <p>
     * Convert the Address Dto information into address details.
     * </p>
     * @param createAddressRequestDto address details of the student .
     * @ return  the checking parameter whether the id removed or not by true or false.
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
}