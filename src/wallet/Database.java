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

    void close() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void deleteAccount(Account account) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addAccount(Account account) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void updateAccount(Account existingAccount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
