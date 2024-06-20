package com.i2i.cms.service;

import com.i2i.cms.dao.BankAccountDao;
import com.i2i.cms.model.BankAccount;

public class BankAccountService {
   
  private BankAccountDao bankAccountDao = new BankAccountDao();

  // Create new bank account
  public BankAccount addBankDetails(BankAccount bankAccount) {
    return bankAccountDao.insertBankAccount(bankAccount); 
  }
}