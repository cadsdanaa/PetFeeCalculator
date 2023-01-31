package com.amiradanapetsitting.petfeecalculator;

import androidx.core.util.Pair;

import java.util.Calendar;
import java.util.Date;

public class Calculator {

    private static final int TEN_TO_TWENTYFOUR_HOUR_RATE = 40;
    private static final int FIVE_TO_TEN_HOUR_RATE = 30;
    private static final int ONE_TO_FIVE_HOUR_RATE = 20;
    private static final int TRANSPORT_RATE = 5;
    private static final int EXTRA_PET_RATE = 5;


    public int getDifferenceInHours(Calendar startDate, Calendar endDate) {
        Date start = startDate.getTime();
        Date end = endDate.getTime();

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) Math.ceil((double) (end.getTime() - start.getTime()) / MILLI_TO_HOUR);
    }

    public Pair<Integer, Integer> get10To24HourPeriods(int totalHours) {
        int remainingHours = totalHours;
        int periods = 0;
        for(int i = 24; i > 10; i--) {
            periods += remainingHours/i;
            remainingHours -= i* (remainingHours/i);
        }
        return Pair.create(periods, remainingHours);
    }

    public Pair<Integer, Integer> getRemaining5To10HourPeriods(int remainingHours) {
        int periods = 0;
        for(int i = 10; i > 5; i--) {
            periods += remainingHours/i;
            remainingHours -= i* (remainingHours/i);
        }
        return Pair.create(periods, remainingHours);
    }

    public Pair<Integer, Integer> getRemaining0To5HourPeriods(int remainingHours) {
        int periods = 0;
        for(int i = 5; i >= 1; i--) {
            periods += remainingHours/i;
            remainingHours -= i* (remainingHours/i);
        }
        return Pair.create(periods, remainingHours);
    }

    public int calculateTotal(int tenToTwentyFourHourPeriods, int fiveToTenHourPeriods, int oneToFiveHourPeriods, int extraPets, int transports) {
        return TEN_TO_TWENTYFOUR_HOUR_RATE * tenToTwentyFourHourPeriods +
                FIVE_TO_TEN_HOUR_RATE * fiveToTenHourPeriods +
                ONE_TO_FIVE_HOUR_RATE * oneToFiveHourPeriods +
                EXTRA_PET_RATE * extraPets * (tenToTwentyFourHourPeriods + fiveToTenHourPeriods + oneToFiveHourPeriods) +
                TRANSPORT_RATE * transports;
    }

    public String getCalculationExplanation(int tenToTwentyFourHourPeriods, int fiveToTenHourPeriods, int oneToFiveHourPeriods, int extraPets, int transports) {
        return "Base Price: $" + (TEN_TO_TWENTYFOUR_HOUR_RATE * tenToTwentyFourHourPeriods +
                FIVE_TO_TEN_HOUR_RATE * fiveToTenHourPeriods +
                ONE_TO_FIVE_HOUR_RATE * oneToFiveHourPeriods) + "\n" +
                "   0-5 hours: $" + ONE_TO_FIVE_HOUR_RATE + " x " + oneToFiveHourPeriods + " = $" + ONE_TO_FIVE_HOUR_RATE * oneToFiveHourPeriods + "\n" +
                "   5-10 hours: $" + FIVE_TO_TEN_HOUR_RATE + " x " + fiveToTenHourPeriods + " = $" + FIVE_TO_TEN_HOUR_RATE * fiveToTenHourPeriods + "\n" +
                "   10-24 hours: $" + TEN_TO_TWENTYFOUR_HOUR_RATE + " x " + tenToTwentyFourHourPeriods + " = $" + TEN_TO_TWENTYFOUR_HOUR_RATE * tenToTwentyFourHourPeriods + "\n" +
                "\n" +
                "Multi Pet Fee: $" + EXTRA_PET_RATE * extraPets * (tenToTwentyFourHourPeriods + fiveToTenHourPeriods + oneToFiveHourPeriods) + "\n" +
                "   " + extraPets + " extra pet(s) @ $5 per day for " + (tenToTwentyFourHourPeriods + fiveToTenHourPeriods + oneToFiveHourPeriods) + " days\n" +
                "Transport Fee: $" + TRANSPORT_RATE * transports + " for " + transports + " transport(s)"
                ;
    }
}
