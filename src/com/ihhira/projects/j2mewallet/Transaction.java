/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Imran
 */
public class Transaction implements Persistable {

    int id;
    String description;
    Category category;
    Account primaryAccount;
    Account secondaryAccount;
    double amount;

    public Transaction() {
        id = -1;
        description = "desc";
        category = null;
        primaryAccount = null;
        secondaryAccount = null;
        amount = 0;
    }

    public void copyTo(Transaction t) {
        if (t != null) {
            t.id = id;
            t.description = description;
            t.category = category;
            t.primaryAccount = primaryAccount;
            t.secondaryAccount = secondaryAccount;
            t.amount = amount;
        }
    }

    public boolean match(Account account) {
        return primaryAccount.name.equals(account.name) || secondaryAccount.name.equals(account.name);
    }

    public String toString() {
        return description;
    }
}
