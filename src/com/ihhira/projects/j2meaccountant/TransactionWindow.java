/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author Imran
 */
public class TransactionWindow extends List implements CommandListener {

    private final int TRANSACTION_DEPOSITE = -1;
    private final int TRANSACTION_SPEND = -2;
    private final int TRANSACTION_TRANSFER = -3;
    Account account;
    Vector transactions;
    private final Command backCommand;
    private final Command deleteCommand;
    private final Command editCommand;
    private final Command depositeCommand;
    private final Command spendCommand;
    private final Command transferCommand;

    TransactionWindow(Account account) {
        super(account.toString(), IMPLICIT);
        setFitPolicy(Choice.TEXT_WRAP_ON);
        this.account = account;


        backCommand = new Command("Back", Command.BACK, 0);
        deleteCommand = new Command("Delete", Command.ITEM, 1);
        editCommand = new Command("Edit", Command.ITEM, 2);
        depositeCommand = new Command("Add", Command.ITEM, 3);
        spendCommand = new Command("Spend", Command.ITEM, 4);
        transferCommand = new Command("Transfer fund", Command.ITEM, 6);

        addCommand(backCommand);
        addCommand(depositeCommand);
        addCommand(spendCommand);
        addCommand(transferCommand);
        setCommandListener(this);

        refreshTransactions();
        refreshCommands();

        if (transactions.size() <= 0) {
//            Accountant.setCurrent("Empty Transaction list", "Please add a transaction", AlertType.INFO, this);
        }

    }

    public void commandAction(Command c, Displayable d) {
        int selectedIndex = getSelectedIndex();
        if (c == backCommand) {
            AccountsWindow accountWindow = new AccountsWindow();
            Accountant.setCurrent(accountWindow);
        } else if (c == deleteCommand) {
            deleteTransaction(selectedIndex);
        } else if (c == editCommand) {
            Transaction transaction = ((Transaction) transactions.elementAt(selectedIndex));
            showTransactionForm(selectedIndex, transaction);
        } else if (c == depositeCommand) {
            Transaction transaction = new Transaction();
            transaction.primaryAccountID = account.id;
            showTransactionForm(TRANSACTION_DEPOSITE, transaction);
        } else if (c == spendCommand) {
            Transaction transaction = new Transaction();
            transaction.primaryAccountID = account.id;
            showTransactionForm(TRANSACTION_SPEND, transaction);
        } else if (c == transferCommand) {
            Transaction transaction = new Transaction();
            transaction.primaryAccountID = account.id;
            transaction.transfer = true;
            showTransactionForm(TRANSACTION_TRANSFER, transaction);
        }
    }

    private void updateTransaction(int selectedIndex, Transaction transaction) {
        Database.updateTransaction(transaction);
        transactions.removeElementAt(selectedIndex);
        transactions.insertElementAt(transaction, selectedIndex);
        String detail = getDetail(transaction);
        set(selectedIndex, detail, null);
    }

    private void deleteTransaction(int selectedIndex) {
        Transaction transaction = (Transaction) transactions.elementAt(selectedIndex);
        Database.deleteTransaction(transaction);
        transactions.removeElementAt(selectedIndex);
        delete(selectedIndex);
        refreshCommands();
    }

    private void addTransaction(Transaction transaction) {
        Database.addTransaction(transaction);
        transactions.insertElementAt(transaction, 0);
        String detail = getDetail(transaction);
        insert(0, detail, null);
        refreshCommands();
    }

    private void showTransactionForm(final int selectedIndex, Transaction transaction) {
        String title = null;
        if (selectedIndex == TRANSACTION_DEPOSITE) {
            title = "Deposite";
        } else if (selectedIndex == TRANSACTION_SPEND) {
            title = "Spend";
        } else if (selectedIndex == TRANSACTION_TRANSFER) {
            title = "Transfer";
        } else {
            title = "Edit Transaction";
        }

        TransactionForm transactionForm = new TransactionForm(title, transaction) {
            public void formSubmitted(int responseType, Transaction transaction) {
                if (responseType == RESPONSE_TYPE_OK) {
                    if (selectedIndex < 0) {
                        if (selectedIndex == TRANSACTION_SPEND) {
                            transaction.amount = -transaction.amount;
                        }
                        addTransaction(transaction);
                    } else {
                        updateTransaction(selectedIndex, transaction);
                    }
                }
                Accountant.setCurrent(TransactionWindow.this);
            }
        };
        Accountant.setCurrent(transactionForm);
    }

    private void refreshTransactions() {
        //refreshing transactionList
        deleteAll();
        transactions = Database.getTransactions(account);
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = (Transaction) transactions.elementAt(i);
            String detail = getDetail(transaction);
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

    private String getDetail(Transaction transaction) {
        StringBuffer sb = new StringBuffer();
        sb.append(Util.getDate(transaction.date)).append(Util.NEW_LINE);

        if (transaction.transfer) {
            if (transaction.primaryAccountID == account.id) {
                sb.append("Transfered to ");
                sb.append(Database.getAccount(transaction.secondaryAccountID).name).append(Util.NEW_LINE);
            } else {
                sb.append("Deposited from ");
                sb.append(Database.getAccount(transaction.primaryAccountID).name).append(Util.NEW_LINE);
            }
            sb.append(transaction.description).append(Util.NEW_LINE);
            if (transaction.primaryAccountID == account.id) {
                sb.append("-").append(transaction.amount);
            } else {
                sb.append("").append(transaction.amount);
            }
        } else {
            sb.append(Database.getCategory(transaction.categoryID).name).append(" : ").append(Util.NEW_LINE);
            sb.append(transaction.description).append(Util.NEW_LINE);
            sb.append(transaction.amount);
        }

        return sb.toString();
    }
}
