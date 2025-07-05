package com.example.pgcnexus;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class MarksActivity extends AppCompatActivity {
    private TableLayout marksTable;
    private Button btnSaveMarks;
    private Spinner programSpinner;
    private Map<String, List<Student>> programStudentsMap = new HashMap<>();
    private Map<String, EditText> marksInputs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        initializeViews();
        initializeSampleData();
        setupClickListeners();

        // Show initial data (first program by default)
        showStudentsForProgram("BSIT 21B");
    }

    private void initializeViews() {
        marksTable = findViewById(R.id.marks_table);
        btnSaveMarks = findViewById(R.id.btn_save_marks);
        programSpinner = findViewById(R.id.spinner_exam_type);

        // Set up Spinner with programs
        String[] programs = {"Select Program", "BSIT 21B", "BSCS 22", "BSSE 22"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, programs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programSpinner.setAdapter(adapter);
    }

    private void initializeSampleData() {
        // BSIT 21B Students
        List<Student> bsitStudents = new ArrayList<>();
        bsitStudents.add(new Student("Denarya Maryam"));
        bsitStudents.add(new Student("Hafsa Khalid"));
        bsitStudents.add(new Student("Huda Rasheed"));
        programStudentsMap.put("BSIT 21B", bsitStudents);

        // BSCS 22 Students
        List<Student> bscsStudents = new ArrayList<>();
        bscsStudents.add(new Student("Maria Anwar"));
        bscsStudents.add(new Student("Mehwish Mushtaq"));
        programStudentsMap.put("BSCS 22", bscsStudents);

        // BSSE 22 Students
        List<Student> bsseStudents = new ArrayList<>();
        bsseStudents.add(new Student("Saba Ahmad"));
        bsseStudents.add(new Student("Sehrish Shafiq"));
        bsseStudents.add(new Student("Ayesha Khan"));
        bsseStudents.add(new Student("Fatima Ali"));
        programStudentsMap.put("BSSE 22", bsseStudents);
    }

    private void setupClickListeners() {
        btnSaveMarks.setOnClickListener(v -> saveAllMarks());

        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProgram = parent.getItemAtPosition(position).toString();
                if (!selectedProgram.equals("Select Program")) {
                    showStudentsForProgram(selectedProgram);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private void showStudentsForProgram(String program) {
        // Clear existing rows (keep header)
        marksTable.removeViews(1, marksTable.getChildCount() - 1);
        marksInputs.clear();

        List<Student> students = programStudentsMap.get(program);
        if (students == null || students.isEmpty()) {
            addEmptyTableMessage();
            return;
        }

        boolean alternateColor = false;
        for (Student student : students) {
            addStudentRow(student, alternateColor);
            alternateColor = !alternateColor;
        }
    }

    private void addStudentRow(Student student, boolean alternateColor) {
        TableRow row = new TableRow(this);

        // Student Name
        TextView nameView = new TextView(this);
        nameView.setLayoutParams(new TableRow.LayoutParams(
                dpToPx(160), TableRow.LayoutParams.WRAP_CONTENT));
        nameView.setText(student.getName());
        nameView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        nameView.setMaxLines(1);
        nameView.setEllipsize(android.text.TextUtils.TruncateAt.END);
        row.addView(nameView);

        // Assignment Marks
        EditText assignmentInput = createMarksInput(student, "assignment", 0, 10);
        row.addView(assignmentInput);
        marksInputs.put(student.getName() + " assignment", assignmentInput);

        // Quiz Marks
        EditText quizInput = createMarksInput(student, "quiz", 0, 10);
        row.addView(quizInput);
        marksInputs.put(student.getName() + " quiz", quizInput);

        // Midterm Marks
        EditText midtermInput = createMarksInput(student, "midterm", 0, 50);
        row.addView(midtermInput);
        marksInputs.put(student.getName() + " midterm", midtermInput);

        // Final Marks
        EditText finalInput = createMarksInput(student, "final", 0, 100);
        row.addView(finalInput);
        marksInputs.put(student.getName() + " final", finalInput);

        // Set background color
        row.setBackgroundColor(alternateColor ?
                getResources().getColor(android.R.color.background_light) :
                Color.WHITE);

        marksTable.addView(row);
    }

    private EditText createMarksInput(Student student, String type, int min, int max) {
        EditText input = new EditText(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                dpToPx(80), TableRow.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(params);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint(String.format("%d-%d", min, max));
        input.setTag(student.getName() + "_" + type);
        return input;
    }

    private void addEmptyTableMessage() {
        TableRow row = new TableRow(this);
        TextView message = new TextView(this);
        message.setText("No students found for this program");
        message.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        message.setGravity(View.TEXT_ALIGNMENT_CENTER);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.span = 5; // Span all columns
        row.addView(message, params);

        marksTable.addView(row);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void saveAllMarks() {
        boolean hasErrors = false;

        for (Map.Entry<String, EditText> entry : marksInputs.entrySet()) {
            String key = entry.getKey();
            EditText input = entry.getValue();
            String marksStr = input.getText().toString();

            if (marksStr.isEmpty()) {
                continue; // Skip empty fields
            }

            try {
                double marks = Double.parseDouble(marksStr);
                String[] parts = key.split(" ");
                String type = parts[1];
                double maxMarks = type.equals("assignment") || type.equals("quiz") ? 10 :
                        type.equals("midterm") ? 50 : 100;

                if (marks < 0 || marks > maxMarks) {
                    input.setError(String.format("Must be between 0-%d", (int)maxMarks));
                    hasErrors = true;
                }
            } catch (NumberFormatException e) {
                input.setError("Invalid number");
                hasErrors = true;
            }
        }

        if (!hasErrors) {
            // Save to database logic would go here
            Toast.makeText(this, "Marks saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please correct the errors", Toast.LENGTH_SHORT).show();
        }
    }

    private static class Student {
        private final String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() { return name; }
    }
}
