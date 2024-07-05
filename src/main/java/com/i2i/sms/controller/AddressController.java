package com.i2i.sms.controller;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.dto.AddressResponseDto;
import com.i2i.sms.service.AddressService;


/**
 * <p>
 * Display all Address details based on the student Id.
 * </p>
 */
@RestController
@RequestMapping("sms/api/1.0/address")
public class AddressController {
    private static final Logger logger = LogManager.getLogger(AddressController.class);
    @Autowired
    private AddressService addressService;
    private Scanner scanner = new Scanner(System.in);


    /**
     * <p>
     * Display all Address details based on the student Id.
     * </p>
     *
     * @param id This is the unique student id that must be numerical.
     * @return AddressResponseDto contains details of the address of particular student.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> displayAddressByStudentId(@PathVariable int id) {
        logger.info("Displaying Address for the student id: {}", id);
        try {
            AddressResponseDto address = addressService.getAddressByStudentId(id);
            if (null == address) {
                logger.info("Address not available for the student id: {}", id);
                return new ResponseEntity<>(address, HttpStatus.NOT_FOUND);
            } else {
                logger.info("Retrieved Address for the student id: {}", id);
                return new ResponseEntity<>(address, HttpStatus.FOUND);
            }
        } catch (StudentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
