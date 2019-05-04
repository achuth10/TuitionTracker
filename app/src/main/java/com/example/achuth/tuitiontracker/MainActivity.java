package com.example.achuth.tuitiontracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button present;
    private ListView listView;
    private Spinner classes;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Gson gson;
    private ArrayList <Student> students;
    private  ArrayList <String> classeslist;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);;
        editor = sharedPreferences.edit();
        intialise();
        checkforprevdata();





    }

    private void checkforprevdata() {
        String prevdata= sharedPreferences.getString("Students",null);
        if(prevdata==null)
        {
            System.out.println("No prev data");
        }
        else
        {
            Type type = new TypeToken<List<Student>>() {}.getType();
            students = gson.fromJson(prevdata,type);
            addStudents();
        }

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student =students.get(position);
                student.setPresent(!student.getPresent());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addStudents() {
        adapter = new StudentAdapter(students, getApplicationContext());
        adapter.notifyDataSetChanged();

    }

    private void intialise() {
        present=(Button)findViewById(R.id.present);
        listView = (ListView)findViewById(R.id.list);
        classes=(Spinner)findViewById(R.id.classes);
        students=new ArrayList<>();
        gson=new Gson();
        classeslist=new ArrayList<>();
        classeslist.add("Grade 10");
        classeslist.add("Grade 11");
        classeslist.add("Grade 12");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classeslist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(adapter);

    }
}
