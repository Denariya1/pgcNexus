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
public class CoursesGradesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GradesAdapter adapter;
    private List<GradeItem> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_grades);

        // Initialize views
        recyclerView = findViewById(R.id.rvGrades);
        View emptyState = findViewById(R.id.emptyState);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GradesAdapter(gradeList);
        recyclerView.setAdapter(adapter);

        // Load grades
        loadGrades();
    }

    private void loadGrades() {
        // TODO: Replace with actual data loading
        gradeList.clear();

        // Sample data
        gradeList.add(new GradeItem(
                "COMP 101",
                "Midterm Exam",
                "A-",
                "Excellent work! Minor improvements needed in section 3.",
                "Graded: May 15, 2023"
        ));

        gradeList.add(new GradeItem(
                "COMP 101",
                "Assignment 3",
                "B+",
                "Good solution, but late submission (-10%)",
                "Graded: Apr 28, 2023"
        ));

        adapter.notifyDataSetChanged();
        findViewById(R.id.emptyState).setVisibility(
                gradeList.isEmpty() ? View.VISIBLE : View.GONE
        );
    }

    // Grade Item Model
    private static class GradeItem {
        String courseName;
        String assignmentTitle;
        String grade;
        String feedback;
        String gradedDate;

        public GradeItem(String courseName, String assignmentTitle,
                         String grade, String feedback, String gradedDate) {
            this.courseName = courseName;
            this.assignmentTitle = assignmentTitle;
            this.grade = grade;
            this.feedback = feedback;
            this.gradedDate = gradedDate;
        }
    }

    // Adapter
    private class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {
        private List<GradeItem> grades;

        public GradesAdapter(List<GradeItem> grades) {
            this.grades = grades;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grade_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            GradeItem item = grades.get(position);

            holder.courseName.setText(item.courseName);
            holder.assignmentTitle.setText(item.assignmentTitle);
            holder.grade.setText(item.grade);
            holder.feedback.setText(item.feedback);
            holder.gradedDate.setText(item.gradedDate);
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView courseName, assignmentTitle, grade, feedback, gradedDate;

            ViewHolder(View itemView) {
                super(itemView);
                courseName = itemView.findViewById(R.id.tvCourseName);
                assignmentTitle = itemView.findViewById(R.id.tvAssignmentTitle);
                grade = itemView.findViewById(R.id.tvGrade);
                feedback = itemView.findViewById(R.id.tvFeedback);
                gradedDate = itemView.findViewById(R.id.tvGradedDate);
            }
        }
    }
}