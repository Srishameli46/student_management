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
     * @throws StudentException when the address can not be accessed.
     */
    public AddressResponseDto getAddressByStudentId(String studentId) {
        try {
            Optional<Address> address =  addressRepository.findByStudentId(studentId);
            if(address.isPresent()){
                Address addressDetail = address.get();
                return new AddressResponseDto(addressDetail);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving address of the student id: {}", studentId, e);
            throw new StudentException("Failed to retrieve address of the student id " + studentId, e);
        }
    }

    /**
     * <p>
     * Delete student's address details using their address id.
     * </p>
     *
     * @param addressId Student unique Id given in uuid.
     * @throws StudentException when the address can not be deleted.
     */
    public void deleteById(String addressId) {
        try {
            Optional<Address> address = addressRepository.findById(addressId);
            if (address.isPresent()) {
                Address addressToDelete = address.get();
                addressRepository.deleteById(addressToDelete.getAddressId());
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting the address: {}", addressId, e);
            throw new StudentException("Failed to delete address with ID " + addressId, e);
        }
    }
}