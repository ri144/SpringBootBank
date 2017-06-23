package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by student on 6/23/17.
 */
@Entity
public class Holder {
    @Id
    private int acct;

    public int getAcct() {
        return acct;
    }

    public void setAcct(int acct) {
        this.acct = acct;
    }
}
