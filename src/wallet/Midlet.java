/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

import com.sun.lwuit.Display;
import javax.microedition.midlet.*;

/**
 * @author Imran
 */
public class Midlet extends MIDlet {

    Database database;

    public void startApp() {
        Display.init(this);
        database = new Database();
        AccountsForm accountsForm = new AccountsForm(database);
        accountsForm.show();

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
