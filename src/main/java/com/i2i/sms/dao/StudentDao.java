package com.i2i.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This student class represents basic details such as students name, date of birth and student Id.
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retrieving and removing the students.
 * </p>
 */
@Repository
@Component
public class StudentDao {

    private static final Logger logger = LogManager.getLogger(StudentDao.class);
    /**
     * <p>
     * Insert all Students along with name, dob, studentId, grade and associated address details.
     * </p>
     *
     * @param student This student provide the input like name, Date of birth, id of a student, grade details and the address for that student.
     * @throws StudentException when there is no proper details provided from the user.
     */
    public void insertStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            logger.debug("Starting transaction to insert student with name: {}", student.getName());
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (null != transaction) {
                transaction.rollback();
                logger.warn("Transaction rolled back while inserting student id: {}", student.getId());
            }
            throw new StudentException("Issue while Inserting\n" + student.getName() + "\nPlease Check the details provided...", e);
        }
    }

    /**
     * <p>
     * Give the Student details by the Id that get searched.
     * </p>
     *
     * @param id For which id of the student get be integer.
     * @return all the student details of that id, if not present return null.
     * @throws StudentException when there is no such exists to fetch by the studentId.
     */
    public Student retrieveStudentById(int id) {
        Student student = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            student = session.get(Student.class, id);
        } catch (Exception e) {
            throw new StudentException("Unable to get student id " + id + " because there is no such studentId ....", e);
        }
        return student;
    }

    /**
     * <p>
     * Get all Student details includes,
     * Name to be given in alphabets alone no other characters allowed.
     * Date of birth of the student should be yyyy-MM-dd.
     * Address of the student will be provided with the door number, street name, city name, state name and pin code.
     * The grade details like standard, section along with the corresponding gradeId.
     * </p>
     *
     * @return the list of student details
     * @throws StudentException when there is no details to fetch.
     */
    public List<Student> retrieveAllStudents() {
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
     * @param id Student unique Id given in integer alone.
     * @return true if the Id found and Removed, or return false.
     * @throws StudentException when there is no details to remove the student.
     */
    public boolean deleteStudentById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            logger.debug("Starting transaction to delete student with id: {}", id);
            Student student = session.get(Student.class, id);
            if (null != student) {
                session.delete(student);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (null != transaction) {
                transaction.rollback();
                logger.warn("Transaction rolled back while deleting student id: {}", id);
            }
            throw new StudentException("Unable to remove the student id " + id + " because there is no such studentId ....", e);
        }
        return false;
    }
}
