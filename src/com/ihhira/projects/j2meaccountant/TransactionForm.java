/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import java.util.Vector;
import javax.microedition.lcdui.AlertType;
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
    Vector accountsVector;
    Vector categoriesVector;
    ChoiceGroup secondaryAccount;
    ChoiceGroup category;
    TextField description;
    TextField amount;

    public TransactionForm(String title, final Transaction transaction) {
        super(title);

        if (transaction.transfer) {
            prepateSecondaryAccountChoiceGroup(transaction);
        } else {
            prepareCategoryChoiceGroup(transaction);
        }
        //
        description = new TextField("Description", transaction.description, 50, TextField.ANY);
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
                        try {
                            transaction.amount = Double.parseDouble(amount.getString());
                        } catch (Exception ex) {
                            throw new Exception("amount is not correct");
                        }

                        if (transaction.transfer) {
                            Log.log("selected account index = " + secondaryAccount.getSelectedIndex());
                            transaction.secondaryAccountID = ((Account) accountsVector.elementAt(secondaryAccount.getSelectedIndex())).id;
                            if (transaction.secondaryAccountID == transaction.primaryAccountID) {
                                throw new Exception("Please select another account");
                            }
                        } else {
                            Log.log("selected category index = " + category.getSelectedIndex());
                            transaction.categoryID = ((Category) categoriesVector.elementAt(category.getSelectedIndex())).id;
                        }
                    }
                    int responseType = (c.getCommandType() == Command.OK ? RESPONSE_TYPE_OK : RESPONSE_TYPE_CANCEL);
                    formSubmitted(responseType, transaction);
                } catch (Exception e) {
                    e.printStackTrace();
                    Accountant.setCurrent("Error", e.getMessage(), AlertType.ERROR, TransactionForm.this);
                }
            }
        });
    }

    public abstract void formSubmitted(int responseType, Transaction transaction);

    private void prepateSecondaryAccountChoiceGroup(final Transaction transaction) {
        accountsVector = Database.getAccounts();
        String[] accounts = new String[accountsVector.size()];
        int selectedAccountIndex = -1;
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = ((Account) accountsVector.elementAt(i)).name;
            if (transaction.secondaryAccountID == ((Account) accountsVector.elementAt(i)).id) {
                selectedAccountIndex = i;
            }
        }
        secondaryAccount = new ChoiceGroup("To", ChoiceGroup.POPUP, accounts, null);
        append(secondaryAccount);
        if (selectedAccountIndex != -1) {
            secondaryAccount.setSelectedIndex(selectedAccountIndex, true);
        }
    }

    private void prepareCategoryChoiceGroup(final Transaction transaction) {
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
    }
}
