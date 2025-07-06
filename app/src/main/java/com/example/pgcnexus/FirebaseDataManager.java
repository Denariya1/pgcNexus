package com.example.pgcnexus;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataManager {
    private static final String TAG = "FirebaseDataManager";
    private FirebaseFirestore db;

    public FirebaseDataManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void addSampleTeachers() {
        List<Teacher> sampleTeachers = new ArrayList<>();
        
        sampleTeachers.add(new Teacher(
            "Prof. Jawad Mustafa",
            "jawad@pgc.edu.pk",
            "Computer Science",
            "0321-1234567",
            "MS IT",
            "Android, Java"
        ));
        
        sampleTeachers.add(new Teacher(
            "Prof. Salma Yousaf",
            "salma@pgc.edu.pk",
            "IT",
            "0315-6547890",
            "PhD CS",
            "Python, Database"
        ));
        
        sampleTeachers.add(new Teacher(
            "Mr. Ali Javed",
            "ali@pgc.edu.pk",
            "SE",
            "0300-9998877",
            "BS SE",
            "OOP, Data Structures"
        ));

        for (Teacher teacher : sampleTeachers) {
            db.collection("teachers")
                    .add(teacher)
                    .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.firestore.DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Sample teacher added successfully");
                            } else {
                                Log.e(TAG, "Error adding sample teacher", task.getException());
                            }
                        }
                    });
        }
    }

    public void clearAllTeachers() {
        db.collection("teachers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (com.google.firebase.firestore.QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                        Log.d(TAG, "All teachers cleared");
                    } else {
                        Log.e(TAG, "Error clearing teachers", task.getException());
                    }
                });
    }
} 