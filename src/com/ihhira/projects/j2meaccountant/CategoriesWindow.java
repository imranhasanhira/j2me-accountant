/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import com.ihhira.projects.j2meaccountant.model.Category;
import com.ihhira.projects.j2meaccountant.model.Database;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import java.util.Vector;

/**
 *
 * @author Imran
 */
public class CategoriesWindow extends Form implements ActionListener {

    Vector categories;
    List categoriesList;
    private final Command backCommand;
    private final Command deleteCommand;
    private final Command editCommand;
    private final Command addCommand;

    public CategoriesWindow(String title) {
        super(title);
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        categoriesList = new List();
        setPreferredW(240);
        addComponent(categoriesList);

        backCommand = new Command("Back");
        deleteCommand = new Command("Delete Category");
        editCommand = new Command("Edit Category");
        addCommand = new Command("Add Category");

        addCommand(backCommand);
        addCommand(addCommand);
        this.addCommandListener(this);

        refreshCategories();
    }

    private void refreshCategories() {
        categories = Database.getCategories();
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < categories.size(); i++) {
            listModel.addItem(((Category) categories.elementAt(i)).name);
        }
        categoriesList.setModel(listModel);
    }

    private void showCategoriesForm(final Category category) {
        String title = category == null ? "New Category" : "Edit category";
        String text = category == null ? "" : category.name;

        InputForm categoryInputForm = new InputForm(this, title, "Name", text) {
            protected void formSubmitted(int response, String text) {
                if (response == RESPONSE_TYPE_OK && text.length() > 0) {
                    if (category == null) {
                        Category category = new Category(text);
                        Database.addCategory(category);
                        refreshCategories();
                    } else {
                        category.name = text;
                        Database.updateCategory(category);
                        refreshCategories();
                    }
                }
            }
        };
        categoryInputForm.show();
    }

    public void actionPerformed(ActionEvent ae) {
        int selectedIndex = categoriesList.getSelectedIndex();
        Command c = ae.getCommand();
        if (c == backCommand) {
            AccountsWindow accountWindow = new AccountsWindow();
            accountWindow.show();
        } else if (c == deleteCommand) {
            Category category = (Category) categories.elementAt(selectedIndex);
            Database.deleteCategory(category);
            refreshCategories();
        } else if (c == editCommand) {
            showCategoriesForm((Category) categories.elementAt(selectedIndex));
        } else if (c == addCommand) {
            showCategoriesForm(null);
        }
    }
}
