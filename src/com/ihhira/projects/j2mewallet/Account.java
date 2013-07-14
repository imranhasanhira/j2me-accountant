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
public class Account implements Persistable{

    int id;
    String name;
    double totalBalance;

    public Account() {
        this("account");
    }

    public Account(String name) {
        this(-1, name, 0);
    }

    public Account(int id, String name, double totalBalance) {
        this.id = id;
        this.name = name;
        this.totalBalance = totalBalance;
    }

    public String toString() {
        return name + "(" + totalBalance + ")";
    }

    public void copyTo(Account acc) {
        acc.id = id;
        acc.name = name;
        acc.totalBalance = totalBalance;
    }
}
