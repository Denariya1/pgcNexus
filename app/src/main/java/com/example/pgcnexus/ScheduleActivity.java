package com.example.pgcnexus;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
public class ScheduleActivity extends AppCompatActivity {
    private LinearLayout mondayContent, tuesdayContent, wednesdayContent, thursdayContent, fridayContent;
    private ImageView mondayArrow, tuesdayArrow, wednesdayArrow, thursdayArrow, fridayArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mondayContent = findViewById(R.id.monday_content);
        mondayArrow = findViewById(R.id.monday_arrow);
        mondayArrow.setOnClickListener(view -> toggleVisibility(mondayContent));
        tuesdayContent = findViewById(R.id.tuesday_content);
        tuesdayArrow = findViewById(R.id.tuesday_arrow);
        tuesdayArrow.setOnClickListener(view -> toggleVisibility(tuesdayContent));
        wednesdayContent = findViewById(R.id.wednesday_content);
        wednesdayArrow = findViewById(R.id.wednesday_arrow);
        wednesdayArrow.setOnClickListener(view -> toggleVisibility(wednesdayContent));
        thursdayContent = findViewById(R.id.thursday_content);
        thursdayArrow = findViewById(R.id.thursday_arrow);
        thursdayArrow.setOnClickListener(view -> toggleVisibility(thursdayContent));
        fridayContent = findViewById(R.id.friday_content);
        fridayArrow = findViewById(R.id.friday_arrow);
        fridayArrow.setOnClickListener(view -> toggleVisibility(fridayContent));
    }
    private void toggleVisibility(LinearLayout layout) {
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }
}
