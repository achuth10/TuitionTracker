package com.example.achuth.tuitiontracker;

import java.util.ArrayList;
import java.util.Date;

public class Student {
    private String Name;
    private Boolean present;
    private int num_classes;
    private ArrayList <Date> days;
    Student(String Name,Boolean present)
{
    this.Name=Name;
    this.present=present;
}
    public String getName() {
        return Name;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
