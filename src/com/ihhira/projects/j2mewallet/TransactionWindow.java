/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2mewallet;

import javax.microedition.lcdui.List;

/**
 *
 * @author Imran
 */
public class TransactionWindow extends List {

    Account account;

    TransactionWindow(Account account) {
        super(account.toString(), IMPLICIT);
        this.account = account;
    }
}
