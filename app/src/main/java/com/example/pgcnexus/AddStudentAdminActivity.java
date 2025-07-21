package com.example.pgcnexus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentAdminActivity extends AppCompatActivity {

    private Spinner spinnerProgram; // Only the program spinner
    private Button btnAddStudent; // Renamed for clarity to match "Add Student" button text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_admin); // Your XML file name

        // Initialize Views
        spinnerProgram = findViewById(R.id.spinner_program);
        btnAddStudent = findViewById(R.id.btn_submit_marks); // Using the existing ID for the "Add Student" button

        // Sample Programs (as per your original code)
        String[] programs = {"BSIT", "BSCS", "BBA", "BSSE"};
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgram.setAdapter(programAdapter);

        // Set click listener for the "Add Student" button
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedProgram = spinnerProgram.getSelectedItem().toString();

                // For now, we'll just show a Toast confirming the program selected.
                // In a real application, you would typically open a new activity
                // or a dialog here to gather more student details (name, ID, etc.)
                // before "adding" them to a database.
                Toast.makeText(AddStudentAdminActivity.this, "Ready to add student for program: " + selectedProgram, Toast.LENGTH_LONG).show();
            }
        });
    }
}