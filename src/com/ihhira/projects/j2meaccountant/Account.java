/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Imran
 */
public class Account implements Persistable {

    int id;
    String name;

    public Account() {
        this("");
    }

    public Account(String name) {
        this(-1, name);
    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public void copyTo(Account acc) {
        acc.id = id;
        acc.name = name;
    }
}
