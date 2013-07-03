/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

/**
 *
 * @author Imran
 */
public class TextArea extends com.sun.lwuit.TextArea {

    private int index;

    TextArea(int index) {
        this.index = index;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}
