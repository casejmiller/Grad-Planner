package com.cmiller.studentscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Assessment;

import java.util.Calendar;

public class EditAssessmentActivity extends AppCompatActivity {

    private int assessID;
    private String spinnerStart;
    private TextView assessmentTitle;
    private TextView assessmentGoal;
    private Spinner assessmentType;
    private DatePickerDialog.OnDateSetListener goalDateListener;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        assessmentTitle = (TextView) findViewById(R.id.assessTitleDet);
        assessmentGoal = (TextView) findViewById(R.id.goalDate);
        assessmentType = (Spinner) findViewById(R.id.AssessmentType);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            assessmentTitle.setText(extras.getString("editAssessTitle"));
            assessmentGoal.setText(extras.getString("editAssessGoal"));
            assessID = extras.getInt("editAssessID");

            spinnerStart = extras.getString("editAssessType");

            Log.d("one", String.valueOf(assessID));
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentType.setAdapter(adapter);

        if (spinnerStart != null) {
            int spinnerPosition = adapter.getPosition(spinnerStart);
            assessmentType.setSelection(spinnerPosition);
        }

        dbHandler = new DBHandler(this, null, null, 1);



        assessmentGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditAssessmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        goalDateListener,
                        year, month, day);
                dialog.show();
            }
        });

        goalDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                assessmentGoal.setText(date);
            }
        };

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
                Assessment assessment = new Assessment(
                        assessID,
                        assessmentTitle.getText().toString(),
                        assessmentType.getSelectedItem().toString(),
                        assessmentGoal.getText().toString()
                );

                if(assessID > 0){
                    dbHandler.updateAssessment(assessment);
                } else {
                    dbHandler.addAssessment(assessment);
                }
                startActivity(new Intent(this, AssessmentsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
