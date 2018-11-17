package com.cmiller.studentscheduler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Mentor;
import com.cmiller.studentscheduler.R;

import java.util.ArrayList;

public class MentorAdapter extends ArrayAdapter<Mentor> {

    public MentorAdapter(@NonNull Context context, ArrayList<Mentor> mentor) {
        super(context, R.layout.term_row, mentor);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.mentor_row, parent, false);

        Mentor mentor = getItem(position);
        TextView mentorName = customView.findViewById(R.id.lvMentorName);
        TextView mentorPhone = customView.findViewById(R.id.lvMentorPhone);
        TextView mentorEmail = customView.findViewById(R.id.lvMentorEmail);


        mentorName.setText(mentor.getMentorName());
        mentorPhone.setText(mentor.getMentorPhone());
        mentorEmail.setText(mentor.getMentorEmail());

        return customView;
    }

}
