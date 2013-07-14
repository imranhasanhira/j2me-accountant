/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import java.util.Vector;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Imran
 */
public abstract class TransactionForm extends Form {

    public static final int RESPONSE_TYPE_OK = 0;
    public static final int RESPONSE_TYPE_CANCEL = 1;
    Vector categoriesVector;
    ChoiceGroup category;
    TextField description;
    TextField amount;

    public TransactionForm(String title, final Transaction transaction) {
        super(title);

        //category
        categoriesVector = Database.getCategories();
        String[] categories = new String[categoriesVector.size()];
        int selectedCategoryIndex = -1;
        for (int i = 0; i < categories.length; i++) {
            categories[i] = ((Category) categoriesVector.elementAt(i)).name;
            if (transaction.id == ((Category) categoriesVector.elementAt(i)).id) {
                selectedCategoryIndex = i;
            }
        }
        category = new ChoiceGroup("Category", ChoiceGroup.POPUP, categories, null);
        append(category);
        if (selectedCategoryIndex != -1) {
            category.setSelectedIndex(selectedCategoryIndex, true);
        }

        //
        description = new TextField("Description", transaction.description, 20, TextField.ANY);
        append(description);

        //
        amount = new TextField("Amount", "" + transaction.amount, 20, TextField.DECIMAL);
        append(amount);


        addCommand(new Command("Cancel", Command.CANCEL, RESPONSE_TYPE_CANCEL));
        addCommand(new Command("OK", Command.OK, RESPONSE_TYPE_OK));

        setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                try {
                    if (c.getCommandType() == Command.OK) {
                        transaction.description = description.getString();
                        transaction.amount = Double.parseDouble(amount.getString());
                        Log.log("selectedindex = " + category.getSelectedIndex());
                        transaction.category = ((Category) categoriesVector.elementAt(category.getSelectedIndex()));
                    }
                    int responseType = (c.getCommandType() == Command.OK ? RESPONSE_TYPE_OK : RESPONSE_TYPE_CANCEL);
                    formSubmitted(responseType, transaction);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public abstract void formSubmitted(int responseType, Transaction transaction);
}
