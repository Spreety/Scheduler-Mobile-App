package android.melissaeppersonc196.scheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melissaeppersonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.melissaeppersonc196.R.layout.activity_assessment_view;

public class TermViewActivity extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;

    int ID;
    int ID2;
    String title;
    String startDate;
    String endDate;
    int courseID;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    public static int courseCount;
    TermsEntity selectedTerm;
    TextView pickDate;
    Calendar calendarView = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener  selectedDate;
    Long termStartDate;
    Long termEndDate;
    List<TermsEntity> allTerms;
    RecyclerView recyclerView;
    Button addCourseButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton deleteButton;

    /**
     * This sets the text in the term view screen. Either the hint text will display
     * or the selected term text will show.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ID = getIntent().getIntExtra("termID", -1);
        schedulerRepository = new SchedulerRepository(getApplication());
        allTerms = schedulerRepository.getAllTerms();
        for(TermsEntity a:allTerms) {
            if(a.getTermID()==ID)selectedTerm = a;
        }
        ID = getIntent().getIntExtra("termID", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        courseID = getIntent().getIntExtra("courseID", -1);
        ID2 = courseID;
        editTitle = findViewById(R.id.termTitle);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);

        //This hides the Add Course button
        addCourseButton = findViewById(R.id.addCourseButton);
        addCourseButton.setVisibility(View.GONE);

        //This hides the Delete button
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);

        if (ID != -1) {
            editTitle.setText(title);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            //This makes the Add Course button visible
            addCourseButton.setVisibility(View.VISIBLE);
            //This makes the delete button visible
            deleteButton.setVisibility(View.VISIBLE);
        }
        //this displays the filtered courses in the recyclerview
        RecyclerView recyclerView = findViewById(R.id.termCourseListText);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CoursesEntity> filteredCourses = new ArrayList<>();
        for (CoursesEntity a:schedulerRepository.getAllCourses()) {
            if (a.getCourseID() == ID) filteredCourses.add(a);
        }
        courseCount = filteredCourses.size();
        adapter.setCourseText(filteredCourses);
    }

    /**
     * This inflates the option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    /**
     * This sets up the options in the menu and how they respond
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //This takes you back to the main screen.
            case R.id.home:
                Intent backToMain = new Intent(this, MainActivity.class);
                startActivity(backToMain);
                return true;
            //This creates the start date alert option
            case R.id.startAlerts:

                String selectedStartDate = editStartDate.getText().toString();

                Intent startIntent = new Intent(TermViewActivity.this,Receiver.class);
                startIntent.putExtra("key",title + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(TermViewActivity.this,++MainActivity.alertCount,startIntent,0);
                AlarmManager alarmManagerStart = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                String startDateFormat = "MM/dd/yyyy";
                SimpleDateFormat simStartDateFormat = new SimpleDateFormat(startDateFormat, Locale.US);
                Date chosenStartDate = null;

                try {
                    chosenStartDate = simStartDateFormat.parse(selectedStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                termStartDate = chosenStartDate.getTime();
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, termStartDate, startSender);

                return true;

            //This creates the end date alert option
            case R.id.endAlerts:

                String selectedEndDate = editEndDate.getText().toString();

                Intent endIntent = new Intent(TermViewActivity.this,Receiver.class);
                endIntent.putExtra("key",title + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(TermViewActivity.this,++MainActivity.alertCount,endIntent,0);
                AlarmManager alarmManagerEnd = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Date chosenEndDate = null;
                try {
                    chosenEndDate = simDateFormat.parse(selectedEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                termEndDate = chosenEndDate.getTime();
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, termEndDate, endSender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this takes all the info entered in the activity view screen and saves it to the database as a new term
     * @param view
     */
    public void saveTermButton(View view) {
        TermsEntity save;

        if (ID != -1) save = new TermsEntity(ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), courseID);

        else {
            List<TermsEntity> allTerms = schedulerRepository.getAllTerms();
            ID = allTerms.get(allTerms.size() - 1).getTermID();
            save = new TermsEntity(++ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), courseID);
        }

        //This triggers the query to save the new Term
        schedulerRepository.insert(save);

        //This sends a pop-up notification that term was created.
        Toast.makeText(getApplicationContext(), "New Term Created", Toast.LENGTH_LONG).show();

        //This makes the Add Course Button visible
        addCourseButton.setVisibility(View.VISIBLE);

        //This makes the Delete button visible
        deleteButton.setVisibility(View.VISIBLE);
        //This takes you back to the assessment list screen after saving the new assessment
        //Intent intent = new Intent(TermViewActivity.this, TermListActivity.class);
        //startActivity(intent);
    }

    /**
     * This will deletes the selected course unless there are assessments related to it.
     * @param view
     */
    public void deleteTermButton(View view) {
        //this brings up a pop up box asking to confirm delete
        AlertDialog.Builder builder = new AlertDialog.Builder(TermViewActivity.this);
        builder.setMessage("Do you want to delete this term?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //once OK is clicked, term is deleted
                    public void onClick(DialogInterface dialog, int which) {
                        if (courseCount == 0) {
                            schedulerRepository.delete(selectedTerm);
                            Toast.makeText(getApplicationContext(), "Term deleted", Toast.LENGTH_LONG).show();
                            //This takes the user back to the Term list screen once term deleted
                            Intent intent = new Intent(TermViewActivity.this, TermListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Can't delete a term with assigned courses", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                //If use hits cancel instead of OK, it takes them back to termViewActivity
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TermViewActivity.this, TermViewActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void addCourse(View view) {
        Intent intent=new Intent(TermViewActivity.this,CourseViewActivity.class);
        startActivity(intent);
    }
}