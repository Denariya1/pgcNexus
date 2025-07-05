package com.example.pgcnexus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CoursesDriveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseFilesAdapter adapter;
    private List<CourseFile> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_drive);

        // Initialize views
        recyclerView = findViewById(R.id.rvCourseFiles);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseFilesAdapter(fileList);
        recyclerView.setAdapter(adapter);

        // Load files
        loadCourseFiles();
    }

    private void loadCourseFiles() {
        fileList.clear();

        // Sample data - replace with actual data loading
        fileList.add(new CourseFile(
                "Lecture_1.pdf",
                "PDF",
                "2.4 MB",
                "May 15, 2023"
        ));

        fileList.add(new CourseFile(
                "Assignment_Guidelines.docx",
                "DOCX",
                "1.1 MB",
                "May 10, 2023"
        ));

        adapter.notifyDataSetChanged();
        findViewById(R.id.emptyState).setVisibility(
                fileList.isEmpty() ? View.VISIBLE : View.GONE
        );
    }

    // Model class for course files
    private static class CourseFile {
        private final String fileName;
        private final String fileType;
        private final String fileSize;
        private final String uploadDate;

        public CourseFile(String fileName, String fileType,
                          String fileSize, String uploadDate) {
            this.fileName = fileName;
            this.fileType = fileType;
            this.fileSize = fileSize;
            this.uploadDate = uploadDate;
        }
    }

    // Adapter class
    private class CourseFilesAdapter extends RecyclerView.Adapter<CourseFilesAdapter.ViewHolder> {
        private final List<CourseFile> files;
        private final Context context;

        public CourseFilesAdapter(List<CourseFile> files) {
            this.files = files;
            this.context = CoursesDriveActivity.this;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.course_file_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CourseFile file = files.get(position);

            holder.fileName.setText(file.fileName);
            holder.fileSize.setText(file.fileSize);
            holder.uploadDate.setText(file.uploadDate);

            // Set appropriate icon based on file type
            int iconRes = R.drawable.ic_file_generic;
            if ("PDF".equalsIgnoreCase(file.fileType)) {
                iconRes = R.drawable.ic_pdf;
            } else if ("DOCX".equalsIgnoreCase(file.fileType)) {
                iconRes = R.drawable.ic_word;
            }
            holder.fileIcon.setImageResource(iconRes);

            holder.downloadButton.setOnClickListener(v -> {
                Toast.makeText(context,
                        "Downloading: " + file.fileName,
                        Toast.LENGTH_SHORT).show();
                // TODO: Implement actual file download logic
            });
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        // ViewHolder class
        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView fileIcon;
            final TextView fileName;
            final TextView fileSize;
            final TextView uploadDate;
            final ImageButton downloadButton;

            ViewHolder(View itemView) {
                super(itemView);
                fileIcon = itemView.findViewById(R.id.ivFileIcon);
                fileName = itemView.findViewById(R.id.tvFileName);
                fileSize = itemView.findViewById(R.id.tvFileSize);
                uploadDate = itemView.findViewById(R.id.tvUploadDate);
                downloadButton = itemView.findViewById(R.id.btnDownload);
            }
        }
    }
}