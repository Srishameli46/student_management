package com.i2i.sms.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.AddressResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.models.Address;
import com.i2i.sms.repository.AddressRepository;

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
     * @param studentId Student unique Id given in uuid.
     * @return a address of the student.
     */
    public AddressResponseDto getAddressByStudentId(String studentId) {
        try {
            Optional<Address> address =  addressRepository.findById(studentId);
            if(address.isPresent()){
                Address addressDetail = address.get();
                return new AddressResponseDto(addressDetail);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error("An error occurred while saving the student: {}", studentId, e);
            throw new StudentException("Failed to save student with ID " + studentId, e);
        }
    }

    /**
     * <p>
     * Delete student's address details using their address id.
     * </p>
     *
     * @param addressId Student unique Id given in uuid.
     *
     */
    public void deleteById(String addressId) {
        try {
            Optional<Address> address = addressRepository.findById(addressId);
            if (address.isPresent()) {
                Address addressToDelete = address.get();
                addressRepository.deleteById(addressToDelete.getAddressId());
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the address: {}", addressId, e);
            throw new StudentException("Failed to retrieve address with ID " + addressId, e);
        }
    }
}