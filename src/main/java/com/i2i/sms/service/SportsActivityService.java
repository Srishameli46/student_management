package com.i2i.sms.service;

import java.util.Date;
import java.util.List;

import com.i2i.sms.dao.SportsActivityDao;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;

/**
 * <p>
 * This sports activity class represents basic details such as sports name, tutor name, venue, sport Id.
 * Additionally, it stored information about the student's such as name, studentId, grade details and the address detail.
 * Manage the information by the following operation like creating, retriving and removing the sports activity along with the associated students.
 * </p>
 */

public class SportsActivityService {

    private SportsActivityDao sportsActivityDao = new SportsActivityDao();

    /**
     * <p>
     * Create new sports Activity that students need to participate .
     * This contains the details of the sports such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
     * </p>
     *
     * @param sportName sport name contains only alphabets and does not contain numbers or special characters.
     * @param venue     venue is get from the user no numerical or alphanumerical values are allowed.
     * @param tutorName tutor name contains only alphabets and does not contain numbers or special characters.
     * @param startDate startDate  should be in the format yyyy-MM-dd alone.
     */
    public SportsActivity addSport(String sportName, String venue, String tutorName, Date startDate) {
        SportsActivity sportsActivity = new SportsActivity();
        sportsActivity.setSportName(sportName);
        sportsActivity.setVenue(venue);
        sportsActivity.setTutorName(tutorName);
        sportsActivity.setStartDate(startDate);
        return sportsActivityDao.insertSportsActivity(sportsActivity);
    }

    /**
     * <p>
     * Get all the students detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @return details of all the sports activity
     * such as sport id, sport name, venue, date-of-joining and sport tutor which has to be added.
     */
    public List<SportsActivity> getAllSportsActivities() {
        return sportsActivityDao.getAllSportsActivities();
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
        return sportsActivityDao.deleteSportsActivityById(sportId);
    }

    /**
     * <p>
     * Insert students to the sport Activity by the associate student id and sport id.
     * </p>
     *
     * @param studentId student id is the studentId get from the student details.
     * @param sportId   SportId is get from the user that should be allowed only in numerical.
     */
    public void addStudentToSportActivity(int studentId, int sportId) {
        sportsActivityDao.insertStudentToSportActivity(studentId, sportId);
    }

    /**
     * <p>
     * Get all the students detail in the particular sport Activity by the sport Id that provided.
     * </p>
     *
     * @param sportId SportId is get from the user that should be allowed only in numerical.
     * @return details of all the students in that particular sports activity.
     */
    public List<Student> getStudentsInSport(int sportId) {
        return sportsActivityDao.getStudentsInSport(sportId);
    }

    /**
     * <p>
     * Remove or un-enrollment of the student from the sport Activity by the sport Id  and the student id that provided.
     * </p>
     *
     * @param studentId Student unique Id given in integer alone.
     * @param sportId   SportId is get from the user that should be allowed only in numerical.
     * @return true if the student un-enrolled from the sportActivity or else return false.
     */
    public boolean removeStudentFromSportActivity(int studentId, int sportId) {
        return sportsActivityDao.removeStudentFromSportActivity(studentId, sportId);
    }
}
