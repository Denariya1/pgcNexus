package com.example.pgcnexus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AssignSubToteacherAdminActivity extends AppCompatActivity {

    EditText teacherEditText, subjectEditText;
    Spinner classSpinner;
    Button assignButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_sub_toteacher_admin);

        // Initialize views
        teacherEditText = findViewById(R.id.teacherEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        classSpinner = findViewById(R.id.classSpinner);
        assignButton = findViewById(R.id.assignButton);
        backButton = findViewById(R.id.backButton);

        // Set up Spinner
        String[] classes = {"BSIT21A", "BSIT20B", "BSCS21", "BSCS23A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        classSpinner.setAdapter(adapter);

        // Back button action
        backButton.setOnClickListener(v -> finish());

        // Assign button click
        assignButton.setOnClickListener(v -> {
            String teacherName = teacherEditText.getText().toString().trim();
            String subjectName = subjectEditText.getText().toString().trim();
            String selectedClass = classSpinner.getSelectedItem().toString();

            if (teacherName.isEmpty() || subjectName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // You can add your database logic here to save the assignment
                Toast.makeText(this,
                        "Assigned " + subjectName + " to " + teacherName + " for class " + selectedClass,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
