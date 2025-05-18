package com.example.pgcnexus;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceActivity extends AppCompatActivity {

    Spinner spinnerClassSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        spinnerClassSelector = findViewById(R.id.spinnerClassSelector);

        // Class list with "Select Class" as hint
        String[] classList = {"Select Class", "Class 1", "Class 2", "Class 3", "Class 4"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classList) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Hint color
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassSelector.setAdapter(adapter);

        spinnerClassSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedClass = classList[position];
                    // TODO: Update student list according to selected class
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

