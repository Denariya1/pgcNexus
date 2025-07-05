package com.example.pgcnexus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FaculityAdminActivity extends AppCompatActivity {

    private EditText etNewFaculty;
    private Button btnAddFaculty;
    private ListView listFaculties;

    private ArrayList<String> facultyList;
    private ArrayAdapter<String> facultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculity_admin);

        etNewFaculty = findViewById(R.id.et_new_faculty);
        btnAddFaculty = findViewById(R.id.btn_add_faculty);
        listFaculties = findViewById(R.id.list_faculties);

        facultyList = new ArrayList<>();
        facultyList.add("BSIT");
        facultyList.add("BSCS");
        facultyList.add("BBA");
        facultyList.add("BSChem");

        facultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, facultyList);
        listFaculties.setAdapter(facultyAdapter);

        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFaculty = etNewFaculty.getText().toString().trim();

                if (newFaculty.isEmpty()) {
                    Toast.makeText(FaculityAdminActivity.this, "Please enter faculty name", Toast.LENGTH_SHORT).show();
                } else if (facultyList.contains(newFaculty)) {
                    Toast.makeText(FaculityAdminActivity.this, "Faculty already exists", Toast.LENGTH_SHORT).show();
                } else {
                    facultyList.add(newFaculty);
                    facultyAdapter.notifyDataSetChanged();
                    etNewFaculty.setText("");
                    Toast.makeText(FaculityAdminActivity.this, "Faculty added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // You can also implement long click for deletion later
    }
}

