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
public class TransactionWindow extends List implements CommandListener {

    Account account;
    Vector transactions;
    private final Command backCommand;
    private final Command deleteCommand;
    private final Command editCommand;
    private final Command addCommand;

    TransactionWindow(Account account) {
        super(account.toString(), IMPLICIT);
        this.account = account;

        backCommand = new Command("Back", Command.BACK, 0);
        deleteCommand = new Command("Delete", Command.ITEM, 1);
        editCommand = new Command("Edit", Command.ITEM, 2);
        addCommand = new Command("Add", Command.ITEM, 3);

        addCommand(backCommand);
        addCommand(addCommand);
        setCommandListener(this);


        refreshTransactions();
        refreshCommands();

    }

    public void commandAction(Command c, Displayable d) {
        int selectedIndex = getSelectedIndex();
        if (c == backCommand) {
            AccountsWindow accountWindow = new AccountsWindow();
            Wallet.setCurrent(accountWindow);
        } else if (c == deleteCommand) {
            deleteTransaction(selectedIndex);
        } else if (c == editCommand) {
            showTransactionForm(selectedIndex);
        } else if (c == addCommand) {
            showTransactionForm(-1);
        }

    }

    private void updateTransaction(int selectedIndex, Transaction transaction) {
        Database.updateTransaction(transaction);
        transactions.removeElementAt(selectedIndex);
        transactions.insertElementAt(transaction, selectedIndex);
        String detail = getDetail(transaction, transaction.category);
        set(selectedIndex, detail, null);
    }

    private void deleteTransaction(int selectedIndex) {
        Transaction transaction = (Transaction) transactions.elementAt(selectedIndex);
        Database.deleteTransaction(transaction);
        transactions.removeElementAt(selectedIndex);
        delete(selectedIndex);
    }

    private void addTransaction(Transaction transaction) {
        Database.addTransaction(transaction);
        transactions.insertElementAt(transaction, 0);
        String detail = getDetail(transaction, transaction.category);
        insert(0, detail, null);
    }

    private void showTransactionForm(final int selectedIndex) {
        Transaction transaction = null;
        if (selectedIndex == -1) {
            transaction = new Transaction();
        } else {
            transaction = ((Transaction) transactions.elementAt(selectedIndex));
        }

        TransactionForm transactionForm = new TransactionForm("New Transaction", transaction) {
            public void formSubmitted(int responseType, Transaction transaction) {
                if (responseType == RESPONSE_TYPE_OK) {
                    if (selectedIndex == -1) {
                        addTransaction(transaction);
                    } else {
                        updateTransaction(selectedIndex, transaction);
                    }
                }
                Wallet.setCurrent(TransactionWindow.this);
            }
        };
        Wallet.setCurrent(transactionForm);
    }

    private void refreshTransactions() {
        //refreshing transactionList
        deleteAll();
        transactions = Database.getTransactions(account);
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = (Transaction) transactions.elementAt(i);
            String detail = getDetail(transaction, transaction.category);
            append(detail, null);
        }
    }

    private void refreshCommands() {
        //refreshing commands
        if (transactions.size() > 0) {
            addCommand(deleteCommand);
            addCommand(editCommand);
        } else {
            removeCommand(deleteCommand);
            removeCommand(editCommand);
        }
    }

    private String getDetail(Transaction transaction, Category cat) {
        return cat.name + ": " + transaction.description + "\n" + transaction.amount;
    }
}
