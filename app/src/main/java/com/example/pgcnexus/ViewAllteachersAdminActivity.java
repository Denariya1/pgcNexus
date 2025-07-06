package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllteachersAdminActivity extends AppCompatActivity {

    private static final String TAG = "ViewAllTeachersAdmin";
    private ImageButton backButton;
    private LinearLayout teacherListContainer;
    private FirebaseFirestore db;
    private List<Teacher> teachersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_allteachers_admin);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
        teachersList = new ArrayList<>();

        // Initialize views
        initializeViews();
        setupClickListeners();
        
        // Load teachers from Firebase
        loadTeachersFromFirebase();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.backButton);
        teacherListContainer = findViewById(R.id.teacherListContainer);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllteachersAdminActivity.this, ManageTeacherActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                            Toast.makeText(ViewAllteachersAdminActivity.this, "Error loading teachers", Toast.LENGTH_SHORT).show();
                            displayEmptyState();
                        }
                    }
                });
    }

    private void displayTeachers() {
        // Clear existing views
        teacherListContainer.removeAllViews();

        if (teachersList.isEmpty()) {
            displayEmptyState();
            return;
        }

        for (Teacher teacher : teachersList) {
            CardView teacherCard = createTeacherCard(teacher);
            teacherListContainer.addView(teacherCard);
        }
    }

    private CardView createTeacherCard(Teacher teacher) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 12);
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(8);
        cardView.setCardElevation(4);

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.HORIZONTAL);
        cardContent.setPadding(16, 16, 16, 16);
        cardContent.setGravity(android.view.Gravity.CENTER_VERTICAL);

        // Profile image
        android.widget.ImageView profileImage = new android.widget.ImageView(this);
        profileImage.setImageResource(R.drawable.profile_button);
        profileImage.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        profileImage.setContentDescription("Profile image of " + teacher.getFullName());
        profileImage.setPadding(0, 0, 16, 0);

        // Teacher name
        TextView nameText = new TextView(this);
        nameText.setText(teacher.getFullName());
        nameText.setTextSize(16);
        nameText.setTypeface(null, android.graphics.Typeface.BOLD);
        nameText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        cardContent.addView(profileImage);
        cardContent.addView(nameText);
        cardView.addView(cardContent);

        return cardView;
    }

    private void displayEmptyState() {
        // Clear existing views
        teacherListContainer.removeAllViews();

        // Create empty state message
        TextView emptyText = new TextView(this);
        emptyText.setText("No teachers found");
        emptyText.setTextSize(16);
        emptyText.setGravity(android.view.Gravity.CENTER);
        emptyText.setPadding(16, 32, 16, 32);
        emptyText.setTextColor(0xFF666666);

        teacherListContainer.addView(emptyText);
    }
}
