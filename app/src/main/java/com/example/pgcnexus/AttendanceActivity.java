package com.example.pgcnexus;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {
    Spinner spinnerClassSelector;
    Button btnSubmitAttendance;
    LinearLayout containerAttendance;
    String selectedClass = "";

    private Map<String, String[]> classStudentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Initialize UI components
        spinnerClassSelector = findViewById(R.id.spinnerClassSelector);
        btnSubmitAttendance = findViewById(R.id.btnSubmitAttendance);
        containerAttendance = findViewById(R.id.containerAttendance);

        // Load sample data
        initializeStudentData();

        // Class list for spinner
        String[] classList = {
                "Select Class",
                "BSIT 21 B",
                "BSCS 22 A",
                "BSSE 20 C",
                "BBA 23 D",
                "MCS 24 A"
        };

        // Custom spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Disable "Select Class" item
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassSelector.setAdapter(adapter);

        // Auto-select first class (optional)
        spinnerClassSelector.setSelection(1);
        selectedClass = classList[1];
        showClassAttendance(selectedClass);

        // Spinner item selection listener
        spinnerClassSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedClass = classList[position];
                    showClassAttendance(selectedClass);
                } else {
                    selectedClass = "";
                    showClassAttendance("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Submit button click listener
        btnSubmitAttendance.setOnClickListener(v -> {
            if (selectedClass.isEmpty()) {
                Toast.makeText(this, "Please select a class first!", Toast.LENGTH_SHORT).show();
            } else {
                if (validateAttendance()) {
                    submitAttendance();
                }
            }
        });
    }

    private void initializeStudentData() {
        classStudentMap.put("BSIT 21 B", new String[]{
                "Ali Raza", "Fatima Khan", "Zain Malik", "Hassan Javed", "Ayesha Siddiqui"
        });

        classStudentMap.put("BSCS 22 A", new String[]{
                "Sara Ahmed", "Bilal Asif", "Mehwish Noor", "Hamza Tariq", "Sana Yousaf"
        });

        classStudentMap.put("BSSE 20 C", new String[]{
                "Ahmed Raza", "Zara Khan", "Usman Malik", "Hina Javed", "Farhan Siddiqui"
        });

        classStudentMap.put("BBA 23 D", new String[]{
                "Amina Khan", "Babar Asif", "Mariam Noor", "Tariq Javed", "Yasmeen Yousaf"
        });

        classStudentMap.put("MCS 24 A", new String[]{
                "Imran Khan", "Sadia Malik", "Nasir Javed", "Fariha Siddiqui", "Kamran Yousaf"
        });
    }

    private void showClassAttendance(String className) {
        containerAttendance.removeAllViews();

        if (className == null || className.isEmpty()) {
            // Show placeholder when no class selected
            TextView tvPlaceholder = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, dpToPx(24), 0, 0);
            tvPlaceholder.setLayoutParams(params);

            tvPlaceholder.setText("Select a class to view attendance");
            tvPlaceholder.setTextSize(16);
            tvPlaceholder.setTextColor(Color.DKGRAY);
            tvPlaceholder.setGravity(View.TEXT_ALIGNMENT_CENTER);
            containerAttendance.addView(tvPlaceholder);
            return;
        }

        // Add header row
        LinearLayout headerLayout = new LinearLayout(this);
        headerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setPadding(dpToPx(16), dpToPx(12), dpToPx(16), dpToPx(12));
        headerLayout.setBackgroundColor(Color.parseColor("#301E8A"));

        TextView tvNameHeader = new TextView(this);
        tvNameHeader.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));
        tvNameHeader.setText("Student Name");
        tvNameHeader.setTextColor(Color.WHITE);
        tvNameHeader.setTypeface(null, Typeface.BOLD);

        TextView tvAttendanceHeader = new TextView(this);
        tvAttendanceHeader.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tvAttendanceHeader.setText("Status");
        tvAttendanceHeader.setTextColor(Color.WHITE);
        tvAttendanceHeader.setTypeface(null, Typeface.BOLD);
        tvAttendanceHeader.setPadding(dpToPx(16), 0, 0, 0);

        headerLayout.addView(tvNameHeader);
        headerLayout.addView(tvAttendanceHeader);
        containerAttendance.addView(headerLayout);

        // Add student rows
        String[] students = classStudentMap.get(className);
        for (String student : students) {
            addStudentAttendanceRow(student);
        }
    }

    private void addStudentAttendanceRow(String studentName) {
        // Main row layout
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setPadding(dpToPx(16), dpToPx(12), dpToPx(16), dpToPx(12));
        rowLayout.setTag(studentName); // Set student name as tag for easy reference

        // Student name text
        TextView tvStudent = new TextView(this);
        tvStudent.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));
        tvStudent.setText(studentName);
        tvStudent.setTextSize(16);
        tvStudent.setTag(studentName + "_text"); // Set unique tag

        // Attendance radio group
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setPadding(dpToPx(16), 0, 0, 0);
        radioGroup.setTag(studentName + "_radio"); // Set unique tag

        RadioButton rbPresent = new RadioButton(this);
        rbPresent.setText("Present");
        rbPresent.setTextColor(Color.BLACK);
        rbPresent.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#301E8A")));

        RadioButton rbAbsent = new RadioButton(this);
        rbAbsent.setText("Absent");
        rbAbsent.setTextColor(Color.BLACK);
        rbAbsent.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#301E8A")));

        radioGroup.addView(rbPresent);
        radioGroup.addView(rbAbsent);

        rowLayout.addView(tvStudent);
        rowLayout.addView(radioGroup);
        containerAttendance.addView(rowLayout);

        // Add divider
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1));
        divider.setBackgroundColor(Color.parseColor("#EEEEEE"));
        containerAttendance.addView(divider);
    }

    private boolean validateAttendance() {
        boolean allSelected = true;

        // Reset all highlights first
        for (int i = 0; i < containerAttendance.getChildCount(); i++) {
            View child = containerAttendance.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) child;
                if (row.getTag() != null) { // This is a student row
                    TextView tvStudent = row.findViewWithTag(row.getTag() + "_text");
                    if (tvStudent != null) {
                        tvStudent.setTextColor(Color.BLACK);
                    }
                }
            }
        }

        // Check each student's attendance status
        for (int i = 0; i < containerAttendance.getChildCount(); i++) {
            View child = containerAttendance.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) child;
                if (row.getTag() != null) { // This is a student row
                    RadioGroup radioGroup = row.findViewWithTag(row.getTag() + "_radio");
                    if (radioGroup != null && radioGroup.getCheckedRadioButtonId() == -1) {
                        // No radio button selected for this student
                        TextView tvStudent = row.findViewWithTag(row.getTag() + "_text");
                        if (tvStudent != null) {
                            tvStudent.setTextColor(Color.RED);
                        }
                        allSelected = false;
                    }
                }
            }
        }

        if (!allSelected) {
            Toast.makeText(this, "Please mark attendance for all students!", Toast.LENGTH_LONG).show();
        }

        return allSelected;
    }

    private void submitAttendance() {
        // Here you would typically save the attendance data to your database
        // For now, we'll just show a success message

        StringBuilder attendanceData = new StringBuilder();
        for (int i = 0; i < containerAttendance.getChildCount(); i++) {
            View child = containerAttendance.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) child;
                if (row.getTag() != null) { // This is a student row
                    String studentName = (String) row.getTag();
                    RadioGroup radioGroup = row.findViewWithTag(row.getTag() + "_radio");
                    if (radioGroup != null) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = row.findViewById(selectedId);
                        String status = radioButton.getText().toString();
                        attendanceData.append(studentName).append(": ").append(status).append("\n");
                    }
                }
            }
        }

        Toast.makeText(this, "Attendance submitted for " + selectedClass + ":\n" + attendanceData.toString(),
                Toast.LENGTH_LONG).show();
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}