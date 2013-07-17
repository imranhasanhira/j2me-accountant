/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

/**
 *
 * @author Imran
 */
public abstract class InputForm extends Form {

    public static final int RESPONSE_TYPE_OK = 0;
    public static final int RESPONSE_TYPE_CANCEL = 0;
    Form previousForm;
    TextField textField;

    public InputForm(Form previousForm, String title, String label, String text) {
        super(title);

        this.previousForm = previousForm;
        addComponent(new Label(label));

        textField = new TextField(text, 20);
        addComponent(textField);


    }

    public void show() {
        addCommand(new Command("Ok", RESPONSE_TYPE_OK));
        addCommand(new Command("Cancel", RESPONSE_TYPE_CANCEL));

        addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                formSubmitted(ae.getCommand().getId(), textField.getText());
                previousForm.show();
            }
        });

        super.show();
    }

    protected abstract void formSubmitted(int response, String text);
}
