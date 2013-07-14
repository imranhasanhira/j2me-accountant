/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Imran
 */
public abstract class DialogBox extends Form {

    public static final int RESPONSE_TYPE_OK = 0;
    public static final int RESPONSE_TYPE_CANCEL = 0;
    Displayable displayable;
    TextField textField;

    public DialogBox(Displayable displayable, String title, String label, String text, int maxSize) {
        super(title);

        this.displayable = displayable;
        textField = new TextField(label, text, maxSize, TextField.ANY);
        append(textField);
    }

    public void show() {
        addCommand(new Command("Ok", Command.OK, 0));
        addCommand(new Command("Cancel", Command.CANCEL, 0));

        setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                actionPerformed(c.getCommandType() == Command.OK ? RESPONSE_TYPE_OK : RESPONSE_TYPE_CANCEL, textField.getString());
                Wallet.setCurrent(displayable);
            }
        });
        Wallet.setCurrent(this);
    }

    protected abstract void actionPerformed(int response, String text);
}
