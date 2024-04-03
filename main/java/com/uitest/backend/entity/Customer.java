package com.uitest.backend.entity;
// create table customer (
//     account_no number(19,0) not null,
//     ifsc varchar2(255 char),
//     amount number(10,0) not null,
//     name varchar2(255 char),
//     primary key (account_no)
// )

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Customer {

    @Id
    private long accountNo;

    private String name;

    private String IFSC;

    private int amount;


    public long getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIFSC() {
        return IFSC;
    }
    public void setIFSC(String iFSC) {
        IFSC = iFSC;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
   

}
