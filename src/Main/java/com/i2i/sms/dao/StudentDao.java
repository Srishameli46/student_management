package com.i2i.sms.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query; 
import org.hibernate.Session;      
import org.hibernate.Transaction; 
 
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This student class represents basic details such as students name, date of birth and student Id. 
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retriving and removing the students.
 * </p>
 */
public class StudentDao {
 /**
  * <p>
  * Insert all Students along with name, dob, studentId, grade and associated address details.
  * </p>
  * 
  * @param student
  *          This student provide the input like name, Date of birth, Id of a student, grade details and the address for that student.
  * @throws StudentException when there is no proper details provided from the user.
  *
  */
  public void insertStudent(Student student) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(student);
      transaction.commit();            
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
        throw new StudentException("Issue while Inserting\n" + student.getName() +"\nPlease Check the details provided...", e);
    } 
  }

  /** 
   * <p>
   * Give the Student details by the Id that get searched.
   * </p>
   * 
   * @param id
   *         For which Id of the student get be integer.
   * @throws StudentException when there is no such exists to fetch by the studentId.
   *
   * @return all the student details of that Id, if not present return null.
   */
  public Student retriveStudentById(int id) {
    Student student = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()){ 
      student = session.get(Student.class, id);
    } catch (Exception e) {
        throw new StudentException("Unable to get student id " + id + " because there is no such studentId ....", e);
    } 
    return student;
  }
 
  /**
   * <p>
   * Get all Student details includes,
   *      Name to be given in alphabets alone no other charcters allowed.
   *      Date of birth of the student should be yyyy-MM-dd.
   *      Address of the student will be provided with the door number, street name, city name, state name and pin code.
   *      The grade details like standard, section along with the corresponding gradeId.   
   * </p>
   *
   * @throws StudentException when there is no details to fetch.
   *
   * @return the list of student details
   */
  public List<Student> retriveAllStudents() {
    List<Student> students = new ArrayList<>();
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {   
      students = session.createQuery("from Student", Student.class).list();
    } catch (Exception e) {
        throw new StudentException("Unable to retrieve all student's details", e);
    }
    return students;
  } 

 /**
  * <p>
  * Remove the student by the Id that is provided.
  * </p>
  *
  * @param id
  *         Student unique Id given in interger alone.
  * @throws StudentException when there is no details to remove the student.
  *
  * @return true if the Id found and Removed, or return false.
  */
  public boolean deleteStudentById(int id) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Student student = session.get(Student.class,id);
      if (null != student) {
         session.delete(student);
         transaction.commit();
         return true;
      }
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
        throw new StudentException("Unable to remove the student id " + id + " because there is no such studentId ....", e);
    }
    return false; 
  }
}
