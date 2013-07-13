/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import java.util.Vector;

/**
 *
 * @author Imran
 */
public class Database {

    static Vector accounts;
    static Vector categories;
    static Vector transactions;

    public static void init() {
        accounts = new Vector();
        accounts.addElement(new Account());
        accounts.addElement(new Account());
        accounts.addElement(new Account());

        categories = new Vector();
        categories.addElement(new Category());
        categories.addElement(new Category());
        categories.addElement(new Category());

        transactions = new Vector();
        transactions.addElement(new Transaction());
        transactions.addElement(new Transaction());
        transactions.addElement(new Transaction());
        transactions.addElement(new Transaction());
        transactions.addElement(new Transaction());
    }

    public static Vector getAccounts() {
        return accounts;
    }

    public static Vector getCategories() {
        return categories;
    }

    public static Vector getTransactions() {
        return transactions;
    }

    static void close() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void deleteAccount(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (account.id == ((Account) accounts.elementAt(i)).id) {
                accounts.removeElement(account);
                System.out.println("Account deleted : " + account.name);
                break;
            }
        }
    }

    static void addAccount(Account account) {
        accounts.addElement(account);
        System.out.println("Account added : " + account.name);
    }

    static void updateAccount(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            Account vAccount = ((Account) accounts.elementAt(i));
            if (account.id == vAccount.id) {
                vAccount.id = account.id;
                vAccount.name = account.name;
                vAccount.totalBalance = account.totalBalance;
                System.out.println("Account updated : " + account.name);
                break;
            }
        }
    }
}
