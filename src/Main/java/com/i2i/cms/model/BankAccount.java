package com.i2i.cms.model;

public class BankAccount {

  private int id;
  private String bankName;
  private String accountHolderName;
  private String branch;
  private String accountNumber;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public void setAccountHolderName(String accountHolderName) {
    this.accountHolderName = accountHolderName;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String toString() {
    StringBuilder details = new StringBuilder();
      details.append("\nBank Details :")
             .append("\nID          : ").append(id)
             .append("\nAccount Holder Name : ").append(accountHolderName)
             .append("\nBank Name      : ").append(bankName)
             .append("\nAccount number : ").append(accountNumber)
             .append("\nBranch         : ").append(branch).append("\n");
    return details.toString();
  } 
}
