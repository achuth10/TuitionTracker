package com.example.achuth.tuitiontracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achuth.tuitiontracker.Models.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewStudent extends AppCompatActivity {
    private TextView name,joindate,fees,days,classes;
    private EditText editText;
    private String grade;
    private int pos;
    String deleteid;
    private Gson gson;
    private ArrayList<Student> students;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button delete,savefees;
    private DatabaseReference UserDatabaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        init();


    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        grade = extras.getString("Class");
        pos = extras.getInt("Student");
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(grade);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        name = findViewById(R.id.studname);
        editText=(EditText)findViewById(R.id.editfees);
        savefees=(Button)findViewById(R.id.savefeesbtn);
        joindate = findViewById(R.id.studstartdate);
        fees = findViewById(R.id.studfees);
        days = findViewById(R.id.studdays);
        classes = findViewById(R.id.studclasses);
        students = new ArrayList<>();
        gson = new Gson();
        checkforprevdata();
        delete = findViewById(R.id.deletestud);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Long press the button to delete",Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                students.remove(pos);
                editor.putString(grade,gson.toJson(students)).apply();
                UserDatabaseReference.child(deleteid).removeValue();
                startActivity(new Intent(getApplication(),MainActivity.class));
                return true;
            }
        });
        savefees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                students.get(pos).setFees(s);
                editor.putString(grade,gson.toJson(students)).apply();
                Toast.makeText(getApplicationContext(),"Updated succesfully" ,Toast.LENGTH_SHORT).show();
                recreate();
            }
        });
    }

    private void checkforprevdata() {
        String prevdata = sharedPreferences.getString(grade, null);
        if (prevdata == null) {
            Toast.makeText(getApplicationContext(), "No student info", Toast.LENGTH_SHORT).show();
        } else {
            Type type = new TypeToken<List<Student>>() {
            }.getType();
            students = gson.fromJson(prevdata, type);
            Student student = students.get(pos);
            deleteid= student.getId();
            System.out.println(deleteid);
            name.setText(student.getName());
            joindate.setText("Joined : " +student.getStartdate());
            fees.setText("Fees : " + student.getFees());
            days.setText("Days attended : " + student.getDays());
            classes.setText("Classes attended " + student.getNum_classes());
    }}
}
