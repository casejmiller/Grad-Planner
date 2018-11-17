package com.cmiller.studentscheduler;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cmiller.studentscheduler.receivers.AlarmReceiver;

import java.util.Calendar;

public class AssessmentDetailedActivity extends AppCompatActivity {


    private int assessID;
    private TextView title;
    private TextView type;
    private TextView goal;
    private DBHandler dbHandler;

    private CheckBox goalAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detailed);
        dbHandler = new DBHandler(this, null, null, 1);

        title = (TextView) findViewById(R.id.assessTitleDet);
        type = (TextView) findViewById(R.id.assessTypeDet);
        goal = (TextView) findViewById(R.id.assessGoalDet);

        goalAlert = (CheckBox) findViewById(R.id.goalAlert);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString("assessTitle"));
            type.setText(extras.getString("assessType"));
            goal.setText(extras.getString("assessGoal"));
            assessID = extras.getInt("assessID");

            Log.d("one", String.valueOf(assessID));
        }


        setChecker();


        goalAlert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if(goalAlert.isChecked()){
                    editor.putString("goalAlert"+String.valueOf(assessID), "ON");
                    editor.commit();
                    grabTime();
                    Log.d("goalAlert", "CHECKED");
                }else{
                    editor.remove("goalAlert"+String.valueOf(assessID));
                    editor.commit();
                    cancelAlarm();
                    Log.d("goalAlert","UNCHECK");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEdit:
                optionEdit();
                return true;
            case R.id.menuDelete:
                optionDelete();
                return true;
            case R.id.menuHome:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menuTerms:
                startActivity(new Intent(this, TermsActivity.class));
                return true;
            case R.id.menuCourses:
                startActivity(new Intent(this, CoursesActivity.class));
                return true;
            case R.id.menuAssessments:
                startActivity(new Intent(this, AssessmentsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void optionEdit(){

        Intent intent = new Intent(getBaseContext(), EditAssessmentActivity.class);

        intent.putExtra("editAssessID", assessID);
        intent.putExtra("editAssessTitle", title.getText().toString());
        intent.putExtra("editAssessType", type.getText().toString());
        intent.putExtra("editAssessGoal", goal.getText().toString());

        startActivity(intent);
    }

    public void optionDelete(){
        dbHandler.deleteAssessment(assessID);
        startActivity(new Intent(this, AssessmentsActivity.class));
    }

    public void setChecker(){
        SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);

        String key = "goalAlert"+String.valueOf(assessID);
        String check = sp.getString(key, "");
        if(check.equals("ON")){
            goalAlert.setChecked(true);
        } else {
            goalAlert.setChecked(false);
        }
    }


    public void grabTime() {
        String goalDate = goal.getText().toString();
        String str[] = goalDate.split("/");
        int month = Integer.parseInt(str[0]);
        int day = Integer.parseInt(str[1]);
        int year = Integer.parseInt(str[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(
                year,
                month-1,
                day,
                8,00
        );
        Log.d("DAY", String.valueOf(day));
        Log.d("MONTH", String.valueOf(month));
        Log.d("YEAR", String.valueOf(year));
        Log.d("START", "START ALARM");
        setAlarm(cal.getTimeInMillis());

    }

    private void setAlarm(long timeInMillis) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        String alarmCode = "1";

        intent.putExtra("TITLE",  title.getText().toString());
        intent.putExtra("CODE", alarmCode);

        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(assessID));
        Log.d("UNIQUE INT", String.valueOf(uniqueInt));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC, timeInMillis, pendingIntent);

        String text = "You have set an alert for " + goal.getText().toString() + " for the " +
                title.getText().toString() + " assessment";

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }

    private void cancelAlarm(){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        String alarmCode = "1";
        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(assessID));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pendingIntent);

        String text = "You have canceled an alert for the " + title.getText().toString() +
                " assessment";

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
