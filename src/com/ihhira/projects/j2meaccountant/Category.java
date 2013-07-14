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
public class Category implements Persistable {

    int id;
    String name;

    public Category() {
        this(0, "cat");
    }

    public Category(String name) {
        this(0, name);
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
