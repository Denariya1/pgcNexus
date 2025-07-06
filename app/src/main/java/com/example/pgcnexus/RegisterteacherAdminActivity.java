package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegisterteacherAdminActivity extends AppCompatActivity {

    private static final String TAG = "TeacherRegistration";
    private ImageView backArrow;
    private Button addTeacherButton;
    private EditText searchBar;
    private RecyclerView teachersRecyclerView;
    private FirebaseFirestore db;
    private List<Teacher> teachersList;
    private TeacherAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteacher_admin);

        try {
            // Initialize Firebase Firestore
            db = FirebaseFirestore.getInstance();
            teachersList = new ArrayList<>();

            // Initialize Views
            initializeViews();
            setupClickListeners();
            
            // Load teachers from Firebase
            loadTeachersFromFirebase();

        } catch (Exception e) {
            Log.e(TAG, "Error initializing activity", e);
            Toast.makeText(this, "Error loading registration form", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializeViews() {
        backArrow = findViewById(R.id.backArrow);
        addTeacherButton = findViewById(R.id.addTeacherButton);
        searchBar = findViewById(R.id.searchBar);
        teachersRecyclerView = findViewById(R.id.teachersRecyclerView);
        
        // Setup RecyclerView
        teachersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teacherAdapter = new TeacherAdapter(teachersList, teacher -> {
            // Handle teacher click if needed
        });
        teachersRecyclerView.setAdapter(teacherAdapter);
    }

    private void setupClickListeners() {
        // Back arrow functionality
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterteacherAdminActivity.this, ManageTeacherActivity.class);
            startActivity(intent);
            finish();
        });

        // Add Teacher button functionality
        addTeacherButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterteacherAdminActivity.this, AddTeacherActivity.class);
            startActivity(intent);
        });

        // Search functionality
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String searchQuery = searchBar.getText().toString().trim();
            filterTeachers(searchQuery);
            return true;
        });

        // Add sample data for testing (you can remove this later)
        addSampleDataIfNeeded();
    }

    private void loadTeachersFromFirebase() {
        db.collection("teachers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            teachersList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Teacher teacher = document.toObject(Teacher.class);
                                if (teacher != null) {
                                    teacher.setId(document.getId());
                                    teachersList.add(teacher);
                                }
                            }
                            displayTeachers();
                        } else {
                            Log.e(TAG, "Error getting teachers", task.getException());
                            Toast.makeText(RegisterteacherAdminActivity.this, "Error loading teachers", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayTeachers() {
        if (teachersList.isEmpty()) {
            // Show empty state
            Toast.makeText(this, "No teachers found", Toast.LENGTH_SHORT).show();
        }
        teacherAdapter.updateTeachers(teachersList);
    }



    private void toggleVisibility(LinearLayout layout) {
        layout.setVisibility(layout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    private void filterTeachers(String query) {
        if (query.isEmpty()) {
            displayTeachers();
            return;
        }

        List<Teacher> filteredList = new ArrayList<>();
        for (Teacher teacher : teachersList) {
            if (teacher.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                teacher.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                teacher.getDepartment().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(teacher);
            }
        }

        // Display filtered teachers
        displayFilteredTeachers(filteredList);
    }

    private void displayFilteredTeachers(List<Teacher> filteredTeachers) {
        if (filteredTeachers.isEmpty()) {
            Toast.makeText(this, "No teachers found matching your search", Toast.LENGTH_SHORT).show();
        }
        teacherAdapter.updateTeachers(filteredTeachers);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload teachers when returning from AddTeacherActivity
        loadTeachersFromFirebase();
    }

    private void addSampleDataIfNeeded() {
        // Check if there are any teachers in Firebase
        db.collection("teachers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().isEmpty()) {
                        // No teachers found, add sample data
                        FirebaseDataManager dataManager = new FirebaseDataManager();
                        dataManager.addSampleTeachers();
                        Toast.makeText(this, "Sample teachers added", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
