package com.i2i.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.sms.models.Address;

/**
 * <p>
 * This interface implemented to store, collect, search and remove the student address details.
 * </p>
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}