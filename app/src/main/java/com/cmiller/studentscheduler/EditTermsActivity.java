package com.cmiller.studentscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Term;

import java.util.Calendar;


public class EditTermsActivity extends AppCompatActivity {

    private int termID;
    private TextView termStart;
    private TextView termEnd;
    private TextView termTitle;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terms);
        termStart = (TextView) findViewById(R.id.termStart);
        termEnd = (TextView) findViewById(R.id.termEnd);
        termTitle = (TextView) findViewById(R.id.termTitle);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termTitle.setText(extras.getString("editTermTitle"));
            termStart.setText(extras.getString("editTermStart"));
            termEnd.setText(extras.getString("editTermEnd"));
            termID = extras.getInt("editTermID");

            Log.d("one", String.valueOf(termID));
        }

        dbHandler = new DBHandler(this, null, null, 1);

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTermsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateListener,
                        year,month,day);
                dialog.show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month+1 + "/" + day + "/" + year;
                termStart.setText(date);
            }
        };

        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTermsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateListener,
                        year,month,day);
                dialog.show();
            }
        });

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month+1 + "/" + day + "/" + year;
                termEnd.setText(date);
            }
        };

        Log.d("TERM ID", String.valueOf(termID));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                Term term = new Term(
                        termID,
                        termTitle.getText().toString(),
                        termStart.getText().toString(),
                        termEnd.getText().toString()
                );

                if(termID > 0){
                    dbHandler.updateTerm(term);
                } else {
                    dbHandler.addTerm(term);
                }
                startActivity(new Intent(this, TermsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
