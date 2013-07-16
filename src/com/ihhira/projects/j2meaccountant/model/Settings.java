/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant.model;

/**
 *
 * @author Imran
 */
public class Settings {

    public static final String KEY_THEME_INDEX = "theme";
    public static final String[] THEME_STRINGS = new String[]{"classic", "featured"};

    public static String getTheme() {
        return THEME_STRINGS[((Integer) Database.getSettings(KEY_THEME_INDEX)).intValue()];
    }
}
