package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by student on 6/20/17.
 */
@Entity
public class Atm {

    private long custId;

    @Size(min=2, max=30)
    private String reason;

    private int acct;

    private double amount;

    private String action;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAcct() {
        return acct;
    }

    public void setAcct(int acct) {
        this.acct = acct;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
