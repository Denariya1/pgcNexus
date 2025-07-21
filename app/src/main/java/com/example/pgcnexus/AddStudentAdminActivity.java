package com.example.pgcnexus;

import android.content.Intent; // Required for Intent
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView; // Required for ImageView
import android.widget.Spinner;
import android.widget.TextView; // Required for TextView (for headerTitle)
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentAdminActivity extends AppCompatActivity {

    private Spinner spinnerProgram;
    private Button btnAddStudent;
    private ImageView backArrow; // Declare ImageView for the back arrow
    private TextView headerTitle; // Declare TextView for the header title

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_admin);

        // Initialize Views
        spinnerProgram = findViewById(R.id.spinner_program);
        btnAddStudent = findViewById(R.id.btn_submit_marks);
        backArrow = findViewById(R.id.backArrow); // Initialize the back arrow ImageView
        headerTitle = findViewById(R.id.headerTitle); // Initialize the header title TextView

        // Set the text for the header title
        headerTitle.setText("Add New Student");

        // --- Back Arrow Functionality ---
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the ManageStudentActivity or your desired previous screen.
                // You might want to go back to AdminHomeActivity or a Student Management dashboard.
                Intent intent = new Intent(AddStudentAdminActivity.this, ManageStudentActivity.class); // <-- CHANGE THIS to your desired destination activity
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // Sample Programs
        String[] programs = {"BSIT", "BSCS", "BBA", "BSSE"};
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgram.setAdapter(programAdapter);

        // Set click listener for the "Add Student" button
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedProgram = spinnerProgram.getSelectedItem().toString();

                Toast.makeText(AddStudentAdminActivity.this, "Ready to add student for program: " + selectedProgram, Toast.LENGTH_LONG).show();
                // In a real app, you would typically collect other student details (name, ID, etc.)
                // and then perform a database operation or navigate to another screen.
            }
        });
    }
}