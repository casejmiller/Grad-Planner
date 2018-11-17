package com.cmiller.studentscheduler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Term;
import com.cmiller.studentscheduler.R;

import java.util.ArrayList;

public class TermAdapter extends ArrayAdapter<Term> {

    public TermAdapter(@NonNull Context context, ArrayList<Term> term) {
        super(context, R.layout.term_row, term);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.term_row, parent, false);

        Term term = getItem(position);
        TextView termName = customView.findViewById(R.id.termName);
        TextView termDates = customView.findViewById(R.id.termDates);

        String dateStart = term.getStart();
        String dateEnd = term.getEnd();

        termName.setText(term.getTitle());
        termDates.setText(dateStart + " - " + dateEnd);

        return customView;
    }
}
