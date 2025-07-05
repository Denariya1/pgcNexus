package com.example.pgcnexus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CourseAttendanceActivity extends AppCompatActivity {

    // Declare UI elements
    private TextView tvAttendancePercent, tvFinalPercent, tvMidPercent, tvTotalPresent, tvTotalAbsent;
    private LinearLayout lecturesContainer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_attendance);

        // Initialize UI components
        initializeViews();

        // Simulate attendance data (would normally come from backend/database)
        int overallAttendance = 89;
        int finalAttendance = 89;
        int midAttendance = 100;
        int totalPresent = 28;
        int totalAbsent = 2;

        // Update the text views with attendance data
        updateAttendanceTextViews(overallAttendance, finalAttendance, midAttendance, totalPresent, totalAbsent);

        // Simulate lecture data
        Lecture[] lectures = {
                new Lecture("P", 1, "September", 23, 2024),
                new Lecture("P", 2, "September", 23, 2024),
                new Lecture("P", 3, "September", 30, 2024),
                new Lecture("A", 4, "September", 30, 2024),
                new Lecture("P", 5, "October", 7, 2024)
        };

        // Populate lectures using existing item_lecture.xml layout
        populateLectures(lectures);
    }

    private void initializeViews() {
        lecturesContainer = findViewById(R.id.lecturesContainer);
        tvAttendancePercent = findViewById(R.id.tvAttendancePercent);
        tvFinalPercent = findViewById(R.id.tvFinalPercent);
        tvMidPercent = findViewById(R.id.tvMidPercent);
        tvTotalPresent = findViewById(R.id.tvTotalPresent);
        tvTotalAbsent = findViewById(R.id.tvTotalAbsent);

        if (lecturesContainer == null) {
            Log.e("CourseAttendance", "lecturesContainer not found!");
        }
    }

    private void updateAttendanceTextViews(int overall, int finals, int mid, int present, int absent) {
        tvAttendancePercent.setText(overall + "%");
        tvFinalPercent.setText(finals + "%");
        tvMidPercent.setText(mid + "%");
        tvTotalPresent.setText("Total Present: " + present);
        tvTotalAbsent.setText("Total Absent: " + absent);
    }

    private void populateLectures(Lecture[] lectures) {
        LayoutInflater inflater = LayoutInflater.from(this);
        lecturesContainer.removeAllViews();

        for (Lecture lecture : lectures) {
            View lectureView = inflater.inflate(R.layout.item_lecture, lecturesContainer, false);
            bindLectureData(lectureView, lecture);
            lecturesContainer.addView(lectureView);
        }
    }

    private void bindLectureData(View lectureView, Lecture lecture) {
        TextView tvStatus = lectureView.findViewById(R.id.tvStatus);
        TextView tvLecture = lectureView.findViewById(R.id.tvLecture);
        TextView tvMonth = lectureView.findViewById(R.id.tvMonth);
        TextView tvDay = lectureView.findViewById(R.id.tvDay);
        TextView tvYear = lectureView.findViewById(R.id.tvYear);

        // Set status indicator
        tvStatus.setText(lecture.status);
        if (lecture.status.equals("A")) {
            tvStatus.setBackgroundResource(R.drawable.red_rounded_corner);
        } else {
            tvStatus.setBackgroundResource(R.drawable.green_rounded_corner);
        }

        // Set lecture details
        tvLecture.setText("Lecture: " + lecture.number);
        tvMonth.setText(lecture.month);
        tvDay.setText(String.valueOf(lecture.day));
        tvYear.setText(String.valueOf(lecture.year));
    }

    // Lecture data model class
    static class Lecture {
        String status; // "P" or "A"
        int number;    // Lecture number
        String month;  // Month name
        int day;       // Day of month
        int year;      // Year

        Lecture(String status, int number, String month, int day, int year) {
            this.status = status;
            this.number = number;
            this.month = month;
            this.day = day;
            this.year = year;
        }
    }
}