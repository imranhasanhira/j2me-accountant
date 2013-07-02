/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

/**
 *
 * @author Imran
 */
public class Category {

    long id;
    String name;

    public Category() {
        this(-1, "cat");
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
