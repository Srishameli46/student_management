package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Grade;
import com.i2i.sms.service.GradeService;

/** 
 * <p>
 * Grade controller handles all the operations related to the management based on the user's request.
 * It provide operation like displaying all students enrolled in the specific grade.
 * </p>
 */
public class GradeController {
  private GradeService gradeService = new GradeService();

  /**
   * <p>
   * Display all Grade details along with student informations.
   * </p>
   */
  public void displayGrade() {
    System.out.println("DISPLAY CLASSROOM DETAILS");
    try {
      List<Grade> allGrades = gradeService.getAllGrades();
      if (allGrades.isEmpty()) {
         System.out.println("No classrooms available.\n");
      } else {
          for (Grade grade : allGrades) {
            System.out.println(grade);
          }
      }
    } catch (StudentException e) {
        System.out.println(e.getMessage());
    }
  }
}
