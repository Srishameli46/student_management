package com.i2i.sms.controller;

import java.util.Scanner;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Address;
import com.i2i.sms.service.AddressService;

/**
 * <p>
 * Display all Address details based on the student Id.
 * </p>
 */
public class AddressController {
  private AddressService addressService = new AddressService();
  private Scanner scanner = new Scanner(System.in);

  public void displayAddressByStudentId() {
    System.out.println("Enter student Id to display address:");
    int id = scanner.nextInt();
    try {
      Address address = addressService.getAddressByStudentId(id);
      if (null == address) {
        System.out.println("No Address available.\n");
      } else {
          System.out.println("Student Id:"+id);
          System.out.println(address);
      }
    } catch (StudentException e) {
      System.out.println(e.getMessage());
    }
  }
}
