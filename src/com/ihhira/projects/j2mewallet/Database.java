/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import java.util.Vector;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 *
 * @author Imran
 */
public class Database {

    static PersistableManager pm;
    static Vector transactions;

    public static void init() {
        pm = PersistableManager.getInstance();
    }

    static void close() {
        try {
            if (pm != null) {
                pm.shutdown();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Vector getAccounts() {
        Vector accounts = new Vector();
        try {
            Log.log("Listing accounts...");
            ObjectSet taccounts = pm.find(Account.class, null, null);
            accounts.ensureCapacity(taccounts.size());
            for (int i = 0; i < taccounts.size(); i++) {
                accounts.addElement(taccounts.get(i));
            }
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        
        if (accounts.size() <= 0) {
            addAccount(new Account("Current Account"));
            accounts = Database.getAccounts();
        }
        return accounts;
    }

    static void deleteAccount(Account account) {
        try {
            removeAllTransactionFromAccount(account);
            pm.delete(account);
            Log.log("Account deleted " + account.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    static void addAccount(Account account) {
        try {
            account.id = pm.save(account);
            Log.log("Account added : " + account.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

    }

    static void updateAccount(Account account) {
        try {
            pm.save(account);
            Log.log("Account updated : " + account.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    public static Vector getCategories() {
        Vector categories = new Vector();
        try {
            Log.log("Listing categories...");
            ObjectSet tcategories = pm.find(Category.class, null, null);
            categories.ensureCapacity(tcategories.size());
            for (int i = 0; i < tcategories.size(); i++) {
                categories.addElement(tcategories.get(i));
            }
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

        if (categories.size() <= 0) {
            addCategory(new Category("Miscellaneous"));
            categories = null;
            categories = getCategories();
        }
        return categories;
    }

    public static Category getCategory(int id) {
        try {
            Category category = new Category();
            pm.load(category, id);
            return category;
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    static void addCategory(Category category) {
        try {
            category.id = pm.save(category);
            Log.log("Category added " + category.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    static void updateCategory(Category category) {
        try {
            pm.save(category);
            Log.log("Category updagted " + category.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    static void deleteCategory(Category category) {
        try {
            pm.delete(category);
            Log.log("Category deleted " + category.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    public static Vector getTransactions(final Account account) {
        Log.log("Listing transactions...");
        Vector traVector = new Vector();
        try {
            ObjectSet transactions = pm.find(Transaction.class, new Filter() {
                public boolean matches(Persistable prstbl) {
                    return ((Transaction) prstbl).match(account);
                }
            }, null);
            traVector.ensureCapacity(transactions.size());
            for (int i = 0; i < transactions.size(); i++) {
                traVector.addElement(transactions.get(i));
            }
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        return traVector;
    }

    static void deleteTransaction(Transaction transaction) {
        try {
            pm.delete(transaction);
            Log.log("Transaction deleted : " + transaction.description);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    static void updateTransaction(Transaction transaction) {
        try {
            pm.save(transaction);
            Log.log("Transaction updated : " + transaction.description);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

    }

    static void addTransaction(Transaction transaction) {
        try {
            pm.save(transaction);
            Log.log("Transaction added : " + transaction.description);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }

    private static void removeAllTransactionFromAccount(final Account account) {
        try {
            ObjectSet transactions = pm.find(Transaction.class, new Filter() {
                public boolean matches(Persistable prstbl) {
                    return ((Transaction) prstbl).match(account);
                }
            }, null);
            for (int i = 0; i < transactions.size(); i++) {
                pm.delete(transactions.get(i));
            }
            Log.log("All Transaction added from " + account.name);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }
}
