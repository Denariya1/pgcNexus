package com.example.pgcnexus;

import android.app.AlertDialog;
import android.content.Intent; // Required for Intent
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Required for ImageView
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCoursesAdminActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Spinner spinnerPrograms;
    private Spinner spinnerSemesters;
    private ImageView backArrow; // Declare ImageView for back arrow
    private TextView headerTitle; // Declare TextView for header title

    private Map<String, Map<String, List<Course>>> programSemesterCoursesMap;
    private int courseIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_courses_admin);

        tableLayout = findViewById(R.id.table_layout);
        spinnerPrograms = findViewById(R.id.spinner_programs);
        spinnerSemesters = findViewById(R.id.spinner_semesters);
        backArrow = findViewById(R.id.backArrow); // Initialize back arrow ImageView
        headerTitle = findViewById(R.id.headerTitle); // Initialize header title TextView
        Button btnAddCourse = findViewById(R.id.btn_add_course);

        // Set the header title text
        headerTitle.setText("All Courses");

        // --- Back Arrow Functionality ---
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity you want to go back to.
                // Replace AdminHomeActivity.class with the actual activity.
                Intent intent = new Intent(ManageCoursesAdminActivity.this, ManageStudentActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // Initialize the program-semester-course mapping with realistic courses
        initializeProgramSemesterCourses();

        // Set up the spinners
        setupProgramSpinner();
        setupSemesterSpinner();

        btnAddCourse.setOnClickListener(view -> showAddCourseDialog());
    }

    private void initializeProgramSemesterCourses() {
        programSemesterCoursesMap = new HashMap<>();

        // Helper to add courses to a specific program and semester
        addCoursesToMap("BSIT", "1", Arrays.asList(
                new Course("IT101", "Introduction to Programming", "4", "8 Semesters"),
                new Course("IT102", "Data Structures", "4", "8 Semesters")
        ));
        addCoursesToMap("BSIT", "2", Arrays.asList(
                new Course("IT201", "Object-Oriented Programming", "4", "8 Semesters"),
                new Course("IT202", "Discrete Structures", "4", "8 Semesters")
        ));
        addCoursesToMap("BSIT", "3", Arrays.asList(
                new Course("IT301", "Database Systems", "4", "8 Semesters"),
                new Course("IT302", "Computer Networks", "4", "8 Semesters")
        ));
        addCoursesToMap("BSIT", "4", Arrays.asList(
                new Course("IT401", "Web Development", "4", "8 Semesters"),
                new Course("IT402", "Operating Systems", "4", "8 Semesters")
        ));

        addCoursesToMap("BSCS", "1", Arrays.asList(
                new Course("CS101", "Calculus and Analytical Geometry", "3", "8 Semesters"),
                new Course("CS102", "Introduction to Computing", "3", "8 Semesters")
        ));
        addCoursesToMap("BSCS", "2", Arrays.asList(
                new Course("CS201", "Digital Logic Design", "3", "8 Semesters"),
                new Course("CS202", "Programming Fundamentals", "3", "8 Semesters")
        ));

        addCoursesToMap("BBA", "1", Arrays.asList(
                new Course("BA101", "Principles of Management", "3", "8 Semesters"),
                new Course("BA102", "Introduction to Business", "3", "8 Semesters")
        ));
        addCoursesToMap("BBA", "2", Arrays.asList(
                new Course("BA201", "Microeconomics", "3", "8 Semesters"),
                new Course("BA202", "Financial Accounting", "3", "8 Semesters")
        ));
        // Add more programs and semesters as needed
    }

    private void addCoursesToMap(String program, String semester, List<Course> courses) {
        if (!programSemesterCoursesMap.containsKey(program)) {
            programSemesterCoursesMap.put(program, new HashMap<>());
        }
        programSemesterCoursesMap.get(program).put(semester, courses);
    }

    private void setupProgramSpinner() {
        // Get unique program names from the map for the spinner
        String[] programs = programSemesterCoursesMap.keySet().toArray(new String[0]);
        Arrays.sort(programs); // Optional: sort programs alphabetically

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                programs
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(adapter);

        spinnerPrograms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTableForSelectedSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupSemesterSpinner() {
        // Create semester list from 1 to 8
        List<String> semesters = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            semesters.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                semesters
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemesters.setAdapter(adapter);

        spinnerSemesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTableForSelectedSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updateTableForSelectedSpinners() {
        // Clear existing rows (except header)
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }

        // Reset course index for the new selection
        courseIndex = 1;

        String selectedProgram = spinnerPrograms.getSelectedItem().toString();
        String selectedSemester = spinnerSemesters.getSelectedItem().toString();

        List<Course> courses = null;
        if (programSemesterCoursesMap.containsKey(selectedProgram)) {
            Map<String, List<Course>> semesterMap = programSemesterCoursesMap.get(selectedProgram);
            if (semesterMap != null && semesterMap.containsKey(selectedSemester)) {
                courses = semesterMap.get(selectedSemester);
            }
        }

        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
                addRowToTable(course.code, course.name, course.subjects, course.totalSem);
            }
        } else {
            // Display a message if no courses are found for the selection
            TableRow noDataRow = new TableRow(this);
            TextView noDataTv = new TextView(this);
            noDataTv.setText("No courses found for this program and semester.");
            noDataTv.setGravity(Gravity.CENTER);
            noDataTv.setPadding(dpToPx(4), dpToPx(16), dpToPx(4), dpToPx(16));
            noDataTv.setTextSize(16);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.span = 5; // Span across all 5 columns
            noDataTv.setLayoutParams(params);
            tableLayout.addView(noDataRow);
        }
    }

    private void showAddCourseDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dpToPx(20), dpToPx(15), dpToPx(20), dpToPx(5));

        EditText etCode = new EditText(this);
        etCode.setHint("Course Code");
        etCode.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        layout.addView(etCode);

        EditText etName = new EditText(this);
        etName.setHint("Course Name");
        etName.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        layout.addView(etName);

        EditText etSubjects = new EditText(this);
        etSubjects.setHint("Subjects (e.g., 3)");
        etSubjects.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSubjects.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        layout.addView(etSubjects);

        EditText etTotalSem = new EditText(this);
        etTotalSem.setHint("Total Sem/Year (e.g., 8 Semesters)");
        etTotalSem.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
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
                        String selectedProgram = spinnerPrograms.getSelectedItem().toString();
                        String selectedSemester = spinnerSemesters.getSelectedItem().toString();

                        // Add to the program's and semester's course list
                        Course newCourse = new Course(code, name, subjects, totalSem);
                        if (!programSemesterCoursesMap.containsKey(selectedProgram)) {
                            programSemesterCoursesMap.put(selectedProgram, new HashMap<>());
                        }
                        Map<String, List<Course>> semesterMap = programSemesterCoursesMap.get(selectedProgram);
                        if (!semesterMap.containsKey(selectedSemester)) {
                            semesterMap.put(selectedSemester, new ArrayList<>());
                        }
                        semesterMap.get(selectedSemester).add(newCourse);

                        // Refresh the table to include the newly added course if the current selection matches
                        updateTableForSelectedSpinners();

                        Toast.makeText(this, "Course Added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addRowToTable(String code, String name, String subjects, String totalSem) {
        TableRow row = new TableRow(this);
        row.setBackgroundColor(courseIndex % 2 == 0 ? 0xFFF5F5F5 : 0xFFFFFFFF); // Use hex colors directly

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
        tv.setPadding(dpToPx(4), dpToPx(16), dpToPx(4), dpToPx(16));
        tv.setTextColor(0xFF000000); // Set text color to black for better visibility on white/light gray background
        return tv;
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
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