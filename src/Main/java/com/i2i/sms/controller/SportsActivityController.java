package com.i2i.sms.controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;
import com.i2i.sms.service.SportsActivityService;
import com.i2i.sms.utils.DateUtils;

/** 
 * <p>
 * Sports activity controller handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new sports activity details, adding students to their related sports along with the other operations,includes
 *  - retrieving the whole sports details along with their associated student and grade details.
 *  - removing the student from all associated sport activities or remove the whole sport.
 * </p>
 */

public class SportsActivityController {
  private SportsActivityService sportsActivityService = new SportsActivityService();
  private Scanner scanner = new Scanner(System.in);
  
  /**
   * <p>
   * Get the various option from the student to access the sports details of the students.
   * </p>
   */
  public void accessSportsDetails() {
    while (true) {
      System.out.println("\n1. Add Sports");
      System.out.println("2. Display All Sports");
      System.out.println("3. Display Students in particular sport");
      System.out.println("4. Remove Sports From the list ");
      System.out.println("5. Unenrollment of the student from the sports ");
      System.out.println("6. Exits");
      System.out.println("Enter your choice : ");
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          addSports();
          break;
        case 2:
          displayAllSports();
          break;
        case 3:
          displayStudentsInSport();
          break;
        case 4:
          removeSportById();
          break;
        case 5:
          unenrollStudentFromSport();
          break;
        case 6:
          return;
        default:
          System.out.println("Invalid choice.");
          break;
      }
    }
  }

/**
 * <p>
 * Insert student into the sports activity according to their preference.
 * </p>
 */
  public void addStudentToSports() {
    System.out.println("Enter student id : ");
    int studentId = scanner.nextInt();
    List<SportsActivity> sportsActivities = sportsActivityService.getAllSportsActivities();
    for (SportsActivity sport : sportsActivities) {
       System.out.println(sport);
    }
    int loop;
    do {
       System.out.println("Enter sport id to participate : ");
       int sportIdToParticipate = scanner.nextInt();
       try {
          sportsActivityService.addStudentToSportActivity(studentId, sportIdToParticipate);
       } catch(Exception e) {
           System.out.println(e.getMessage());
       }
       System.out.println("Enter 1 to participate in another sport or 0 to exit : ");
       loop = scanner.nextInt();
    } while (loop == 1);
  }

/**
 * <p>
 * Create new sport activity that students will participate.
 * This includes sport Id, sport name, venue, tutor name and the start date.
 * </p>
 */
  private void addSports() {
    System.out.println("Enter sport name : ");
    scanner.nextLine();
    String sportName = scanner.nextLine();
    System.out.println("Enter sport venue : ");
    String sportVenue = scanner.nextLine();
    System.out.println("Enter start date (yyyy-MM-dd):");
    Date validStartDate = null;
    boolean isValidDate = true;
    do {
      String date =scanner.next();
      validStartDate = DateUtils.checkAndFormatDate(date);
      if(null == validStartDate) {
        System.out.println("Invalid Input\n");
      } else {
        isValidDate = false;
      } 
    } while (isValidDate);
 
    System.out.println("Enter sport tutor : ");
    String sportTutor = scanner.nextLine();
    try {
      SportsActivity sportsActivity = sportsActivityService.addSport(sportName, sportVenue, sportTutor, validStartDate);
      if (null != sportsActivity) {
         System.out.println("Sport inserted successfully");
      } else {
          System.out.println("Failed to add sport");
      }    
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }

/**
 * <p>
 * Display all sport activity that students will participate.
 * This includes sport Id, sport name, venue, tutor name and the start date.
 * </p>
 */
  private void displayAllSports() {
    try {
      List<SportsActivity> sports = sportsActivityService.getAllSportsActivities();
      if (sports.isEmpty()) {
        System.out.println("No sports available");
      } else {
          for (SportsActivity sport : sports) {
            System.out.println(sport);
          }
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }

/**
 * <p>
 * Display students details in the particular sport activity.
 * This provides student details along with their associated grade and address details.
 * </p>
 */
  private void displayStudentsInSport() {
    try {
      List<SportsActivity> sports = sportsActivityService.getAllSportsActivities();
      for (SportsActivity sport : sports) {
        System.out.println(sport);
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    System.out.println("Enter sport id : ");
    int sportId = scanner.nextInt();
    try {
       List<Student> students = sportsActivityService.getStudentsInSport(sportId);
       if (students.isEmpty()) {
       System.out.println("No students Enrolled\n");
       } else {
           for (Student student : students) {
             System.out.println(student);
           }
       }
    } catch (Exception e) {
         System.out.println(e.getMessage());
    }
  }

/**
 * <p>
 * Remove sports details in the particular sport activity.
 * This will remove the information includes sport Id, sport name, venue, tutor name and the start date.
 * </p>
 */
  private void removeSportById() {
    System.out.println("Enter sport id to remove: ");
    int sportIdToRemove = scanner.nextInt();
    try {
      if (sportsActivityService.removeSportById(sportIdToRemove)) {
         System.out.println("Sport removed successfully");
      } else {
          System.out.println("Failed to remove sport");
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }

/**
 * <p>
 * Remove student from the user provided sports activities.
 * This will remove the information from the student categories and sport activity will remain the same.
 * </p>
 */
  private void unenrollStudentFromSport() {
    System.out.println("Enter student id: ");
    int studentIdToUnenroll = scanner.nextInt();
    System.out.println("Enter sport id: ");
    int sportIdToUnenroll = scanner.nextInt();
    try {
      if (sportsActivityService.removeStudentFromSportActivity(studentIdToUnenroll, sportIdToUnenroll)) {
         System.out.println("Student removed from sport successfully");
      } else {
         System.out.println("Failed to remove student from sport");
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }
}
