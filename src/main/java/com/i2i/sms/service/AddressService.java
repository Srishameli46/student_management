package com.i2i.sms.service;

import com.i2i.sms.dto.AddressResponseDto;
import com.i2i.sms.models.Address;

/**
 * <p>
 * Implementation to collect, search the address details for the particular student.
 * </p>
 */
public interface AddressService {

    AddressResponseDto getAddressByStudentId(String studentId);

    void deleteById(String addressId);
}
