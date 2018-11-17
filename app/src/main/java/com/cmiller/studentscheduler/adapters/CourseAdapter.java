package com.cmiller.studentscheduler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Course;
import com.cmiller.studentscheduler.R;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<Course> {


    public CourseAdapter(@NonNull Context context, ArrayList<Course> course) {
        super(context, R.layout.course_row, course);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.course_row, parent, false);

        Course course = getItem(position);
        TextView courseName = customView.findViewById(R.id.courseName);

        courseName.setText(course.getTitle());

        return customView;
    }
}
