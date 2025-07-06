package com.example.pgcnexus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private List<Teacher> teachersList;
    private OnTeacherClickListener listener;

    public interface OnTeacherClickListener {
        void onTeacherClick(Teacher teacher);
    }

    public TeacherAdapter(List<Teacher> teachersList, OnTeacherClickListener listener) {
        this.teachersList = teachersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_item_layout, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teachersList.get(position);
        holder.bind(teacher);
    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }

    public void updateTeachers(List<Teacher> newTeachers) {
        this.teachersList = newTeachers;
        notifyDataSetChanged();
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private ImageView arrowImage;
        private LinearLayout detailsLayout;
        private CardView cardView;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            nameText = itemView.findViewById(R.id.teacherName);
            arrowImage = itemView.findViewById(R.id.teacherArrow);
            detailsLayout = itemView.findViewById(R.id.teacherDetails);
        }

        public void bind(Teacher teacher) {
            nameText.setText(teacher.getFullName());

            // Set click listener for expand/collapse
            itemView.setOnClickListener(v -> {
                if (detailsLayout.getVisibility() == View.GONE) {
                    detailsLayout.setVisibility(View.VISIBLE);
                } else {
                    detailsLayout.setVisibility(View.GONE);
                }
            });

            // Set up detail text views
            setupDetailTextViews(teacher);
        }

        private void setupDetailTextViews(Teacher teacher) {
            detailsLayout.removeAllViews();
            
            addDetailTextView("Full Name: " + teacher.getFullName());
            addDetailTextView("Email: " + teacher.getEmail());
            addDetailTextView("Department: " + teacher.getDepartment());
            addDetailTextView("Phone: " + teacher.getPhone());
            addDetailTextView("Qualification: " + teacher.getQualification());
            addDetailTextView("Subjects: " + teacher.getSubjects());
        }

        private void addDetailTextView(String text) {
            TextView textView = new TextView(itemView.getContext());
            textView.setText(text);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            detailsLayout.addView(textView);
        }
    }
} 