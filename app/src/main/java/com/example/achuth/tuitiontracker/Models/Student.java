package com.example.achuth.tuitiontracker.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Student {
    private String name,startdate,grade,fees;
    private Boolean present;
    private int num_classes;
    private ArrayList <String> days;
    private String id;

    Student(String Name,Boolean present)
{
    this.name=Name;
    this.present=present;
}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student(String Name, String startDate, String fees, String grade, Boolean present) {
        this.name=Name;
        this.startdate=startDate;
        this.fees=fees;
        this.grade=grade;
        this.present=present;
        num_classes=0;
        days=new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public void setNum_classes(int num_classes) {
        this.num_classes = num_classes;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public int getNum_classes() {
        return num_classes;
    }


    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
