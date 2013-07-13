/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import java.util.Vector;
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
    private final int COMMAND_TYPE_EXIT = 0;
    private final int COMMAND_TYPE_SETTINGS = 1;
    private final int COMMAND_TYPE_CATEGORIES = 2;
    private final int COMMAND_TYPE_DELETE = 3;
    private final int COMMAND_TYPE_EDIT = 4;
    private final int COMMAND_TYPE_ADD = 5;
    private final int COMMAND_TYPE_SHOW = 6;

    public AccountsWindow() {
        super("Accounts", List.IMPLICIT);
        showAccounts();
        addCommands();
    }

    public void commandAction(Command c, Displayable d) {
        int selectedIndex = getSelectedIndex();
        switch (c.getPriority()) {
            case COMMAND_TYPE_EXIT:
                Wallet.exit();
                break;

            case COMMAND_TYPE_SETTINGS:
                break;

            case COMMAND_TYPE_CATEGORIES:
                break;

            case COMMAND_TYPE_DELETE:
                if (selectedIndex != -1) {
                    Account account = (Account) accounts.elementAt(selectedIndex);
                    Database.deleteAccount(account);
                    showAccounts();
                }
                break;

            case COMMAND_TYPE_EDIT:
                if (selectedIndex != -1) {
                    Account account = (Account) accounts.elementAt(selectedIndex);
                    Log.log("Editing account - " + account);
                    showExistingAccountForm(account);
                }
                break;

            case COMMAND_TYPE_ADD:
                Log.log("Adding account");
                showNewAccountForm();
                break;

            case COMMAND_TYPE_SHOW:
                if (selectedIndex != -1) {
                    Account account = (Account) accounts.elementAt(selectedIndex);
                    Log.log("Showing account - " + account);
                    TransactionWindow tw = new TransactionWindow(account);
                    Wallet.setCurrent(tw);
                }

                break;
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
        DialogBox dialogBox = new DialogBox(this, "Account name", existingName, 20) {
            protected void actionPerformed(int response, String text) {
                if (response == DialogBox.RESPONSE_TYPE_OK && text.length() > 0) {

                    if (existingAccount == null) {
                        Account acc = new Account(text);
                        Database.addAccount(acc);
                    } else {
                        existingAccount.name = text;
                        Database.updateAccount(existingAccount);
                    }
                    showAccounts();
                }
            }
        };
        dialogBox.show();
    }

    private void showAccounts() {
        this.deleteAll();
        accounts = Database.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = (Account) accounts.elementAt(i);
            append(acc.name, null);
        }
    }

    private void addCommands() {
        addCommand(new Command("Exit", Command.EXIT, COMMAND_TYPE_EXIT));
        addCommand(new Command("Settings", Command.ITEM, COMMAND_TYPE_SETTINGS));
        addCommand(new Command("Categories", Command.ITEM, COMMAND_TYPE_CATEGORIES));
        addCommand(new Command("Delete", Command.ITEM, COMMAND_TYPE_DELETE));
        addCommand(new Command("Edit", Command.ITEM, COMMAND_TYPE_EDIT));
        addCommand(new Command("Add", Command.ITEM, COMMAND_TYPE_ADD));
        addCommand(new Command("Show", Command.ITEM, COMMAND_TYPE_SHOW));

        setCommandListener(this);
    }
}
