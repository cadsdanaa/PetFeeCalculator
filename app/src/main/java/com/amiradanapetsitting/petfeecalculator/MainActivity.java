package com.amiradanapetsitting.petfeecalculator;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();
    private int numberOfExtraAnimals = 0;
    private int transportNumber = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.btn_startTimePicker);
        DatePickerDialog.OnDateSetListener startDateListener = (view, year, month, day) -> {
            new TimePickerDialog(MainActivity.this, (timePicker, hour, minute) -> {
                startCalendar.set(year, month, day, hour, minute);
                ((TextView) findViewById(R.id.text_startDate)).setText("   " + dateFormat.format(startCalendar.getTime()));
            }, startCalendar.get(Calendar.HOUR), startCalendar.get(Calendar.MINUTE), false).show();
        };
        startButton.setOnClickListener(view ->
                new DatePickerDialog(
                        MainActivity.this,
                        startDateListener,
                        startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH))
                        .show());

        Button endButton = findViewById(R.id.btn_endTimePicker);
        DatePickerDialog.OnDateSetListener endDateListener = (view, year, month, day) -> {
            new TimePickerDialog(MainActivity.this, (timePicker, hour, minute) -> {
                endCalendar.set(year, month, day, hour, minute);
                ((TextView) findViewById(R.id.text_endDate)).setText("   " + dateFormat.format(endCalendar.getTime()));
            }, endCalendar.get(Calendar.HOUR), endCalendar.get(Calendar.MINUTE), false).show();
        };
        endButton.setOnClickListener(view ->
                new DatePickerDialog(
                        MainActivity.this,
                        endDateListener,
                        endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH))
                        .show());

        NumberPicker extraAnimals = findViewById(R.id.input_extraPets);
        extraAnimals.setOnValueChangedListener((picker, oldVal, newVal) -> {
            numberOfExtraAnimals = newVal;
        });

        NumberPicker transports = findViewById(R.id.input_transports);
        transports.setOnValueChangedListener((picker, oldVal, newVal) -> {
            transportNumber = newVal;
        });

        Button calculateButton = findViewById(R.id.btn_calculate);
        calculateButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            Calculator calculator = new Calculator();
            int totalHours = calculator.getDifferenceInHours(startCalendar, endCalendar);
            Pair<Integer, Integer> tenToTwentyFourHourPeriods = calculator.get10To24HourPeriods(totalHours);
            Pair<Integer, Integer> fiveToTenHourPeriods = calculator.getRemaining5To10HourPeriods(tenToTwentyFourHourPeriods.second);
            Pair<Integer, Integer> oneToFiveHourPeriods = calculator.getRemaining0To5HourPeriods(fiveToTenHourPeriods.second);
            builder.setTitle("Total Bill: $" + calculator.calculateTotal(
                    tenToTwentyFourHourPeriods.first,
                    fiveToTenHourPeriods.first,
                    oneToFiveHourPeriods.first,
                    numberOfExtraAnimals,
                    transportNumber));
            builder.setMessage(calculator.getCalculationExplanation(
                    tenToTwentyFourHourPeriods.first,
                    fiveToTenHourPeriods.first,
                    oneToFiveHourPeriods.first,
                    numberOfExtraAnimals,
                    transportNumber
            ));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        });

    }
}