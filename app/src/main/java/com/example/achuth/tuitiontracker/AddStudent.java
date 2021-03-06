package com.example.achuth.tuitiontracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achuth.tuitiontracker.Models.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddStudent extends AppCompatActivity  {
    private Button addstudbtn;
    private EditText date,name,fees;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String grade;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private ArrayList<Student>  students;
    private DatabaseReference UserDatabaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Students");
        Bundle extras = getIntent().getExtras();
        grade = extras.getString("Class");

        init();



}

    private void getprevdata() {
        String prevdata = sharedPreferences.getString(grade, null);
        if (prevdata == null) {
            students=new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Student>>() {
            }.getType();
            students = gson.fromJson(prevdata, type);
        }

    }

    private void init() {
        addstudbtn = findViewById(R.id.addstudbtn);
        date=findViewById(R.id.enterdate);
        name=findViewById(R.id.entername);
        fees=findViewById(R.id.enterfees);
        students=new ArrayList<>();
        gson=new Gson();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addstudbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getprevdata();
                int namelen,datelen,feeslen;
                namelen=name.getText().toString().length();
                datelen=date.getText().toString().length();
                feeslen=fees.getText().toString().length();
                if(namelen > 0 && datelen>0 && feeslen >0)
                {
                    Student student=new Student(name.getText().toString(),date.getText().toString(),fees.getText().toString(),grade,false);
                    students.add(student);
                    String uniqueID = UUID.randomUUID().toString();
                    student.setId(uniqueID);
                    editor.putString(grade,gson.toJson(students)).commit();
                    UserDatabaseReference.child(grade).child(uniqueID).setValue(student);
                    Toast.makeText(getApplicationContext(),"Student succesfully added",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}


