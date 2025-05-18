package com.example.pgcnexus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredCoursesActivity extends AppCompatActivity {

    private GridView semesterGrid;
    private LinearLayout courseDetailsContainer;
    private TextView semesterTitle;
    private LinearLayout courseListContainer;

    private Map<String, List<Course>> semesterCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Corrected layout name here

        // Initialize views
        semesterGrid = findViewById(R.id.semesterGrid);
        courseDetailsContainer = findViewById(R.id.courseDetailsContainer);
        semesterTitle = findViewById(R.id.semesterTitle);
        courseListContainer = findViewById(R.id.courseListContainer);

        // Initialize semester data
        initializeSemesterData();

        // Set up semester grid
        List<String> semesters = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            semesters.add("Semester " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, semesters);
        semesterGrid.setAdapter(adapter);

        semesterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String semester = "Semester " + (position + 1);
                showSemesterDetails(semester);
            }
        });
    }

    private void initializeSemesterData() {
        semesterCourses = new HashMap<>();

        // Semester 1
        List<Course> semester1 = new ArrayList<>();
        semester1.add(new Course("ENGL101", "English-I", 3));
        semester1.add(new Course("ISLM101", "Islamic Studies", 3));
        semester1.add(new Course("COMP101", "Introduction to Computer", 3));
        semester1.add(new Course("POLS101", "Principles of Political Science", 3));
        semester1.add(new Course("MATH100", "General Mathematics", 3));
        semester1.add(new Course("MCOM101", "Introduction to Mass Communication", 3));
        semesterCourses.put("Semester 1", semester1);

        // Semester 2
        List<Course> semester2 = new ArrayList<>();
        semester2.add(new Course("ENGL102", "English-II", 3));
        semester2.add(new Course("PAK101", "Pakistan Studies", 3));
        semester2.add(new Course("COMP102", "Programming Fundamentals", 4));
        semester2.add(new Course("MATH101", "Calculus", 3));
        semester2.add(new Course("PHYS101", "Applied Physics", 3));
        semester2.add(new Course("STAT101", "Statistics", 3));
        semesterCourses.put("Semester 2", semester2);

        // Semester 3
        List<Course> semester3 = new ArrayList<>();
        semester3.add(new Course("COMP201", "Object Oriented Programming", 4));
        semester3.add(new Course("COMP202", "Data Structures", 4));
        semester3.add(new Course("COMP203", "Discrete Structures", 3));
        semester3.add(new Course("MATH201", "Linear Algebra", 3));
        semester3.add(new Course("COMP204", "Digital Logic Design", 3));
        semester3.add(new Course("HUMA101", "Technical Writing", 3));
        semesterCourses.put("Semester 3", semester3);

        // Semester 4
        List<Course> semester4 = new ArrayList<>();
        semester4.add(new Course("COMP205", "Database Systems", 4));
        semester4.add(new Course("COMP206", "Computer Networks", 4));
        semester4.add(new Course("COMP207", "Operating Systems", 4));
        semester4.add(new Course("COMP208", "Computer Organization & Assembly", 3));
        semester4.add(new Course("MATH202", "Probability & Statistics", 3));
        semester4.add(new Course("MGMT101", "Principles of Management", 3));
        semesterCourses.put("Semester 4", semester4);

        // Semester 5
        List<Course> semester5 = new ArrayList<>();
        semester5.add(new Course("COMP301", "Theory of Automata", 3));
        semester5.add(new Course("COMP302", "Software Engineering", 4));
        semester5.add(new Course("COMP303", "Web Technologies", 4));
        semester5.add(new Course("COMP304", "Artificial Intelligence", 3));
        semester5.add(new Course("COMP305", "Data Communication", 3));
        semester5.add(new Course("COMP306", "Numerical Computing", 3));
        semesterCourses.put("Semester 5", semester5);

        // Semester 6
        List<Course> semester6 = new ArrayList<>();
        semester6.add(new Course("COMP307", "Compiler Construction", 3));
        semester6.add(new Course("COMP308", "Computer Graphics", 3));
        semester6.add(new Course("COMP309", "Information Security", 3));
        semester6.add(new Course("COMP310", "Parallel Computing", 3));
        semester6.add(new Course("COMP311", "Data Mining", 3));
        semester6.add(new Course("COMP312", "Human Computer Interaction", 3));
        semester6.add(new Course("COMP313", "Professional Practices", 3));
        semesterCourses.put("Semester 6", semester6);

        // Semester 7
        List<Course> semester7 = new ArrayList<>();
        semester7.add(new Course("COMP401", "Final Year Project-I", 3));
        semester7.add(new Course("COMP402", "Cloud Computing", 3));
        semester7.add(new Course("COMP403", "Big Data Analytics", 3));
        semester7.add(new Course("COMP404", "Machine Learning", 3));
        semester7.add(new Course("COMP405", "Mobile Application Development", 3));
        semester7.add(new Course("COMP406", "Wireless Networks", 3));
        semesterCourses.put("Semester 7", semester7);

        // Semester 8
        List<Course> semester8 = new ArrayList<>();
        semester8.add(new Course("COMP407", "Final Year Project-II", 3));
        semester8.add(new Course("COMP408", "Internet of Things", 3));
        semester8.add(new Course("COMP409", "Blockchain Technologies", 3));
        semester8.add(new Course("COMP410", "Natural Language Processing", 3));
        semester8.add(new Course("COMP411", "Advanced Database Systems", 3));
        semester8.add(new Course("COMP412", "Entrepreneurship in IT", 3));
        semesterCourses.put("Semester 8", semester8);
    }

    private void showSemesterDetails(String semester) {
        courseDetailsContainer.setVisibility(View.VISIBLE);
        semesterTitle.setText(semester);

        // Clear previous courses
        courseListContainer.removeAllViews();

        // Add current semester courses
        List<Course> courses = semesterCourses.get(semester);
        if (courses != null) {
            for (Course course : courses) {
                View courseView = LayoutInflater.from(this).inflate(R.layout.course_item, courseListContainer, false);

                TextView codeView = courseView.findViewById(R.id.courseCode);
                TextView nameView = courseView.findViewById(R.id.courseName);
                TextView creditView = courseView.findViewById(R.id.courseCredit);

                codeView.setText(course.getCode());
                nameView.setText(course.getName());
                creditView.setText("Credit Hours: " + course.getCreditHours());

                courseListContainer.addView(courseView);
            }
        }
    }

    // Course class to hold course information
    private static class Course {
        private String code;
        private String name;
        private int creditHours;

        public Course(String code, String name, int creditHours) {
            this.code = code;
            this.name = name;
            this.creditHours = creditHours;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public int getCreditHours() {
            return creditHours;
        }
    }
}
