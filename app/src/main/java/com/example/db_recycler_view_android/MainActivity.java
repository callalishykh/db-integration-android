package com.example.db_recycler_view_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonAdd;
    EditText editName, editAge;
    Switch switchIsActive;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    StudentModel studentModel;
    List<StudentModel> studentsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        switchIsActive = findViewById(R.id.switchStudent);
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        studentsList = dbHelper.getAllStudents();

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new myRecyclerViewAdapter(MainActivity.this,studentsList) ;
        recyclerView.setAdapter(adapter);
    }

    public void add(View view) {
        try {
            studentModel = new StudentModel(editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked(),0);

        }
        catch (Exception e){
        }
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        dbHelper.addStudent(studentModel);
        studentsList = dbHelper.getAllStudents();
        adapter = new myRecyclerViewAdapter(MainActivity.this,studentsList) ;
        recyclerView.setAdapter(adapter);

        editName.setText("");
        editAge.setText("");
    }
}