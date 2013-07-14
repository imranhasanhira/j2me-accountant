/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import java.util.Vector;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author Imran
 */
public class AccountsWindow extends List implements CommandListener {

    Vector accounts;
    private final Command exitCommand;
    private final Command settingsCommand;
    private final Command categoriesCommand;
    private final Command deleteCommand;
    private final Command editCommand;
    private final Command addCommand;
    private final Command showCommand;

    public AccountsWindow() {
        super("Accounts", List.IMPLICIT);

        exitCommand = new Command("Exit", Command.EXIT, 0);
        settingsCommand = new Command("Settings", Command.ITEM, 1);
        categoriesCommand = new Command("Categories", Command.ITEM, 2);
        deleteCommand = new Command("Delete", Command.ITEM, 3);
        editCommand = new Command("Edit", Command.ITEM, 4);
        addCommand = new Command("Add", Command.ITEM, 5);
        showCommand = new Command("Show", Command.ITEM, 6);

        addCommand(exitCommand);
        addCommand(settingsCommand);
        addCommand(categoriesCommand);
        addCommand(deleteCommand);
        addCommand(editCommand);
        addCommand(addCommand);
        addCommand(showCommand);

        setCommandListener(this);


        refreshAccountList();

    }

    public void commandAction(Command c, Displayable d) {
        int selectedIndex = getSelectedIndex();
        if (c == exitCommand) {
            Wallet.exit();
        } else if (c == settingsCommand) {
            Wallet.setCurrent("Info", "Coming soon", AlertType.INFO, this);
        } else if (c == categoriesCommand) {
            CategoriesWindow categoriesWindow = new CategoriesWindow("Categories");
            Wallet.setCurrent(categoriesWindow);
        } else if (c == deleteCommand) {
            if (selectedIndex != -1) {
                Account account = (Account) accounts.elementAt(selectedIndex);
                Database.deleteAccount(account);
                accounts.removeElementAt(selectedIndex);
                delete(selectedIndex);
            }
        } else if (c == editCommand) {
            if (selectedIndex != -1) {
                Account account = (Account) accounts.elementAt(selectedIndex);
                Log.log("Editing account - " + account);
                showExistingAccountForm(account);
            }
        } else if (c == addCommand) {
            Log.log("Adding account");
            showNewAccountForm();
        } else if (c == showCommand) {
            if (selectedIndex != -1) {
                Account account = (Account) accounts.elementAt(selectedIndex);
                Log.log("Showing account - " + account);
                TransactionWindow tw = new TransactionWindow(account);
                Wallet.setCurrent(tw);
            }
        }
    }

    private void showNewAccountForm() {
        showAcountForm(null);
    }

    private void showExistingAccountForm(Account existingAccount) {
        showAcountForm(existingAccount);
    }

    /**
     *
     * @param <Account> existingAccount Performs two task. Add new account ( if
     * param is null
     */
    private void showAcountForm(final Account existingAccount) {
        String existingName = (existingAccount == null ? "" : existingAccount.name);
        DialogBox dialogBox = new DialogBox(this, "New Account", "Account name", existingName, 20) {
            protected void actionPerformed(int response, String text) {
                if (response == DialogBox.RESPONSE_TYPE_OK && text.length() > 0) {

                    if (existingAccount == null) {
                        Account acc = new Account(text);
                        Database.addAccount(acc);
                    } else {
                        existingAccount.name = text;
                        Database.updateAccount(existingAccount);
                    }
                    refreshAccountList();
                }
            }
        };
        dialogBox.show();
    }

    private void refreshAccountList() {
        this.deleteAll();
        accounts = Database.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = (Account) accounts.elementAt(i);
            append(acc.name, null);
        }
    }
}
