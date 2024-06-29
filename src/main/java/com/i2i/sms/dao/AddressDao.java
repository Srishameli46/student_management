package com.i2i.sms.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.models.Address;


/**
 * <p>
 * Class implemented to store, collect, search and remove the student details.
 * </p>
 */
@Repository
@Component
public class AddressDao {

    private static final Logger logger = LogManager.getLogger(AddressDao.class);
    /**
     * <p>
     * Get the address of the each student by the given studentId.
     * </p>
     *
     * @param studentId For which studentId is get from the user.
     * @return Address
     * Address of the student will be retrieved with the door number, street name, city name, state name and pin code.
     * @throws StudentException when there is no details on address to fetch by that particular student Id.
     */
    public Address getAddressByStudentId(int studentId) {
        Address address = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            address = session.get(Address.class, studentId);
        } catch (Exception e) {
            throw new StudentException("Unable to get the address of the student id " + studentId + ", since no values assigned....", e);
        }
        return address;
    }

    /**
     * <p>
     * Set the address of the each student that have the door number, street name, city name, state name and pin code.
     * This generates address id in auto incrementation.
     * </p>
     *
     * @param address For which address could not be null.
     *                The door number contains numerical values and some alphabets or special character(/).
     *                The street name contains only alphabets and does not contain numbers or special characters.
     *                The city name contains only alphabets and does not contain numbers or special characters.
     *                The state name contains only alphabets and does not contain numbers or special characters.
     *                The pin code contains only numerical values upto 6-digits and does not contain alphabets or special characters.
     * @throws StudentException when there is no address details provided.
     */
    public void insertAddress(Address address) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            logger.debug("Starting transaction to insert address detail ");
            session.save(address);
            transaction.commit();
        } catch (Exception e) {
            if (null != transaction) {
                transaction.rollback();
                logger.warn("Transaction rolled back while inserting address detail");

            }
            throw new StudentException("Unable to set the address\n" + "\nPlease check the details provided", e);
        }
    }
}
