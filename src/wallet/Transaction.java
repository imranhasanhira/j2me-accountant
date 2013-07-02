/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

/**
 *
 * @author Imran
 */
public class Transaction {

    long id;
    String title;
    String description;
    long categoryID;
    long primaryAccountID;
    long secondaryAccountID;
    double amount;

    public Transaction() {
        title = "title";
    }
}
