/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Imran
 */
public abstract class DialogBox {

    public static final int RESPONSE_TYPE_OK = 0;
    public static final int RESPONSE_TYPE_CANCEL = 0;
    Displayable displayable;
    TextBox textbox;

    public DialogBox(Displayable displayable, String label, String text, int maxSize) {
        this.displayable = displayable;
        textbox = new TextBox(label, text, maxSize, TextField.ANY);
    }

    public void show() {
        textbox.addCommand(new Command("Ok", Command.OK, 0));
        textbox.addCommand(new Command("Cancel", Command.CANCEL, 0));
        textbox.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                actionPerformed(c.getCommandType() == Command.OK ? RESPONSE_TYPE_OK : RESPONSE_TYPE_CANCEL, textbox.getString());
                Wallet.setCurrent(displayable);
            }
        });
        Wallet.setCurrent(textbox);
    }

    protected abstract void actionPerformed(int response, String text);
}
