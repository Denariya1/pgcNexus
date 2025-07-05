package com.example.pgcnexus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MarksheetAdminActivity extends AppCompatActivity {

    private Spinner spinnerProgram, spinnerStudent;
    private EditText etQuiz, etMid, etFinal, etGpa, etCgpa;
    private Button btnSubmitMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marksheet_admin); // Ensure your XML is named activity_marksheet.xml

        // Initialize Views
        spinnerProgram = findViewById(R.id.spinner_program);
        spinnerStudent = findViewById(R.id.spinner_student);
        etQuiz = findViewById(R.id.et_quiz);
        etMid = findViewById(R.id.et_mid);
        etFinal = findViewById(R.id.et_final);
        etGpa = findViewById(R.id.et_gpa);
        etCgpa = findViewById(R.id.et_cgpa);
        btnSubmitMarks = findViewById(R.id.btn_submit_marks);

        // Sample Programs
        String[] programs = {"BSIT", "BSCS", "BBA", "BSSE"};
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgram.setAdapter(programAdapter);

        // Sample Students (replace with real data later)
        String[] students = {"Ali Raza", "Ayesha Khan", "Bilal Ahmed", "Fatima Noor"};
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, students);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudent.setAdapter(studentAdapter);

        // Submit Button Logic
        btnSubmitMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String program = spinnerProgram.getSelectedItem().toString();
                String student = spinnerStudent.getSelectedItem().toString();
                String quiz = etQuiz.getText().toString().trim();
                String mid = etMid.getText().toString().trim();
                String finalMarks = etFinal.getText().toString().trim();
                String gpa = etGpa.getText().toString().trim();
                String cgpa = etCgpa.getText().toString().trim();

                if (quiz.isEmpty() || mid.isEmpty() || finalMarks.isEmpty() || gpa.isEmpty() || cgpa.isEmpty()) {
                    Toast.makeText(MarksheetAdminActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show summary
                String summary = "Program: " + program +
                        "\nStudent: " + student +
                        "\nQuiz: " + quiz +
                        "\nMid: " + mid +
                        "\nFinal: " + finalMarks +
                        "\nGPA: " + gpa +
                        "\nCGPA: " + cgpa;

                Toast.makeText(MarksheetAdminActivity.this, "Marks Submitted:\n" + summary, Toast.LENGTH_LONG).show();

                // Optional: Clear fields
                etQuiz.setText("");
                etMid.setText("");
                etFinal.setText("");
                etGpa.setText("");
                etCgpa.setText("");
            }
        });
    }
}

