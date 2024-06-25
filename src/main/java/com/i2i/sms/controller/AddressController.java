package com.i2i.sms.controller;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Address;
import com.i2i.sms.service.AddressService;

/**
 * <p>
 * Display all Address details based on the student Id.
 * </p>
 */
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);
    private AddressService addressService = new AddressService();
    private Scanner scanner = new Scanner(System.in);

    public void displayAddressByStudentId() {
        System.out.println("Enter student Id to display address:");
        int id = scanner.nextInt();
        logger.info("Displaying Address for the student id: {}", id);
        try {
            Address address = addressService.getAddressByStudentId(id);
            logger.info("Retrieved Address for the student id: {}", id);
            if (null == address) {
                System.out.println("No Address available.\n");
                logger.info("Address not available for the student id: {}", id);
            } else {
                System.out.println("Student Id:" + id);
                System.out.println(address);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
        }
    }
}
