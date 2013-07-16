/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import com.ihhira.projects.j2meaccountant.model.Database;
import com.ihhira.projects.j2meaccountant.model.Settings;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

/**
 *
 * @author Imran
 */
public class SettingsWindow extends Form implements ActionListener {

    ComboBox themeComboBox;

    public SettingsWindow() {
        super("Settings");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        loadCommands();
        loadComponents();
        loadSettings();

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getCommand().getCommandName().equals("Ok")) {
            saveSettings();
        }
        AccountsWindow accountsWindow = new AccountsWindow();
        accountsWindow.show();
    }

    private void saveSettings() {
        //theme
        if (themeComboBox.getSelectedIndex() >= 0) {
            Database.setSettings(Settings.KEY_THEME_INDEX, new Integer(themeComboBox.getSelectedIndex()));
        }
    }

    private void loadSettings() {
        //theme settings
        int themeIndex = ((Integer) Database.getSettings(Settings.KEY_THEME_INDEX)).intValue();
        themeComboBox.setSelectedIndex(themeIndex);


    }

    private void loadComponents() {
        addComponent(new Label("Theme"));
        themeComboBox = new ComboBox(Settings.THEME_STRINGS);
        addComponent(themeComboBox);
    }

    private void loadCommands() {
        addCommand(new Command("Ok"));
        addCommand(new Command("Cancel"));
        addCommandListener(this);
    }
}
