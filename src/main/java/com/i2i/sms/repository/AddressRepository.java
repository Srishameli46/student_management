package com.i2i.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.i2i.sms.models.Address;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * This interface implemented to store, collect, search and remove the student address details.
 * </p>
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}