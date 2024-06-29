package com.i2i.sms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.i2i.sms.dao.StudentDao;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This student class represents basic details such as students name, date of birth and student Id.
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retrieving and removing the students.
 * </p>
 */
@Service
@Component
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AddressService addressService;

    /**
     * <p>
     * Add the Student details which will be associated with the Grade.
     * </p>
     *
     * @param name    Name to be given in alphabets alone no other characters allowed.
     * @param dob     Date of birth of the student should be yyyy-MM-dd.
     * @param address Address of the student will be provided with the door number, street name, city name, state name and pin code.
     * @param grade   The grade details like standard, section along with the corresponding gradeId.
     * @return the details of the single student.
     */
    public Student addStudent(String name, Date dob, Address address, Grade grade) {
        Student student = new Student();
        student.setName(name);
        student.setDob(dob);
        student.setAddress(address);
        student.setGrade(grade);
        addressService.insertAddress(address);
        studentDao.insertStudent(student);
        grade.addStudent(student);
        return student;
    }

    /**
     * <p>
     * Display all students along with their grade details and address.
     * </p>
     *
     * @return all student details with associated grade and address.
     */
    public List<Student> getAllStudents() {
        return studentDao.retrieveAllStudents();
    }

    /**
     * <p>
     * Display student details by their student Id.
     * </p>
     *
     * @param id Student unique Id given in integer alone.
     * @return details of the student by the id given to search.
     */
    public Student searchStudentById(int id) {
        return studentDao.retrieveStudentById(id);
    }

    /**
     * <p>
     * Remove the student by the student Id in the Student details, Grade details, address and their related sports activities.
     * </p>
     *
     * @param id Student unique id given in integer alone.
     * @ return  the checking parameter whether the id removed or not by true or false.
     */
    public boolean removeStudentById(int id) {
        Student student = studentDao.retrieveStudentById(id);
        boolean found = false;
        if (null != student) {
            found = studentDao.deleteStudentById(id);
        }
        return found;
    }
}