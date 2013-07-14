/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 * @author Imran
 */
public class Wallet extends MIDlet {

    static Display display;
    static Wallet wallet;

    public void startApp() {
        display = Display.getDisplay(this);
        wallet = this;

        Database.init();

        AccountsWindow accountsWindow = new AccountsWindow();
        setCurrent(accountsWindow);
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

    public static void setCurrent(Displayable displayable) {
        display.setCurrent(displayable);
    }

    public static void setCurrent(String title, String text, AlertType type, Displayable displayable) {
        Alert alert = new Alert(title, text, null, type);
        display.setCurrent(alert, displayable);
    }

    public static void exit() {
        wallet.destroyApp(true);
        wallet.notifyDestroyed();
    }
}
