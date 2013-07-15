/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihhira.projects.j2meaccountant;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Imran
 */
public class Util {

    public static final String NEW_LINE = "\r\f";

    public static String getDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        StringBuffer sb = new StringBuffer();
        sb.append(c.get(Calendar.DATE)).append(" ");
        sb.append(getMonth(c.get(Calendar.MONTH))).append(", ");
        sb.append(c.get(Calendar.YEAR));
        return sb.toString();
    }

    public static String getMonth(int monthIndex) {
        String[] months = new String[]{
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        };
        if (monthIndex < months.length) {
            return months[monthIndex];
        } else {
            return " Month ";
        }
    }
}
