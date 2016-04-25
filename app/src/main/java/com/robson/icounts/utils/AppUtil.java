package com.robson.icounts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;

/**
 * Created by robson on 09/02/16.
 */
public class AppUtil {

    private static final String PATTERN_DATE = "dd/MM/yyyy";
    private static final String PATTERN_TIME = "HH:mm:ss";
    private static final String PATTERN_DATETIME = "dd/MM/yyyy HH:mm:ss";
    private static final String PATTERN_NUMBER = "#.00";

    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
    public static final Integer FILTER_MENU_ITEM_OPEN = 1;
    public static final int ARCHIVED = 1;
    public static final int OS_UNARCHIVING = 2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final Integer FAILED = 0;
    public static final Integer SUCCESS = 1;
    public static final Integer NOT_NETWORK = 2;

    private static Integer FILTER_MENU_ACTIVE = FILTER_MENU_ITEM_OPEN;

    public static Context CONTEXT;
    public static String DEVICE_ID;
    public static String URI_SERVER;

    private AppUtil() {
        super();
    }

    public static <T> T get(Object element) {
        return (T) element;
    }

    public static String formatDate(Date date) {
        return format(date, AppUtil.PATTERN_DATE);
    }

    public static String formatDateTime(Date date) {
        return format(date, AppUtil.PATTERN_DATETIME);
    }

    public static String formatTime(Date date) {
        return format(date, AppUtil.PATTERN_TIME);
    }

    public static String formatDecimal(Number number) {
        final DecimalFormat decimalFormat = AppUtil.get(NumberFormat.getNumberInstance(Locale.US));
        decimalFormat.applyPattern(AppUtil.PATTERN_NUMBER);
        return decimalFormat.format(number);
    }

    private static String format(Date date, String pattern) {
        final DateFormat dateTimeFormat = new SimpleDateFormat(pattern, AppUtil.LOCALE_PT_BR);
        return dateTimeFormat.format(date);
    }

    public static void changeMenuFilterOption(Integer option) {
        AppUtil.FILTER_MENU_ACTIVE = option;
    }

    public static int getMenuActive() {
        return AppUtil.FILTER_MENU_ACTIVE;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String implodeArray(String[] inputArray, String glueString) {
        String output = "";
        if (inputArray.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(inputArray[0]);
            for (int i=1; i<inputArray.length; i++) {
                sb.append(glueString);
                sb.append(inputArray[i]);
            }
            output = sb.toString();
        }
        return output;
    }

}
