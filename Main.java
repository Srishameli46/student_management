import java.util.Scanner;

import com.i2i.sms.controller.GradeController;
import com.i2i.sms.controller.SportsActivityController;
import com.i2i.sms.controller.StudentController;
import com.i2i.sms.helper.HibernateConnection;

public class Main {  
  private static Scanner scanner = new Scanner(System.in);
  private static StudentController studentController = new StudentController();
  private static GradeController gradeController = new GradeController();
  private static SportsActivityController sportsActivityController = new SportsActivityController();

  public static void main(String args[]) {
    int input;
    do {
      System.out.println("STUDENT DETAILS:");
      System.out.println("Choose the operation\n" +
                       "1. Storing the details\n" +
                       "2. Display student details\n" +
                       "3. Search by ID\n" + 
                       "4. Remove by ID\n" +
                       "5. Display classsRoom details\n" +
                       "6. Manage Sports details\n" +
                       "7. Participation of students into the sports\n" +
                       "8. Exit\n");

      int option = scanner.nextInt(); 
      switch (option) {
        case 1:
          studentController.createStudent();
          break;

        case 2:
          studentController.displayStudent();
          break;

        case 3:
          studentController.searchStudent();
          break;
 
        case 4:
          studentController.removeStudent();
          break;

        case 5:
          gradeController.displayGrade();
          break;

        case 6:
          sportsActivityController.accessSportsDetails();
          break;

        case 7:
          sportsActivityController.addStudentToSports();
          break;
          
        case 8:
          HibernateConnection.shutdown();
          System.exit(0);

        default:
          System.out.println("Invalid Input\n");
      }
      System.out.println("\nGive 1 (continue) or 0 (exit)\n");
      input = scanner.nextInt();
      if (input == 0) {
        HibernateConnection.shutdown();
      }
    } while (input == 1);
  }
}
