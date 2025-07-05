package com.example.pgcnexus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CourseAnnouncementActivity extends AppCompatActivity {

    // Simplified Model class without attachment field
    private static class Announcement {
        String id;
        String title;
        String content;
        String teacherName;
        String date;

        Announcement(String id, String title, String content,
                     String teacherName, String date) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.teacherName = teacherName;
            this.date = date;
        }
    }

    // Simplified Adapter class
    private class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
        private List<Announcement> announcements;

        AnnouncementAdapter(List<Announcement> announcements) {
            this.announcements = announcements;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.announcement_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Announcement announcement = announcements.get(position);

            holder.title.setText(announcement.title);
            holder.content.setText(announcement.content);
            holder.teacherName.setText(announcement.teacherName);
            holder.date.setText(announcement.date);

            holder.itemView.setOnClickListener(v ->
                    showAnnouncementDetails(announcement));
        }

        @Override
        public int getItemCount() {
            return announcements.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, content, teacherName, date;

            ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tvAnnouncementTitle);
                content = itemView.findViewById(R.id.tvAnnouncementContent);
                teacherName = itemView.findViewById(R.id.tvTeacherName);
                date = itemView.findViewById(R.id.tvAnnouncementDate);
            }
        }
    }

    private RecyclerView recyclerView;
    private View emptyStateView;
    private List<Announcement> announcementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_announcement);

        // Initialize views
        recyclerView = findViewById(R.id.rvAnnouncements);
        emptyStateView = findViewById(R.id.emptyState);

        setupRecyclerView();
        loadAnnouncements();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AnnouncementAdapter(announcementList));
    }

    private void loadAnnouncements() {
        announcementList.clear();

        // Sample data without attachment references
        announcementList.add(new Announcement(
                "1",
                "Midterm Exam Schedule",
                "The midterm exam will be held on June 30th from 9-11 AM in Room 205.",
                "Dr. Smith",
                "June 10, 2023"
        ));

        announcementList.add(new Announcement(
                "2",
                "Assignment Submission",
                "Assignment 3 deadline has been extended to Friday 5 PM.",
                "Dr. Smith",
                "June 5, 2023"
        ));

        recyclerView.getAdapter().notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        emptyStateView.setVisibility(
                announcementList.isEmpty() ? View.VISIBLE : View.GONE
        );
    }

    private void showAnnouncementDetails(Announcement announcement) {
        // Implementation for showing details
    }
}