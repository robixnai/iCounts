package com.robson.icounts.development;

import java.util.Random;

/**
 * Created by robson on 20/03/16.
 */
public abstract class MockTestDevelopment {


    private static double valueMin = 1.00;
    private static double valueCredit = 2457.78;
    private static double valueDebit = 1968.93;
    private static Random rand = new Random();
    private static double valueRents = valueMin + (valueCredit - valueMin) * rand.nextDouble();
    private static double valueSpending = valueMin + (valueDebit - valueMin) * rand.nextDouble();
    private static double valueRecurrence = 1748.35;
    private static double valueRecurrenceMoment = valueMin + (valueRecurrence - valueMin) * rand.nextDouble();


    public static double getValueCredit() {
        return valueCredit;
    }

    public static double getValueDebit() {
        return valueDebit;
    }

    public static double getValueRents() {
        return valueRents;
    }

    public static double getValueSpending() {
        return valueSpending;
    }

    public static double getValueRecurrence() {
        return valueRecurrence;
    }

    public static double getValueRecurrenceMoment() {
        return valueRecurrenceMoment;
    }

    public static int mockProgressBarRents() {
        int rents = (int) ((valueRents / valueCredit) * 100);
        return rents;
    }

    public static int mockProgressBarSpending() {
        int spending = (int) ((valueSpending / valueDebit) * 100);
        return spending;
    }

    public static int mockProgressBarRecurrence() {
        return (int) ((valueRecurrenceMoment / valueRecurrence) * 100);
    }

    public static String[] mockPieChart() {
        return new String[] {
                "Despesas fixa",
                "Despesas variaveis",
                "Despesas extras",
                "Despesas adicionais"
        };
    }

}
