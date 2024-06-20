package com.i2i.cms.dao;

import org.hibernate.Session;       
import org.hibernate.Transaction;  

import com.i2i.cms.exception.BankDetailException;
import com.i2i.cms.helper.HibernateConnection;
import com.i2i.cms.model.BankAccount;

public class BankAccountDao {

  public BankAccount insertBankAccount(BankAccount bankAccount) {
    Transaction transaction = null;
    try (Session session = HibernateConnection.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(bankAccount);
      transaction.commit();            
    } catch (Exception e) {
        if (null != transaction) {
           transaction.rollback();
        }
        throw new BankDetailException("Unable to set the BankAccount number " + bankAccount.getAccountNumber() + "\nPlease check the details provided", e);
    }
    return bankAccount;
  } 

}