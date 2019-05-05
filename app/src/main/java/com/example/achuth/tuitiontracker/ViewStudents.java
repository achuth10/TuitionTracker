package com.example.achuth.tuitiontracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewStudents extends AppCompatActivity {
    private ListView viewstudlist;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner spinner;
    private ArrayList<String> classeslist;
    private String pos;
    private Gson gson;
    private ArrayList<Student> students;
    private ArrayList<String> studentnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        init();

    }

    private void init() {
        viewstudlist = (ListView) findViewById(R.id.viewstudlist);
        spinner = (Spinner) findViewById(R.id.viewstudspin);
        students = new ArrayList<>();
        gson = new Gson();
        classeslist = new ArrayList<>();
        studentnames=new ArrayList<>();
        classeslist.add("Grade 10");
        classeslist.add("Grade 11");
        classeslist.add("Grade 12");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classeslist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = classeslist.get(spinner.getSelectedItemPosition());
                checkforprevdata();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void checkforprevdata() {
        String prevdata = sharedPreferences.getString(pos, null);
        if (prevdata == null) {
            Toast.makeText(getApplicationContext(), "No student info", Toast.LENGTH_SHORT).show();
            studentnames.clear();
        } else {
            Type type = new TypeToken<List<Student>>() {
            }.getType();
            students = gson.fromJson(prevdata, type);
            studentnames.clear();
            for(int i=0;i<students.size();i++)
            {
                studentnames.add(students.get(i).getName());
            }
            addStudents();
        }
    }
    private void addStudents() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, studentnames);
        adapter.notifyDataSetChanged();
        viewstudlist.setAdapter(adapter);
        viewstudlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}

