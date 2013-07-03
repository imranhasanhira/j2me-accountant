/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

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
}
