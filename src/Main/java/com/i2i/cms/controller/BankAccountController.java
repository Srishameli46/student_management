package com.i2i.cms.controller;

import java.util.Scanner;

import com.i2i.cms.exception.BankDetailException;
import com.i2i.cms.model.BankAccount;
import com.i2i.cms.service.BankAccountService;

public class BankAccountController {
  private static Scanner scanner = new Scanner(System.in);
  private static BankAccountService bankAccountService = new BankAccountService();

  public static void main(String args[]) {
    System.out.print("Enter bank name: ");
    String bankName = scanner.nextLine();
    System.out.print("Enter account holder name: ");
    String accountHolderName = scanner.nextLine();
    System.out.print("Enter branch: ");
    String branch = scanner.nextLine();
    System.out.print("Enter account number: ");
    String accountNumber = scanner.nextLine();
    BankAccount account = new BankAccount();
    account.setBankName(bankName);
    account.setAccountHolderName(accountHolderName);
    account.setBranch(branch);
    account.setAccountNumber(accountNumber);
    try {
      account = bankAccountService.addBankDetails(account);
      if (null != account) { 
        System.out.println(account);
      } else {
        System.out.println("Bank account not saved!");
      }
    } catch (BankDetailException e) {
        System.out.println(e.getMessage());
    }
  }
}
   