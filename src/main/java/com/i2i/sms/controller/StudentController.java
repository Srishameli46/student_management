package com.i2i.sms.controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Address;
import com.i2i.sms.models.Grade;
import com.i2i.sms.models.SportsActivity;
import com.i2i.sms.models.Student;
import com.i2i.sms.service.AddressService;
import com.i2i.sms.service.GradeService;
import com.i2i.sms.service.SportsActivityService;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.utils.DataValidationUtils;
import com.i2i.sms.utils.DateUtils;

/**
 * <p>
 * Student controller handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new student details, grade details and address along with the other operations,includes
 * - retrieving the whole student details along with their associated grade and sports activity
 * - removing the student from all associated grade, address detail and the sports activities.
 * </p>
 */
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private StudentService studentService = new StudentService();
    private AddressService addressService = new AddressService();
    private GradeService gradeService = new GradeService();
    private SportsActivityService sportsActivityService = new SportsActivityService();
    private Scanner scanner = new Scanner(System.in);

    /**
     * <p>
     * Get and create student details such as name, Date of birth and standard.
     * </p>
     **/
    public void createStudent() {
        logger.info("Starting to create a new student");
        System.out.println("\nENTER DETAILS\n");
        boolean isValidName = false;
        String name = "";
        while (!isValidName) {
            System.out.println("Enter name:");
            name = scanner.nextLine();
            isValidName = DataValidationUtils.validString(name);
            if (!isValidName) {
                System.out.println("Invalid name format:"+ name + "\n");
            }
        }

        System.out.println("Enter Date of birth (yyyy-MM-dd):");
        boolean isValidDate = true;
        Date validDob = null;
        do {
            String dob = scanner.nextLine();
            validDob = DateUtils.checkAndFormatDate(dob);
            if (null == validDob) {
                System.out.println("Invalid Date format:" + dob +"\n");
            } else {
                isValidDate = false;
            }
        } while (isValidDate);

        System.out.println("Enter the standard :");
        int standard = scanner.nextInt();
        Address address = addAddress();
        try {
            Grade grade = gradeService.addGrade(standard);
            Student student = studentService.addStudent(name, validDob, address, grade);
            System.out.println(student);
            logger.info("Created student id: {}",student.getId());
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * <p>
     * Display student details such as name, Date of birth, Age, Id Along with associate grades.
     * </p>
     **/
    public void displayStudent() {
        logger.info("Displaying all students with their details.");
        System.out.println("\nDISPLAY STUDENT DETAILS");
        try {
            List<Student> allStudents = studentService.getAllStudents();
            if (allStudents.isEmpty()) {
                System.out.println("No students available\n");
            } else {
                for (Student student : allStudents) {
                    System.out.println(student + "\n" + student.getSportsActivities());
                }
            }
            logger.info("Retrieved and displayed students details.");
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * <p>
     * Search students by their Id.
     * </p>
     */
    public void searchStudent() {
        System.out.println("\nSearch By ID");
        System.out.println("Enter student ID:");
        int searchId = scanner.nextInt();
        logger.info("Searching for student ID: {}", searchId);
        try {
            Student foundStudent = studentService.searchStudentById(searchId);
            if (null != foundStudent) {
                System.out.println(foundStudent);
                for (SportsActivity sport : foundStudent.getSportsActivities()) {
                    System.out.println(sport);
                }
                logger.info("Searched student ID: {} founded", searchId);
            } else {
                System.out.println("Student not found\n");
                logger.info("Searched student ID: {} not founded", searchId);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * <p>
     * Remove students by their Id.
     * </p>
     */
    public void removeStudent() {
        System.out.println("\nRemove Student by Id");
        System.out.println("Enter student ID: ");
        int removeId = scanner.nextInt();
        logger.info("Removing student Id: {}", removeId);
        try {
            boolean removedStudent = studentService.removeStudentById(removeId);
            if (removedStudent) {
                System.out.println("Student removed successfully.\n");
                logger.info("Removed student Id: {}", removeId);
            } else {
                System.out.println("Student not found\n");
                logger.info("Student Id: {} not found to remove", removeId);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * <p>
     * Add address for the students.
     * </p>
     */
    public Address addAddress() {
        logger.info("Adding address to student");
        System.out.println("ADD ADDRESS TO THE STUDENT DETAILS:\n");
        System.out.print("Enter doorNo\n");
        scanner.nextLine();
        String doorNo = scanner.nextLine();
        System.out.print("Enter street name:\n");
        String street = scanner.nextLine();
        System.out.print("Enter city:\n");
        String city = scanner.nextLine();
        System.out.print("Enter state name:\n");
        String state = scanner.nextLine();
        boolean isValidPinCode = false;
        String pinCode = "";
        while (!isValidPinCode) {
            System.out.println("Enter Pin Code:");
            pinCode = scanner.nextLine();
            isValidPinCode = DataValidationUtils.validPinCode(pinCode);
            if (!isValidPinCode) {
                System.out.println("Invalid Input\n");
            }
        }
        Address address = new Address();
        address.setDoorNo(doorNo);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPinCode(pinCode);
        logger.info("Added address to student");
        return address;
    }
}

