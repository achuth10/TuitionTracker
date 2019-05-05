package com.example.achuth.tuitiontracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button submit;
    private ListView listView;
    private Spinner classes;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Gson gson;
    private ArrayList<Student> students;
    private ArrayList<String> classeslist;
    private StudentAdapter adapter;
    private String pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        intialise();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addstudent:
                Intent i = new Intent(this, AddStudent.class);
                i.putExtra("Class",pos);
                startActivity(i);
                return true;
            case R.id.refresh:
                refresh();
                return true;
            case R.id.viewstud:
                startActivity(new Intent(this,ViewStudents.class));
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    private void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void checkforprevdata() {
        String prevdata = sharedPreferences.getString(pos, null);
        if (prevdata == null) {
            Toast.makeText(getApplicationContext(), "No student info", Toast.LENGTH_SHORT).show();
            students.clear();
            adapter.notifyDataSetChanged();
        } else {
            Type type = new TypeToken<List<Student>>() {
            }.getType();
            students = gson.fromJson(prevdata, type);
            addStudents();
        }
    }

    private void addStudents() {
        adapter = new StudentAdapter(students, getApplicationContext());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = students.get(position);
                student.setPresent(!student.getPresent());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void intialise() {
        submit = (Button) findViewById(R.id.submit);
        listView = (ListView) findViewById(R.id.list);
        classes = (Spinner) findViewById(R.id.classes);
        students = new ArrayList<>();
        gson = new Gson();
        classeslist = new ArrayList<>();
        classeslist.add("Grade 10");
        classeslist.add("Grade 11");
        classeslist.add("Grade 12");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classeslist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(adapter);

            classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos =classeslist.get(classes.getSelectedItemPosition());
                    checkforprevdata();
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<students.size();i++)
                    {
                        if(students.get(i).getPresent())
                        {
                            students.get(i).setNum_classes(students.get(i).getNum_classes()+1);
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c);
                            students.get(i).getDays().add(formattedDate);
                        }
                    }

                    for(int i=0;i<students.size();i++) {
                        System.out.println(students.get(i).getDays());
                        students.get(i).setPresent(false);
                    }
                    editor.putString(pos,gson.toJson(students)).commit();
                    Toast.makeText(getApplicationContext(),"Attendance marked",Toast.LENGTH_SHORT).show();
                }
            });


    }
}
