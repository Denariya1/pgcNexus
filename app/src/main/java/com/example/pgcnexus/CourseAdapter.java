package com.example.pgcnexus;  // Replace with your actual package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<String> courseList;  // List of courses to display

    // Constructor to pass the list of courses
    public CourseAdapter(ArrayList<String> courseList) {
        this.courseList = courseList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item in RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);  // Return the ViewHolder
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        // Get the course at the given position
        String course = courseList.get(position);

        // Set the course name in the TextView
        holder.courseName.setText(course);
    }

    @Override
    public int getItemCount() {
        return courseList.size();  // Return the total number of items in the list
    }

    // ViewHolder class for each item in the RecyclerView
    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;  // TextView to display the course name

        public CourseViewHolder(View itemView) {
            super(itemView);
            // Initialize the views from the course_item.xml layout
            courseName = itemView.findViewById(R.id.course_name);  // Find the TextView
        }
    }
}
