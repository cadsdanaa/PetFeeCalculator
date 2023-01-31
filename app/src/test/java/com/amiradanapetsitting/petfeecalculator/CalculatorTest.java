package com.amiradanapetsitting.petfeecalculator;

import androidx.core.util.Pair;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void shouldGetHourDifferenceBetweenCalendarDates() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 5, 5, 5, 30);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 5, 9, 11, 30);
        Calculator calculator = new Calculator();

        long actualDifference = calculator.getDifferenceInHours(startDate, endDate);

        assertEquals(102, actualDifference);
    }

    @Test
    public void shouldGetHourDifferenceBetweenCalendarDatesRounded() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 5, 5, 5, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 5, 9, 11, 30);
        Calculator calculator = new Calculator();

        long actualDifference = calculator.getDifferenceInHours(startDate, endDate);

        assertEquals(103, actualDifference);
    }

    @Test
    public void shouldGetHourDifferenceBetweenCalendarDatesRoundedUpAlways() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 5, 5, 5, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 5, 5, 11, 1);
        Calculator calculator = new Calculator();

        long actualDifference = calculator.getDifferenceInHours(startDate, endDate);

        assertEquals(7, actualDifference);
    }

    @Test
    public void shouldGetTotal10To24HourPeriods() {
        int totalHours = 55;
        Calculator calculator = new Calculator();

        Pair<Integer, Integer> periodResult = calculator.get10To24HourPeriods(totalHours);

        assertEquals(2, (int) periodResult.first);
        assertEquals(7, (int) periodResult.second);
    }

    @Test
    public void shouldGetTotalRemaining5To10HourPeriods() {
        int totalHours = 7;
        Calculator calculator = new Calculator();

        Pair<Integer, Integer> periodResult = calculator.getRemaining5To10HourPeriods(totalHours);

        assertEquals(1, (int) periodResult.first);
        assertEquals(0, (int) periodResult.second);
    }

    @Test
    public void shouldGetTotalRemaining0To5HourPeriods() {
        int totalHours = 4;
        Calculator calculator = new Calculator();

        Pair<Integer, Integer> periodResult = calculator.getRemaining0To5HourPeriods(totalHours);

        assertEquals(1, (int) periodResult.first);
        assertEquals(0, (int) periodResult.second);
    }

    @Test
    public void shouldGetTotalRemainingHourPeriodsWithLowerBoundNotInclusiveForFiveToTen() {
        int totalHours = 29;
        Calculator calculator = new Calculator();

        Pair<Integer, Integer> twentyFourPeriodResult = calculator.get10To24HourPeriods(totalHours);
        Pair<Integer, Integer> tenPeriodResult = calculator.getRemaining5To10HourPeriods(twentyFourPeriodResult.second);
        Pair<Integer, Integer> fivePeriodResult = calculator.getRemaining0To5HourPeriods(tenPeriodResult.second);

        assertEquals(1, (int) twentyFourPeriodResult.first);
        assertEquals(0, (int) tenPeriodResult.first);
        assertEquals(1, (int) fivePeriodResult.first);
    }

    @Test
    public void shouldGetTotalRemainingHourPeriodsWithLowerBoundNotInclusiveForTenToTwentyFour() {
        int totalHours = 34;
        Calculator calculator = new Calculator();

        Pair<Integer, Integer> twentyFourPeriodResult = calculator.get10To24HourPeriods(totalHours);
        Pair<Integer, Integer> tenPeriodResult = calculator.getRemaining5To10HourPeriods(twentyFourPeriodResult.second);
        Pair<Integer, Integer> fivePeriodResult = calculator.getRemaining0To5HourPeriods(tenPeriodResult.second);

        assertEquals(1, (int) twentyFourPeriodResult.first);
        assertEquals(1, (int) tenPeriodResult.first);
        assertEquals(0, (int) fivePeriodResult.first);
    }

    @Test
    public void shouldCalculateTotalWithNoExtraPetsAndNoTransports() {
        Calculator calculator = new Calculator();

        int actualTotal = calculator.calculateTotal(3, 1, 1, 0, 0);

        assertEquals(170, actualTotal);
    }

    @Test
    public void shouldCalculateTotalWithExtraPetsAndNoTransports() {
        Calculator calculator = new Calculator();

        int actualTotal = calculator.calculateTotal(2, 1, 1, 8, 0);

        assertEquals(290, actualTotal);
    }

    @Test
    public void shouldCalculateTotalWithNoExtraPetsAndTransports() {
        Calculator calculator = new Calculator();

        int actualTotal = calculator.calculateTotal(1, 0, 0, 0, 3);

        assertEquals(55, actualTotal);
    }

    @Test
    public void shouldCalculateTotalWithExtraPetsAndTransports() {
        Calculator calculator = new Calculator();

        int actualTotal = calculator.calculateTotal(1, 1, 0, 3, 3);

        assertEquals(115, actualTotal);
    }

    @Test
    public void shouldGetCalculationExplanationText() {
        Calculator calculator = new Calculator();

        String actualExplanation = calculator.getCalculationExplanation(1, 1, 1, 1, 1);

        assertEquals("" +
                "Base Price: $90\n   0-5 hours: $20 x 1 = $20\n   5-10 hours: $30 x 1 = $30\n   10-24 hours: $40 x 1 = $40\n" +
                "\n" +
                "Multi Pet Fee: $15\n   1 extra pet(s) @ $5 per day for 3 days\n" +
                "Transport Fee: $5 for 1 transport(s)", actualExplanation);
    }
}