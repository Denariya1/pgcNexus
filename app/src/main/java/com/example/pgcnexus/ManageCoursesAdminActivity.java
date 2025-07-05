package com.example.pgcnexus;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCoursesAdminActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Spinner spinnerPrograms;
    private Map<String, List<Course>> programCoursesMap;
    private int courseIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_courses_admin);

        tableLayout = findViewById(R.id.table_layout);
        spinnerPrograms = findViewById(R.id.spinner_programs);
        Button btnAddCourse = findViewById(R.id.btn_add_course);

        // Initialize the program-course mapping with realistic courses
        initializeProgramCourses();

        // Set up the spinner
        setupSpinner();

        btnAddCourse.setOnClickListener(view -> showAddCourseDialog());
    }

    private void initializeProgramCourses() {
        programCoursesMap = new HashMap<>();

        // BSIT21A - Information Technology courses
        List<Course> bsit21aCourses = new ArrayList<>();
        bsit21aCourses.add(new Course("IT101", "Introduction to Programming", "4", "8 Semesters"));
        bsit21aCourses.add(new Course("IT102", "Data Structures", "4", "8 Semesters"));
        bsit21aCourses.add(new Course("IT103", "Database Systems", "4", "8 Semesters"));
        bsit21aCourses.add(new Course("IT104", "Computer Networks", "4", "8 Semesters"));
        bsit21aCourses.add(new Course("IT105", "Web Development", "4", "8 Semesters"));
        programCoursesMap.put("BSIT21A", bsit21aCourses);

        // BSIT21B - Advanced IT courses
        List<Course> bsit21bCourses = new ArrayList<>();
        bsit21bCourses.add(new Course("IT201", "Advanced Programming", "4", "8 Semesters"));
        bsit21bCourses.add(new Course("IT202", "Algorithms Analysis", "4", "8 Semesters"));
        bsit21bCourses.add(new Course("IT203", "Network Security", "4", "8 Semesters"));
        bsit21bCourses.add(new Course("IT204", "Mobile App Development", "4", "8 Semesters"));
        bsit21bCourses.add(new Course("IT205", "Cloud Computing", "4", "8 Semesters"));
        programCoursesMap.put("BSIT21B", bsit21bCourses);

        // BSCS22A - Computer Science courses
        List<Course> bscs22aCourses = new ArrayList<>();
        bscs22aCourses.add(new Course("CS101", "Discrete Mathematics", "4", "8 Semesters"));
        bscs22aCourses.add(new Course("CS102", "Computer Organization", "4", "8 Semesters"));
        bscs22aCourses.add(new Course("CS103", "Operating Systems", "4", "8 Semesters"));
        bscs22aCourses.add(new Course("CS104", "Artificial Intelligence", "4", "8 Semesters"));
        bscs22aCourses.add(new Course("CS105", "Machine Learning", "4", "8 Semesters"));
        programCoursesMap.put("BSCS22A", bscs22aCourses);

        // BSSE23A - Software Engineering courses
        List<Course> bsse23aCourses = new ArrayList<>();
        bsse23aCourses.add(new Course("SE101", "Software Engineering", "4", "8 Semesters"));
        bsse23aCourses.add(new Course("SE102", "Software Design", "4", "8 Semesters"));
        bsse23aCourses.add(new Course("SE103", "Software Testing", "4", "8 Semesters"));
        bsse23aCourses.add(new Course("SE104", "Software Project Management", "4", "8 Semesters"));
        bsse23aCourses.add(new Course("SE105", "DevOps Practices", "4", "8 Semesters"));
        programCoursesMap.put("BSSE23A", bsse23aCourses);

        // BBA21A - Business Administration courses
        List<Course> bba21aCourses = new ArrayList<>();
        bba21aCourses.add(new Course("BA101", "Business Statistics", "4", "8 Semesters"));
        bba21aCourses.add(new Course("BA102", "Financial Accounting", "4", "8 Semesters"));
        bba21aCourses.add(new Course("BA103", "Marketing Management", "4", "8 Semesters"));
        bba21aCourses.add(new Course("BA104", "Business Communication", "4", "8 Semesters"));
        bba21aCourses.add(new Course("BA105", "Organizational Behavior", "4", "8 Semesters"));
        programCoursesMap.put("BBA21A", bba21aCourses);
    }

    private void setupSpinner() {
        // Create program list
        String[] programs = {"BSIT21A", "BSIT21B", "BSCS22A", "BSSE23A", "BBA21A"};

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                programs
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(adapter);

        // Set spinner listener
        spinnerPrograms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProgram = parent.getItemAtPosition(position).toString();
                updateTableForProgram(selectedProgram);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updateTableForProgram(String program) {
        // Clear existing rows (except header)
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }

        // Reset course index for the new program
        courseIndex = 1;

        // Add courses for the selected program
        List<Course> courses = programCoursesMap.get(program);
        if (courses != null) {
            for (Course course : courses) {
                addRowToTable(course.code, course.name, course.subjects, course.totalSem);
            }
        }
    }

    private void showAddCourseDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        EditText etCode = new EditText(this);
        etCode.setHint("Course Code");
        layout.addView(etCode);

        EditText etName = new EditText(this);
        etName.setHint("Course Name");
        layout.addView(etName);

        EditText etSubjects = new EditText(this);
        etSubjects.setHint("Subjects");
        etSubjects.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(etSubjects);

        EditText etTotalSem = new EditText(this);
        etTotalSem.setHint("Total Sem/Year");
        layout.addView(etTotalSem);

        new AlertDialog.Builder(this)
                .setTitle("Add New Course")
                .setView(layout)
                .setPositiveButton("Add", (dialog, which) -> {
                    String code = etCode.getText().toString().trim();
                    String name = etName.getText().toString().trim();
                    String subjects = etSubjects.getText().toString().trim();
                    String totalSem = etTotalSem.getText().toString().trim();

                    if (code.isEmpty() || name.isEmpty() || subjects.isEmpty() || totalSem.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        // Get currently selected program
                        String selectedProgram = spinnerPrograms.getSelectedItem().toString();

                        // Add to the program's course list
                        List<Course> courses = programCoursesMap.get(selectedProgram);
                        if (courses == null) {
                            courses = new ArrayList<>();
                            programCoursesMap.put(selectedProgram, courses);
                        }
                        courses.add(new Course(code, name, subjects, totalSem));

                        // Add to table
                        addRowToTable(code, name, subjects, totalSem);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addRowToTable(String code, String name, String subjects, String totalSem) {
        TableRow row = new TableRow(this);
        row.setBackgroundColor(courseIndex % 2 == 0 ? 0xFFF5F5F5 : 0xFFFFFFFF);

        row.addView(createTextView(String.valueOf(courseIndex), 100));
        row.addView(createTextView(code, 120));
        row.addView(createTextView(name, 200));
        row.addView(createTextView(subjects, 100));
        row.addView(createTextView(totalSem, 120));

        tableLayout.addView(row);
        courseIndex++;
    }

    private TextView createTextView(String text, int widthDp) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setWidth((int) (widthDp * getResources().getDisplayMetrics().density));
        tv.setPadding(4, 16, 4, 16);
        return tv;
    }

    // Helper class to represent a Course
    private static class Course {
        String code;
        String name;
        String subjects;
        String totalSem;

        Course(String code, String name, String subjects, String totalSem) {
            this.code = code;
            this.name = name;
            this.subjects = subjects;
            this.totalSem = totalSem;
        }
    }
}
