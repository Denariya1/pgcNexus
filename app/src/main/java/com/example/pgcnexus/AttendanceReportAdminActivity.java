package com.example.pgcnexus;

import android.content.Intent; // Import for Intent
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView; // Import for ImageView
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;

public class AttendanceReportAdminActivity extends AppCompatActivity {

    private Spinner programSpinner;
    private LinearLayout cardsContainer;
    private ImageView backArrow; // Declare ImageView for back arrow
    private List<Student> allStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_report_admin);

        // Initialize views
        programSpinner = findViewById(R.id.programSpinner);
        cardsContainer = findViewById(R.id.cardsContainer);
        TextView headerTitle = findViewById(R.id.headerTitle);
        headerTitle.setText("Attendance Report");
        backArrow = findViewById(R.id.backArrow); // Initialize back arrow ImageView

        // --- Back Arrow Functionality ---
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to AdminHomeActivity (or whichever activity you intend)
                Intent intent = new Intent(AttendanceReportAdminActivity.this, ManageStudentActivity.class); // <-- CHANGE THIS to your desired destination activity
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // Setup student data
        setupStudentData();

        // Setup spinner
        setupProgramSpinner();

        // Show initial data (all students)
        filterStudents("All Programs");
    }

    private void setupStudentData() {
        // BSIT Students
        allStudents.add(new Student("Ali Khan", "LEFBSIT026", "BSIT", 2, 5, 3));
        allStudents.add(new Student("Sara Ahmed", "LEFBSIT027", "BSIT", 1, 2, 4));

        // BSCS Students
        allStudents.add(new Student("Usman Ali", "LEFBSCS101", "BSCS", 0, 1, 6));
        allStudents.add(new Student("Fatima Khan", "LEFBSCS102", "BSCS", 3, 0, 4));

        // BSSE Students
        allStudents.add(new Student("Ahmed Raza", "LEFBSSE201", "BSSE", 1, 3, 3));

        // BBA Students
        allStudents.add(new Student("Zainab Malik", "LEFBBA301", "BBA", 2, 1, 4));
        allStudents.add(new Student("Bilal Akhtar", "LEFBBA302", "BBA", 0, 0, 7));
    }

    private void setupProgramSpinner() {
        // Create program list
        String[] programs = {"All Programs", "BSIT", "BSCS", "BSSE", "BBA"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                programs
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programSpinner.setAdapter(adapter);

        // Spinner selection listener
        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProgram = parent.getItemAtPosition(position).toString();
                filterStudents(selectedProgram);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterStudents("All Programs");
            }
        });
    }

    private void filterStudents(String program) {
        cardsContainer.removeAllViews();

        List<Student> filteredStudents = new ArrayList<>();

        if (program.equals("All Programs")) {
            filteredStudents.addAll(allStudents);
        } else {
            for (Student student : allStudents) {
                if (student.program.equals(program)) {
                    filteredStudents.add(student);
                }
            }
        }

        // Display filtered students
        for (Student student : filteredStudents) {
            addStudentCard(student);
        }
    }

    private void addStudentCard(Student student) {
        CardView card = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16)); // Use dpToPx for margins
        card.setLayoutParams(cardParams);
        card.setCardElevation(dpToPx(8)); // Use dpToPx for elevation
        card.setRadius(dpToPx(16)); // Use dpToPx for radius
        card.setContentPadding(dpToPx(24), dpToPx(24), dpToPx(24), dpToPx(24)); // Use dpToPx for padding
        card.setCardBackgroundColor(0xFFFFFFFF); // White background for card

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);

        // Add student details
        addTextView(cardContent, student.name, 18, true);
        addTextView(cardContent, "Roll No: " + student.rollNo, 14, false);
        addTextView(cardContent, "Program: " + student.program, 14, false);
        addTextView(cardContent, "Leaves: " + student.leaves, 14, false);
        addTextView(cardContent, "Absents: " + student.absents, 14, false);
        addTextView(cardContent, "Presents: " + student.presents, 14, false);

        card.addView(cardContent);
        cardsContainer.addView(card);
    }

    private void addTextView(LinearLayout layout, String text, float size, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(size);
        textView.setTypeface(null, bold ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
        textView.setPadding(0, dpToPx(4), 0, dpToPx(4)); // Use dpToPx for padding
        textView.setTextColor(0xFF000000); // Black text color
        layout.addView(textView);
    }

    /**
     * Helper method to convert dp to pixels for programmatic view creation.
     * @param dp The dp value to convert.
     * @return The equivalent pixel value.
     */
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    class Student {
        String name;
        String rollNo;
        String program;
        int leaves;
        int absents;
        int presents;

        public Student(String name, String rollNo, String program, int leaves, int absents, int presents) {
            this.name = name;
            this.rollNo = rollNo;
            this.program = program;
            this.leaves = leaves;
            this.absents = absents;
            this.presents = presents;
        }
    }
}