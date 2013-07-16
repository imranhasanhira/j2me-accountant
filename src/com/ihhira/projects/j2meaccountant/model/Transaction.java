/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant.model;

import java.util.Date;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Imran
 */
public class Transaction implements Persistable {

    public int id;
    public Date date;
    public String description;
    public int categoryID;
    public int primaryAccountID;
    public boolean transfer;
    public int secondaryAccountID;
    public double amount;

    public Transaction() {
        id = -1;
        date = null;
        description = "";
        categoryID = 0;
        primaryAccountID = -1;
        transfer = false;
        secondaryAccountID = -1;
        amount = 0;
    }

    public void copyTo(Transaction t) {
        if (t != null) {
            t.id = id;
            t.date = date;
            t.description = description;
            t.categoryID = categoryID;
            t.primaryAccountID = primaryAccountID;
            t.transfer = transfer;
            t.secondaryAccountID = secondaryAccountID;
            t.amount = amount;
        }
    }

    public boolean match(Account account) {
        return primaryAccountID == account.id || secondaryAccountID == account.id;
    }

    public String toString() {
        return description;
    }
}
