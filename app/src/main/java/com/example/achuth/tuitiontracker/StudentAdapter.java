package com.example.achuth.tuitiontracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.achuth.tuitiontracker.Models.Student;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter {
    private ArrayList<Student> students;
    Context context;
    private static class ViewHolder{
        TextView txtName;
        CheckBox checkBox;
    }
    public StudentAdapter (ArrayList <Student> data , Context context)
    {
        super(context,R.layout.rowlayout,data);
        students=data;
        this.context=context;

    }
    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.present);
            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Student item = getItem(position);

        viewHolder.txtName.setText(item.getName());
        viewHolder.checkBox.setChecked(item.getPresent());

        return result;
    }
}
