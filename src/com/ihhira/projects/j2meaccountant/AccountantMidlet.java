/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import com.ihhira.projects.j2meaccountant.model.Database;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;

/**
 * @author Imran
 */
public class AccountantMidlet extends MIDlet {

    static AccountantMidlet accountantMidlet;

    public void startApp() {
        Display.init(this);
        Database.init();

        accountantMidlet = this;

        

        try {
            Resources resource = Resources.open("/lwuitResource.res");
            UIManager.getInstance().setThemeProps(resource.getTheme("touch"));
        } catch (IOException ioe) {
            Log.log("Couldn't load theme.");
            ioe.printStackTrace();
        }

        AccountsWindow accountsWindow = new AccountsWindow();
        accountsWindow.show();
    }

    public void pauseApp() {
        Database.close();
        System.gc();
    }

    /**
     *
     * @param unconditional
     */
    public void destroyApp(boolean unconditional) {
        Database.close();
    }

    public static void exit() {
        accountantMidlet.destroyApp(true);
        accountantMidlet.notifyDestroyed();
    }
}
