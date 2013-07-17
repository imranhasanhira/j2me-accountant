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
    private static final Calendar calendar = Calendar.getInstance();
    private static final String[] months = new String[]{
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

    public static String getDate(Date date) {
        calendar.setTime(date);
        StringBuffer sb = new StringBuffer();
        sb.append(calendar.get(Calendar.DATE)).append(" ");
        sb.append(getMonth(calendar.get(Calendar.MONTH))).append(", ");
        sb.append(calendar.get(Calendar.YEAR));
        return sb.toString();
    }

    public static String getMonth(int monthIndex) {
        if (monthIndex < months.length) {
            return months[monthIndex];
        } else {
            return " Month ";
        }
    }
}
