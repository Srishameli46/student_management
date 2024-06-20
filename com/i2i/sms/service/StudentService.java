package com.i2i.sms.service;

import java.util.Date;
import java.util.List;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.dao.StudentDao;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.Student;
import com.i2i.sms.service.AddressService;
import com.i2i.sms.service.GradeService;
import com.i2i.sms.models.SportsActivity;

/**
 * <p>
 * This student class represents basic details such as students name, date of birth and student Id. 
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retriving and removing the students.
 * </p>
 */
public class StudentService {
  private StudentDao studentDao = new StudentDao();
  private GradeService gradeService = new GradeService();
  private AddressService addressService = new AddressService();
  
 /**
  * <p>   
  * Add the Student details which will be associated with the Grade.
  * </p>
  *
  * @param name
  *      Name to be given in alphabets alone no other charcters allowed.
  * @param dob
  *      Date of birth of the student should be yyyy-MM-dd.
  * @param address
  *      Address of the student will be provided with the door number, street name, city name, state name and pin code.
  * @param grade
  *      The grade details like standard, section along with the corresponding gradeId.
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
    return studentDao.retriveAllStudents();
  }
  
  /**
   * <p>
   * Display student details by their student Id.
   * </p>
   * 
   * @param Id
   *       Student unique Id given in interger alone.
   * @return details of the student by the Id given to search.
   */
  public Student searchStudentById(int id) {
    return studentDao.retriveStudentById(id);
  }

  /**
   * <p>
   * Remove the student by the student Id in the Student details, Grade details, address and their related sports activities.
   * </p>
   *
   * @param Id
   *       Student unique Id given in interger alone.
   * @ return  the checking paramter whether the Id removed or not by true or false.
   */
  public boolean removeStudentById(int id) {
    Student student = studentDao.retriveStudentById (id);
    boolean found = false;
    if (null != student) {
       found = studentDao.deleteStudentById(id);
    }
    return found;
  }
}