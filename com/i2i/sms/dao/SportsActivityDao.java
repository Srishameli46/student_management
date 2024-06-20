package com.i2i.sms.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
 * This sports activity class represents basic details such as sports name, tutor name, venue, sport Id. 
 * Additionally, it stored information about the student's such as name, studentId, grade details and the address detail.
 * Manage the information by the following operation like creating, retriving and removing the sports activity along with the associated students.
 * </p>
 */
public class SportsActivityDao {

 /**
  * <p>
  * Create new sports Activity that students need to participate .
  * This contains the details of the sports such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
  * </p>
  *
  * @param sportsActivity
  *         For which sportActivity could not be null.
  *         The sport id contains numerical values only.
  *         The sport name contains only alphabets and does not contains numbers or special characters.
  *         The venue name contains  alphabets or numbers or special characters.
  *         The tutor name contains only alphabets and does not contains numbers or special characters.
  *         The start date represents the date on which the sports starts which is in the format dd/MM/yyyy.
  *
  * @throws StudentException when the sportsActivity details are not inserted due to improper details.
  */
  public SportsActivity insertSportsActivity(SportsActivity sportsActivity) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(sportsActivity);
      transaction.commit();
    } catch (Exception e) {
        if (null != transaction) {
          transaction.rollback();
        }
        throw new StudentException("Unable to add the sports activity\n"+ sportsActivity + "\nPlease check the details provided....", e);
    }
    return sportsActivity;
  }
    
 /**
  * <p>
  * Get all sports Activity details that students need to participate.
  * </p>
  *
  * @return the list of sportsActivity contains the details of the sports such as sport id, sport name, venue, date-of-joining and sport tutor which has been added.
  *
  * @throws StudentException when the sportsActivity details are not exists due to improper details .
  * 
  */
  public List<SportsActivity> getAllSportsActivities() {
    List<SportsActivity> sportsActivities = new ArrayList<>();
    try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
      sportsActivities = session.createQuery("from SportsActivity", SportsActivity.class).list();
    } catch (Exception e) {
        throw new StudentException("Unable to get the sports activity", e);
    }
    return sportsActivities;
  }

 /**
  * <p>
  * Remove sport Activity by the sport Id that provided.
  * </p>
  *
  * @param sportId
  *        SportId is get from the user, allowed only numberical values.
  * @return true if the sportActivity deleted or else return false.
  *
  * @throws StudentException when the sportsActivity details are not deleted due to non-existance of the details.
  * 
  */
  public boolean deleteSportsActivityById(int sportId) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      SportsActivity sportsActivity = session.get(SportsActivity.class, sportId);
      if (sportsActivity != null) {
         session.delete(sportsActivity);
         transaction.commit();
         return true;
      }
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
        throw new StudentException("Unable to delete the sports activity for the sport Id " + sportId +", since no values assigned....", e);
    }
    return false;
  }

 /**
  * <p>
  * Insert students to the sport Activity by the associate student id and sport id.
  * </p>
  *
  * @param id
  *        id is the studentId get from the student details.
  * @param sportId
  *        SportId is get from the user,that contains only numberical values.
  *
  * @throws StudentException when the sportsId are not matched or assigned to that particular student Id due to non-existance of the details.
  * 
  */
  public void insertStudentToSportActivity(int id,int sportId) {   
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Student student = session.get(Student.class, id);
      SportsActivity sportsActivity = session.get(SportsActivity.class, sportId);
      if (null != student &&  null != sportsActivity) {
         student.addSportsActivity(sportsActivity);
         session.update(student);
         transaction.commit();
      }
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
       throw new StudentException("Unable to insert the student id " + id + " to their prefered sports id " + sportId +", please check the details....", e);
    }
  }

 /**
  * <p>
  * Get all the students detail in the particular sport Activity by the sport Id that provided.
  * </p>
  *
  * @param sportId
  *        SportId is get from the user, allowed only numberical values.
  * @return details of all the students in that particular sports activity.
  *
  * @throws StudentException when the sportsActivity details are not retrived due to non-existance of the details.
  * 
  */
  public List<Student> getStudentsInSport(int sportId) {
    List<Student> students = new ArrayList<>();
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      SportsActivity sportsActivity = session.get(SportsActivity.class, sportId);
      Set<Student> studentDetails = sportsActivity.getStudents();
      students.addAll(studentDetails);      
    } catch (Exception e) {
        throw new StudentException("Unable to get the students details in this sport id " + sportId + ", since no values assigned....", e);
    }
    return students;
  }

 
 /**
  * <p>
  * Remove student from the sport Activity by the sport Id  and the student id that provided.
  * </p>
  *
  * @param studentId
  *        Student unique Id given in interger alone.
  * @param sportId
  *        SportId is get from the user, allowed only numberical values.
  * @return true if the sportActivity deleted or else return false.
  *
  * @throws StudentException when the sportsActivity details are not deleted due to non-existance of the details.
  * 
  */
  public boolean removeStudentFromSportActivity(int studentId, int sportId) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Student student = session.get(Student.class, studentId);
      SportsActivity sportsActivity = session.get(SportsActivity.class, sportId);
      if (null != student && null != sportsActivity) {
         student.removeSportsActivity(sportsActivity);
         session.update(student);
         transaction.commit();
         return true;
      }
    } catch (Exception e) {
        if (null != transaction) {
          transaction.rollback();
        }
       throw new StudentException("Unable to remove the sports detail of student Id " + studentId + " for the sport id " + sportId + ", since no values assigned....", e);
    }
    return false;
  }
}
