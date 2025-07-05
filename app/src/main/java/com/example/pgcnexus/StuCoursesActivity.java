package com.example.pgcnexus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StuCoursesActivity extends AppCompatActivity {

    private RecyclerView courseRecyclerView;  // RecyclerView to display the courses
    private ArrayList<String> courses;        // List to hold courses
    private CourseAdapter courseAdapter;      // Adapter for the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_courses);  // Set the layout file

        // Initialize the RecyclerView and course list
        courseRecyclerView = findViewById(R.id.course_recycler_view);  // Ensure correct ID
        courses = new ArrayList<>();

        // Add sample courses to the list
        courses.add("Operational Research");
        courses.add("Virtual Systems and Services");
        courses.add("Network Security");
        courses.add("Final Year Project");

        // Log the course list to ensure it's populated
        Log.d("StuCoursesActivity", "Courses: " + courses);

        // Set up the RecyclerView's layout manager (vertical scroll)
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the CourseAdapter and set it to the RecyclerView
        courseAdapter = new CourseAdapter(courses);
        courseRecyclerView.setAdapter(courseAdapter);  // Set the adapter to the RecyclerView

        // Handle item clicks in RecyclerView using RecyclerItemClickListener
        courseRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, courseRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String selectedCourse = courses.get(position);  // Get the selected course
                showCourseOptionsDialog(selectedCourse);  // Show the dialog with options
            }
        }));
    }

    // Method to show the options dialog for the selected course
    private void showCourseOptionsDialog(final String selectedCourse) {
        // Options to show in the dialog
        String[] options = {
                "Course Outline",
                "Attendance",
                "Announcements",
                "Grading",
                "Courses Drive"
        };

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an Option for: " + selectedCourse);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the option clicked
                Intent intent = null;
                switch (which) {
                    case 0: // Course Outline
                        intent = new Intent(StuCoursesActivity.this, CourseOutlineActivity.class);
                        break;
                    case 1: // Attendance
//                        intent = new Intent(StuCoursesActivity.this, CourseOutlineActivity.class);
                        intent = new Intent(StuCoursesActivity.this, CourseAttendanceActivity.class);
                        break;
                    case 2: // Announcements
                        intent = new Intent(StuCoursesActivity.this, CourseAnnouncementActivity.class);
                        break;
                    case 3: // Grading
                        intent = new Intent(StuCoursesActivity.this, CoursesGradesActivity.class);
                        break;
                    case 4: // Courses Drive
                        intent = new Intent(StuCoursesActivity.this, CoursesDriveActivity.class);
                        break;
                }

                if (intent != null) {
                    startActivity(intent); // Start the respective activity
                } else {
                    Toast.makeText(StuCoursesActivity.this, "Option not implemented yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Show the dialog
        builder.create().show();
    }
}
