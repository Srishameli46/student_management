import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.i2i.sms.controller.AddressController;
import com.i2i.sms.controller.GradeController;
import com.i2i.sms.controller.SportsActivityController;
import com.i2i.sms.controller.StudentController;
import com.i2i.sms.helper.HibernateConnection;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= "com.i2i.sms")
public class Main {
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private StudentController studentController;
    @Autowired
    private AddressController addressController;
    @Autowired
    private GradeController gradeController;
    @Autowired
    private SportsActivityController sportsActivityController;

    public static void main(String args[]) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Main main = context.getBean(Main.class);
        main.runApplication();
    }

    public void runApplication () {
        int input;
        do {
            System.out.println("STUDENT DETAILS:");

            System.out.println("Choose the operation\n" +
                    "1. Storing the details\n" +
                    "2. Display student details\n" +
                    "3. Search by ID\n" +
                    "4. Remove by ID\n" +
                    "5. Display class room details\n" +
                    "6. Display Address of Particular student\n" +
                    "7. Manage Sports details\n" +
                    "8. Participation of students into the sports\n" +
                    "9. Exit\n");

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
                    addressController.displayAddressByStudentId();
                    break;

                case 7:
                    sportsActivityController.accessSportsDetails();
                    break;

                case 8:
                    sportsActivityController.addStudentToSports();
                    break;

                case 9:
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
