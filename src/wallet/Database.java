/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

import java.util.Vector;

/**
 *
 * @author Imran
 */
public class Database {

    public Vector getAccounts() {
        Vector v = new Vector();
        v.addElement(new Account());
        v.addElement(new Account());
        v.addElement(new Account());
        return v;
    }

    public Vector getCategories() {
        Vector v = new Vector();
        v.addElement(new Category());
        v.addElement(new Category());
        v.addElement(new Category());
        return v;
    }

    public Vector getTransactions() {
        Vector v = new Vector();
        v.addElement(new Transaction());
        v.addElement(new Transaction());
        v.addElement(new Transaction());
        v.addElement(new Transaction());
        v.addElement(new Transaction());
        return v;
    }
}
