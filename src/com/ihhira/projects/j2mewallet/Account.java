/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

/**
 *
 * @author Imran
 */
public class Account {

    long id;
    String name;
    double totalBalance;

    public Account() {
        this("account");
    }

    public Account(String name) {
        this(-1, name, 0);
    }

    public Account(long id, String name, double totalBalance) {
        this.id = id;
        this.name = name;
        this.totalBalance = totalBalance;
    }

    public String toString() {
        return name + "(" + totalBalance + ")";
    }
}
