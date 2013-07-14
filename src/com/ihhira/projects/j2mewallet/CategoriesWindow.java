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
public class CategoriesWindow extends List implements CommandListener {

    Vector categories;
    private final Command backCommand;
    private final Command deleteCommand;
    private final Command editCommand;
    private final Command addCommand;

    public CategoriesWindow(String title) {
        super(title, List.IMPLICIT);

        backCommand = new Command("Back", Command.BACK, 0);
        deleteCommand = new Command("Delete", Command.ITEM, 1);
        editCommand = new Command("Edit", Command.ITEM, 2);
        addCommand = new Command("Add", Command.ITEM, 3);

        addCommand(backCommand);
        addCommand(addCommand);
        setCommandListener(this);


        refreshCategories();
        refreshCommands();
    }

    public void commandAction(Command c, Displayable d) {
        int selectedIndex = getSelectedIndex();
        if (c == backCommand) {
            AccountsWindow accountWindow = new AccountsWindow();
            Wallet.setCurrent(accountWindow);
        } else if (c == deleteCommand) {
            deleteCategory(selectedIndex);
        } else if (c == editCommand) {
            showCategoriesForm(selectedIndex);
        } else if (c == addCommand) {
            showCategoriesForm(-1);
        }
    }

    private void refreshCommands() {
        //refreshing commands
        if (categories.size() > 0) {
            addCommand(deleteCommand);
            addCommand(editCommand);
        } else {
            removeCommand(deleteCommand);
            removeCommand(editCommand);
        }
    }

    private void refreshCategories() {
        deleteAll();
        categories = Database.getCategories();
        if (categories.size() <= 0) {
            Database.addCategory(new Category("Misc"));
            categories = Database.getCategories();
        }
        for (int i = 0; i < categories.size(); i++) {
            append(((Category) categories.elementAt(i)).name, null);
        }
    }

    private void deleteCategory(int selectedIndex) {
        Category category = (Category) categories.elementAt(selectedIndex);
        Database.deleteCategory(category);
        refreshCategories();
    }

    private void showCategoriesForm(final int selectedIndex) {
        Category cat = null;
        if (selectedIndex == -1) {
            cat = new Category();
        } else {
            cat = ((Category) categories.elementAt(selectedIndex));
        }

        DialogBox dialogBox = new DialogBox(this, "New Category", "Category name", cat.name, 20) {
            protected void actionPerformed(int response, String text) {
                if (response == RESPONSE_TYPE_OK && text.length() > 0) {
                    if (selectedIndex == -1) {
                        addCategory(text);
                    } else {
                        updateCategory(selectedIndex, text);
                    }
                }
            }
        };
        dialogBox.show();
    }

    private void addCategory(String name) {
        Category category = new Category(name);
        Database.addCategory(category);
        refreshCategories();
    }

    private void updateCategory(int selectedIndex, String text) {
        Category category = (Category) categories.elementAt(selectedIndex);
        category.name = text;
        Database.updateCategory(category);
        refreshCategories();
    }
}
