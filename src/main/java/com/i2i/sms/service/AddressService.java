package com.i2i.sms.service;

import com.i2i.sms.dao.AddressDao;
import com.i2i.sms.models.Address;

/**
 * <p>
 * Class implemented to store, collect, search and remove the address details for the particular student.
 * </p>
 */

public class AddressService {
    private AddressDao addressDao = new AddressDao();

    /**
     * <p>
     * Add the Student address details which will be associated with the student.
     * </p>
     *
     * @param Address For which address could not be null.
     *                The door number contains numerical values and some alphabets or special character(/,-).
     *                The street name contains only alphabets and does not contains numbers or special characters.
     *                The city name contains only alphabets and does not contains numbers or special characters.
     *                The state name contains only alphabets and does not contains numbers or special characters.
     *                The pin code contains only numerical values upto 6-digits and does not contains alphabets or special characters.
     */
    public void insertAddress(Address address) {
        addressDao.insertAddress(address);
    }

    /**
     * <p>
     * Get student's address details using their id.
     * </p>
     *
     * @param Id Student unique Id given in String.
     * @return a address of the student.
     */
    public Address getAddressByStudentId(int studentId) {
        return addressDao.getAddressByStudentId(studentId);
    }

}