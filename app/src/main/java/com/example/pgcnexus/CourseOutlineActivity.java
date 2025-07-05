package com.example.pgcnexus;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourseOutlineActivity extends AppCompatActivity {

    private ImageView courseImageView;  // ImageView to show the zoomable image
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;  // Initial zoom scale

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enabling Edge to Edge mode
        setContentView(R.layout.activity_course_outline);

        // Find the ImageView that will display the zoomable image
        courseImageView = findViewById(R.id.courseOutlineImage);

        // Initialize the ScaleGestureDetector for zooming
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Set the image for the ImageView (example image)
        courseImageView.setImageResource(R.drawable.courseoutline);

        // Apply system window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass touch events to the ScaleGestureDetector for pinch-to-zoom detection
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // ScaleListener to handle pinch-to-zoom gestures
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Get the scale factor and apply it to the image
            scaleFactor *= detector.getScaleFactor();

            // Restrict the scale factor to be between 0.1 and 5.0 (min and max zoom levels)
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            // Apply the scaling to the ImageView
            courseImageView.setScaleX(scaleFactor);
            courseImageView.setScaleY(scaleFactor);
            return true;
        }
    }
}
