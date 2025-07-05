package com.example.pgcnexus;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
public class EmailActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;

    private EditText editTextFrom, editTextSubject, editTextMessage;
    private Button buttonChooseFile, buttonSend;
    private TextView textSelectedFile, textTotalSent;
    private Uri selectedFileUri = null;
    private int sentMailCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        initializeViews();
        setupClickListeners();
    }
    private void initializeViews() {
        editTextFrom = findViewById(R.id.editText_from);
        editTextSubject = findViewById(R.id.editText_subject);
        editTextMessage = findViewById(R.id.editText_message);
        buttonChooseFile = findViewById(R.id.button_choose_file);
        buttonSend = findViewById(R.id.button_send);
        textSelectedFile = findViewById(R.id.text_selected_file);
        textTotalSent = findViewById(R.id.text_total_sent);
    }
    private void setupClickListeners() {
        buttonChooseFile.setOnClickListener(v -> openFileChooser());
        buttonSend.setOnClickListener(v -> {
            if (validateInputs()) {
                sendEmail();
            }
        });
    }
    private boolean validateInputs() {
        boolean isValid = true;

        if (editTextFrom.getText().toString().trim().isEmpty()) {
            editTextFrom.setError("Email is required");
            isValid = false;
        }
        if (editTextSubject.getText().toString().trim().isEmpty()) {
            editTextSubject.setError("Subject is required");
            isValid = false;
        }
        if (editTextMessage.getText().toString().trim().isEmpty()) {
            editTextMessage.setError("Message is required");
            isValid = false;
        }
        return isValid;
    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) return;
        if (requestCode == PICK_FILE_REQUEST) {
            handleFileSelection(data.getData());
        }
    }
    private void handleFileSelection(Uri uri) {
        selectedFileUri = uri;
        String fileName = getFileNameFromUri(selectedFileUri);
        textSelectedFile.setText("Selected: " + fileName);
        textSelectedFile.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    }
    private String getFileNameFromUri(Uri uri) {
        String displayName = null;
        try (var cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("_display_name");
                if (nameIndex != -1) {
                    displayName = cursor.getString(nameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (displayName == null) {
            String mimeType = getContentResolver().getType(uri);
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            displayName = "attachment." + (extension != null ? extension : "file");
        }
        return displayName;
    }
    private void sendEmail() {
        String from = editTextFrom.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();
        String[] recipients = {"student1@example.com", "student2@example.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{from});
        if (selectedFileUri != null) {
            emailIntent.putParcelableArrayListExtra(
                    Intent.EXTRA_STREAM,
                    new ArrayList<>(Collections.singletonList(selectedFileUri))
            );
        }
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            sentMailCount++;
            textTotalSent.setText(String.format("Total Sent Mails: %d", sentMailCount));
            Toast.makeText(this, "Email intent launched!", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client installed", Toast.LENGTH_SHORT).show();
        }
    }
}
