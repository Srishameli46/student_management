package com.i2i.sms.service;

import com.i2i.sms.dto.AddressResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.models.Address;

/**
 * <p>
 * Class implemented to store, collect, search and remove the address details for the particular student.
 * </p>
 */
@Service
public class AddressServiceImpl implements AddressService{
    private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressRepository addressRepository;

    /**
     * <p>
     * Get student's address details using their id.
     * </p>
     *
     * @param studentId Student unique Id given in String.
     * @return a address of the student.
     */
    public AddressResponseDto getAddressByStudentId(int studentId) {
        try {
            return new AddressResponseDto(addressRepository.findById(studentId).orElse(null));
        } catch (Exception e) {
            logger.error("An error occurred while saving the student: {}", studentId, e);
            throw new StudentException("Failed to save student with ID " + studentId, e);
        }
    }

    /**
     * <p>
     * Get student's address details using their address id.
     * </p>
     *
     * @param addressId Student unique Id given in String.
     * @return address of the student.
     */
    public Address getById(int addressId) {
        try {
            return addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the address: {}", addressId, e);
            throw new StudentException("Failed to retrieve address with ID " + addressId, e);
        }
    }

}