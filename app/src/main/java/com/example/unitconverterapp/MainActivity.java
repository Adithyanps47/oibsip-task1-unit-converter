package com.example.unitconverterapp;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner unitSpinner;
    Button convertBtn;
    TextView resultText;

    String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        unitSpinner = findViewById(R.id.unitSpinner);
        convertBtn = findViewById(R.id.convertBtn);
        resultText = findViewById(R.id.resultText);

        options = getResources().getStringArray(R.array.conversion_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doConversion();
            }
        });
    }

    private void doConversion() {
        String inputStr = inputValue.getText().toString().trim();
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double input;
        try {
            input = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            return;
        }

        int idx = unitSpinner.getSelectedItemPosition();
        double result = 0;
        String resultLabel = "";

        if (idx == 0) { // cm to m
            result = input / 100.0;
            resultLabel = input + " cm = " + format(result) + " m";

        } else if (idx == 1) { // m to cm
            result = input * 100.0;
            resultLabel = input + " m = " + format(result) + " cm";

        } else if (idx == 2) { // grams to kg
            result = input / 1000.0;
            resultLabel = input + " g = " + format(result) + " kg";

        } else if (idx == 3) { // kg to grams
            result = input * 1000.0;
            resultLabel = input + " kg = " + format(result) + " g";

        } else if (idx == 4) { // km to miles
            result = input * 0.621371;
            resultLabel = input + " km = " + format(result) + " miles";

        } else if (idx == 5) { // miles to km
            result = input / 0.621371;
            resultLabel = input + " miles = " + format(result) + " km";

        } else if (idx == 6) { // °C to °F
            result = (input * 9.0 / 5.0) + 32.0;
            resultLabel = input + " °C = " + format(result) + " °F";

        } else if (idx == 7) { // °F to °C
            result = (input - 32.0) * 5.0 / 9.0;
            resultLabel = input + " °F = " + format(result) + " °C";

        } else {
            resultLabel = "Unsupported conversion";
        }

        resultText.setText(resultLabel);
    }

    private String format(double d) {
        if (Math.abs(d - Math.round(d)) < 1e-9) {
            return String.valueOf((long)Math.round(d));
        } else {
            return String.format("%.6f", d).replaceAll("\\.?0+$", "");
        }
    }
}
