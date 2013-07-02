/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import java.util.Vector;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;

/**
 * @author Imran
 */
public class Midlet extends MIDlet {

    Database database;

    public void startApp() {
        database = new Database();
        refreshAccounts();


    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    private void refreshAccounts2() {
        Vector accounts = database.getAccounts();
        javax.microedition.lcdui.List l = new javax.microedition.lcdui.List("lkust", List.EXCLUSIVE);
        for (int i = 0; i < accounts.size(); i++) {

            Account acc = (Account) accounts.elementAt(i);
            l.append(acc.name, null);
            l.getSelectedIndex();
            f.addComponent(new TextField("tf-" + i));
        }
    }

    private void refreshAccounts() {
        final Form f = new Form();

        Vector accounts = database.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            f.addComponent(new TextField("tf-" + i));
        }

        f.addCommand(new Command("Add", Constants.CT_ADD));
        f.addCommand(new Command("Edit", Constants.CT_EDIT));
        f.addCommand(new Command("Delete", Constants.CT_DELETE));
        f.addCommand(new Command("Categories", Constants.CT_CATEGORIES));
        f.addCommand(new Command("Settings", Constants.CT_SETTINGS));
        f.addCommand(new Command("Exit", Constants.CT_EXIT));
        f.addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Command command = ae.getCommand();
                switch (command.getId()) {
                    case Constants.CT_ADD:
                        showAddAccount();
                        break;

                    case Constants.CT_EDIT:
                        f.getFocused();
                        break;

                    case Constants.CT_DELETE:
                        break;

                    case Constants.CT_CATEGORIES:
                        break;

                    case Constants.CT_SETTINGS:
                        break;

                    case Constants.CT_EXIT:
                        break;
                }
            }
        });

        f.show();
    }

    private void showAddAccount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
