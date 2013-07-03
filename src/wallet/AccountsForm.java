/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.FlowLayout;
import java.util.Vector;

/**
 *
 * @author Imran
 */
public class AccountsForm extends Form {

    Database database;
    Vector accounts;

    public AccountsForm(Database database) {
        this.database = database;
        this.setLayout(new FlowLayout());



        addCommand(new Command("Exit", Constants.CT_EXIT));
        addCommand(new Command("Add", Constants.CT_ADD));
        addCommand(new Command("Edit", Constants.CT_EDIT));
        addCommand(new Command("Delete", Constants.CT_DELETE));
        addCommand(new Command("Categories", Constants.CT_CATEGORIES));
        addCommand(new Command("Settings", Constants.CT_SETTINGS));

        addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Command command = ae.getCommand();
                switch (command.getId()) {
                    case Constants.CT_ADD:
                        addOrUpdateAccount(null);
                        break;

                    case Constants.CT_EDIT:
                        editAccount(AccountsForm.this.getFocused());
                        break;

                    case Constants.CT_DELETE:
                        deleteAccount(AccountsForm.this.getFocused());
                        break;

                    case Constants.CT_CATEGORIES:
                        showCategories();
                        break;

                    case Constants.CT_SETTINGS:
                        showSettings();
                        break;

                    case Constants.CT_EXIT:
                        AccountsForm.this.database.close();
                        System.exit(0);
                        break;
                }
            }
        });


        refreshAccounts();

    }

    private void refreshAccounts() {
        removeAll();
        accounts = database.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            addAccountToUI(i);
        }
    }

    private void deleteAccount(Component focused) {
        if (focused == null) {
            return;
        }

        int index = ((TextArea) focused).getIndex();
        Account account = (Account) accounts.elementAt(index);
        database.deleteAccount(account);
        accounts.removeElementAt(index);
        removeComponent(focused);
    }

    private void editAccount(Component focused) {
        if (focused == null) {
            return;
        }

        int index = ((TextArea) focused).getIndex();
        Account account = (Account) accounts.elementAt(index);
        addOrUpdateAccount(account);
    }

    private void addOrUpdateAccount(final Account existingAccount) {
        final TextField tf = new TextField();
        if (existingAccount != null) {
            tf.setText(existingAccount.name);
        }

        Command okCommand = new Command("Save");
        Command cancelCommand = new Command("Cancel");
        Command[] commands = new Command[]{okCommand, cancelCommand};

        Dialog d = new Dialog();
        d.addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getCommand().getCommandName().equals("Save")) {
                    String accountName = tf.getText();
                    if (accountName.length() > 0) {
                        if (existingAccount == null) {
                            Account account = new Account(accountName);
                            database.addAccount(account);
                            accounts.addElement(account);
                            addAccountToUI(accounts.size() - 1);
                        } else {
                            existingAccount.name = accountName;
                            database.updateAccount(existingAccount);
                        }
                    }
                } else {
                }
            }
        });
        d.show();

        Dialog.show(
                "Type name", tf, commands);
    }

    private void showCategories() {
        new CategoriesForm().show();
    }

    private void showSettings() {
        new SettingsForm().show();
    }

    private void showTransactions(long index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addAccountToUI(int i) {
        final TextArea textArea = new TextArea(i);
        textArea.setText(((Account) accounts.elementAt(i)).name);
        textArea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                showTransactions(textArea.getIndex());
            }
        });
        addComponent(i, textArea);
    }
}
