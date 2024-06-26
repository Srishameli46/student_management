package com.i2i.sms.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.models.Grade;

/**
 * <p>
 * The Grade class represents a specific standard with a section and includes the
 * information about the students enrolled in that grade. 
 *
 * It captures the unique ID for the grade, the standard, and the section identifier.
 * 
 * This class is designed to encapsulate all relevant information about a grade and
 * to provide actions for accessing and modifying that information, as well as for
 * associating students with the grade.
 * </p>
 */
public class GradeDao {

  /**
   * <p>
   * Insert new Grade details such as Standard, Section and gradeId.
   * </p>
   *
   * @param grade
   *         For which grade has included standard, section and associated student details.
   * @return gradeId
   *         grade id after insertion will be retrieved.
   * @throws StudentException when there is no details to fetch and remove such grades.
   *
   */
  public Grade insertGrade(Grade grade) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
      transaction = session.beginTransaction();
      session.save(grade);
      transaction.commit();          
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
        throw new StudentException("Unable to insert "+ grade.getStandard() + "\nPlease Check the details provided...", e);
    }
    return grade;
  }
   
  /**
   * <p>
   * Retrive all Grade that includes Standard, Section and Student Lists.
   * </p>
   *
   * @return grade details standards, section and associated student details.
   *
   * @throws StudentException when unable to fetch the grade details like gradeId, standard and section.
   * 
   */
  public List<Grade> retriveAllGrades() {
    List<Grade> grades = new ArrayList<>();
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      grades = session.createQuery("from Grade", Grade.class).list();
    } catch (Exception e) {
        throw new StudentException("Unable to retrive all grade details", e);
    }
    return grades;
  }

  /**
   * <p>
   * Get the Standard and Section from the grade.
   * </p>
   *
   * @param standard
   *           For which standard is given between 1 to 12.
   * @param section
   *           Section get allocated either as A or B automatically.
   * @throws StudentException when there is no such standard or section exists to fetch.
   *
   * @ return the grade details like standand and section, if not found return no details.
   *
   */
  public Grade getGradeByStandardAndSection(int standard, String section) {
    Grade grade = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      Query query = session.createQuery("from Grade where standard = :standard and section = :section");
      query.setParameter("standard", standard);
      query.setParameter("section", section);
      grade = (Grade)query.uniqueResult();
    } catch (Exception e) {
        throw new StudentException("Grade detail " + standard + "-" + section + " not exists...", e);    
    }
    return grade;
  }

 /**
  * <p>
  * This method provied whether the particular grade and section exists or not.
  * </p>
  *
  * @param standard 
  *        The standard are provied by the user.
  * @param section 
  *        The section will allocated automatically and provided for the existance check.
  *
  * @throws Student exception when there no grade presents with that standard and section.
  * 
  * @return true if the grade already exists or return false.
  *        
  */
  public boolean isStandardAndSectionAvailable(int standard, String section) {
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      Query query = session.createQuery("from Grade where standard = :standard and section = :section");
      query.setParameter("standard", standard);
      query.setParameter("section", section);
      Grade grade = (Grade)query.uniqueResult();
      if (null != grade) {
         return true;
      }
    } catch (Exception e) {
        throw new StudentException("Grade with " + standard + "-" + section + " not exists.\nPlease check the input provided", e);
    }
    return false; 
  }
}

