package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FaculityAdminActivity extends AppCompatActivity {

    private static final String TAG = "FacultyAdminActivity";
    private EditText etNewFaculty;
    private Button btnAddFaculty;
    private ListView listFaculties;
    private ImageView backArrow;

    private ArrayList<String> facultyList;
    private ArrayAdapter<String> facultyAdapter;
    private FirebaseFirestore db;
    private List<Faculty> facultyDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculity_admin);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
        facultyDataList = new ArrayList<>();

        initializeViews();
        setupAdapter();
        loadFacultiesFromFirebase();
        setupClickListeners();
    }

    private void initializeViews() {
        etNewFaculty = findViewById(R.id.et_new_faculty);
        btnAddFaculty = findViewById(R.id.btn_add_faculty);
        listFaculties = findViewById(R.id.list_faculties);
        backArrow = findViewById(R.id.backArrow);
    }

    private void setupAdapter() {
        facultyList = new ArrayList<>();
        facultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, facultyList);
        listFaculties.setAdapter(facultyAdapter);
    }

    private void setupClickListeners() {
        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFaculty();
            }
        });

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(FaculityAdminActivity.this, ManageStudentActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addFaculty() {
        String newFacultyName = etNewFaculty.getText().toString().trim();

        if (newFacultyName.isEmpty()) {
            Toast.makeText(FaculityAdminActivity.this, "Please enter faculty name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if faculty already exists
        if (facultyList.contains(newFacultyName)) {
            Toast.makeText(FaculityAdminActivity.this, "Faculty already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading message
        Toast.makeText(this, "Adding faculty...", Toast.LENGTH_SHORT).show();

        // Create Faculty object
        Faculty faculty = new Faculty(newFacultyName);

        // Save to Firebase
        Log.d(TAG, "Attempting to save faculty: " + faculty.getName());
        
        db.collection("faculties")
                .add(faculty)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.firestore.DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Faculty added successfully with ID: " + task.getResult().getId());
                            
                            // Add to local list
                            facultyList.add(newFacultyName);
                            facultyAdapter.notifyDataSetChanged();
                            
                            // Clear input field
                            etNewFaculty.setText("");
                            
                            // Show success message
                            Toast.makeText(FaculityAdminActivity.this, "Faculty added successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Exception exception = task.getException();
                            Log.e(TAG, "Error adding faculty", exception);
                            
                            String errorMessage = "Error adding faculty. ";
                            if (exception != null) {
                                errorMessage += "Reason: " + exception.getMessage();
                                Log.e(TAG, "Exception details: " + exception.toString());
                                
                                // Provide specific error messages based on exception type
                                if (exception.getMessage().contains("permission")) {
                                    errorMessage = "Permission denied. Please check Firebase security rules.";
                                } else if (exception.getMessage().contains("network")) {
                                    errorMessage = "Network error. Please check your internet connection.";
                                } else if (exception.getMessage().contains("quota")) {
                                    errorMessage = "Firebase quota exceeded. Please try again later.";
                                }
                            }
                            
                            Toast.makeText(FaculityAdminActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loadFacultiesFromFirebase() {
        db.collection("faculties")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            facultyList.clear();
                            facultyDataList.clear();
                            
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Faculty faculty = document.toObject(Faculty.class);
                                if (faculty != null) {
                                    faculty.setId(document.getId());
                                    facultyDataList.add(faculty);
                                    facultyList.add(faculty.getName());
                                }
                            }
                            
                            facultyAdapter.notifyDataSetChanged();
                            
                            if (facultyList.isEmpty()) {
                                // Add sample faculties if none exist
                                addSampleFaculties();
                            }
                        } else {
                            Log.e(TAG, "Error getting faculties", task.getException());
                            Toast.makeText(FaculityAdminActivity.this, "Error loading faculties", Toast.LENGTH_SHORT).show();
                            
                            // Add sample faculties as fallback
                            addSampleFaculties();
                        }
                    }
                });
    }

    private void addSampleFaculties() {
        // Add sample faculties if none exist
        String[] sampleFaculties = {"BSIT", "BSCS", "BBA", "BSChem"};
        
        for (String facultyName : sampleFaculties) {
            Faculty faculty = new Faculty(facultyName);
            
            db.collection("faculties")
                    .add(faculty)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sample faculty added: " + facultyName);
                        } else {
                            Log.e(TAG, "Error adding sample faculty: " + facultyName, task.getException());
                        }
                    });
        }
        
        // Add to local list for immediate display
        facultyList.addAll(java.util.Arrays.asList(sampleFaculties));
        facultyAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sample faculties added", Toast.LENGTH_SHORT).show();
    }
}

