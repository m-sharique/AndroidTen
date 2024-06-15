package com.example.ten;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText editTextId = findViewById(R.id.editTextId);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextDuration = findViewById(R.id.editTextDuration);
        Button buttonInsert = findViewById(R.id.buttonInsert);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonViewAll = findViewById(R.id.buttonViewAll);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editTextId.getText().toString());
                String name = editTextName.getText().toString();
                String duration = editTextDuration.getText().toString();
                dbHelper.insertCourse(id, name, duration);
                showAlert("Success", "Course Inserted");
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editTextId.getText().toString());
                String name = editTextName.getText().toString();
                String duration = editTextDuration.getText().toString();
                dbHelper.updateCourse(id, name, duration);
                showAlert("Success", "Course Updated");
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editTextId.getText().toString());
                dbHelper.deleteCourse(id);
                showAlert("Success", "Course Deleted");
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getAllCourses();
                if (cursor.getCount() == 0) {
                    showAlert("Error", "No Courses Found");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (cursor.moveToNext()) {
                    buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
                    buffer.append("Name: ").append(cursor.getString(1)).append("\n");
                    buffer.append("Duration: ").append(cursor.getString(2)).append("\n\n");
                }
                showAlert("Courses", buffer.toString());
            }
        });
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrudActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
