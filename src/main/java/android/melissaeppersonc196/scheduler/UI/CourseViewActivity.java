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
import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CourseViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static int ID2;
    int ID;
    int termID;
    int courseID;
    public static int currentCourseID;
    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    CoursesEntity selectedCourse;
    public static int assessmentCount;
    public static int noteCount;
    TextView pickDate;
    //TextView pickStartDate;
    //TextView pickEndDate;
    public static String currentCourseTitle;
    Calendar calendarView = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener  selectedDate;
    Long courseStartDate;
    Long courseEndDate;
    List<CoursesEntity> allCourses;
    RecyclerView recyclerView;
    Button addAssessmentButton;
    Button addNoteButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton deleteButton;
    private Spinner statusSpinner;

    private SchedulerRepository schedulerRepository;
    private SchedulerRepository schedulerRepository2;

    /**
     * This sets the text in the course view screen. Either the hint text will display
     * or the selected course text will show.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        //AssessmentViewActivity.ID2 = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ID = getIntent().getIntExtra("courseID",-1);
        schedulerRepository = new SchedulerRepository(getApplication());
        allCourses = schedulerRepository.getAllCourses();
        for(CoursesEntity a:allCourses) {
            if(a.getCourseID()==ID)selectedCourse = a;
        }
        //ID = getIntent().getIntExtra("courseID", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        termID = getIntent().getIntExtra("termID", -1);
        ID2 = termID;
        editTitle = findViewById(R.id.courseName);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        editStatus = findViewById(R.id.courseStatus);
        editInstructorName = findViewById(R.id.instructorName);
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorEmail = findViewById(R.id.instructorEmail);

        //This hides the Add Assessment button
        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        addAssessmentButton.setVisibility(View.GONE);

        //This hides the Add Note button
        addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setVisibility(View.GONE);

        //This hides the Delete button
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);

        if(ID != -1) {
            editTitle.setText(title);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            editStatus.setText(status);
            editInstructorName.setText(instructorName);
            editInstructorPhone.setText(instructorPhone);
            editInstructorEmail.setText(instructorEmail);

            //This displays the Add Assessment button
            addAssessmentButton = findViewById(R.id.addAssessmentButton);
            addAssessmentButton.setVisibility(View.VISIBLE);

            //This displays the Add Note button
            addNoteButton = findViewById(R.id.addNoteButton);
            addNoteButton.setVisibility(View.VISIBLE);

            //This displays the Delete button
            deleteButton = findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.VISIBLE);
        }

        //This gets the filtered assessments
        recyclerView = findViewById(R.id.courseAssessmentListText);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentsEntity> filteredAssessments = new ArrayList<>();
        for (AssessmentsEntity a:schedulerRepository.getAllAssessments()) {
            if (a.getAssessmentID()==ID)filteredAssessments.add(a);
        }
        assessmentCount = filteredAssessments.size();
        adapter.setAssessmentText(filteredAssessments);
        System.out.println(assessmentCount);

        //This gets the filtered notes
        recyclerView = findViewById(R.id.courseNoteListText);
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<NotesEntity> filteredNotes = new ArrayList<>();
        for (NotesEntity a:schedulerRepository.getAllNotes()) {
            if (a.getNoteID()==ID)filteredNotes.add(a);
        }
        noteCount = filteredNotes.size();
        noteAdapter.setNoteText(filteredNotes);

        //This sets up the status spinner
        statusSpinner = findViewById(R.id.course_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerAdapter);
        statusSpinner.setOnItemSelectedListener(this);

        //currentCourseTitle = selectedCourse.getTitle();

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

                Intent startIntent = new Intent(CourseViewActivity.this,Receiver.class);
                startIntent.putExtra("key",title + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(CourseViewActivity.this,++MainActivity.alertCount,startIntent,0);
                AlarmManager alarmManagerStart = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                String startDateFormat = "MM/dd/yyyy";
                SimpleDateFormat simStartDateFormat = new SimpleDateFormat(startDateFormat, Locale.US);
                Date chosenStartDate = null;

                try {
                    chosenStartDate = simStartDateFormat.parse(selectedStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                courseStartDate = chosenStartDate.getTime();
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, courseStartDate, startSender);

                return true;

            //This creates the end date alert option
            case R.id.endAlerts:

                String selectedEndDate = editEndDate.getText().toString();

                Intent endIntent = new Intent(CourseViewActivity.this,Receiver.class);
                endIntent.putExtra("key",title + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseViewActivity.this,++MainActivity.alertCount,endIntent,0);
                AlarmManager alarmManagerEnd = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Date chosenEndDate = null;
                try {
                    chosenEndDate = simDateFormat.parse(selectedEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                courseEndDate = chosenEndDate.getTime();
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, courseEndDate, endSender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addCourse(View view) {
        CoursesEntity a;

        if(ID != -1) {
            a = new CoursesEntity(ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), termID);
        }
        else{
            List<CoursesEntity> allCourses = schedulerRepository.getAllCourses();
            ID = allCourses.get(allCourses.size()-1).getCourseID();
            System.out.println("new Course ID: " + ID);
            a = new CoursesEntity(++ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), termID);
        }
        schedulerRepository.insert(a);
    }
    /**
     * this takes all the info entered in the activity view screen and saves it to the database as a new assessment
     * @param view
     */
    public void saveCourseButton(View view) {
        CoursesEntity save;

        if(ID != -1) {
            save = new CoursesEntity(ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), termID);

            //This sends a pop-up notification that course was updated.
            Toast.makeText(getApplicationContext(), "Course has been updated", Toast.LENGTH_LONG).show();
        }

        else {
            List<CoursesEntity> allCourses = schedulerRepository.getAllCourses();
            System.out.println(allCourses);
            ID = allCourses.get(allCourses.size()-1).getCourseID();
            System.out.println(ID);
            save = new CoursesEntity(++ID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), termID);

            //This sends a pop-up notification that course was created.
            Toast.makeText(getApplicationContext(), "New Course Created", Toast.LENGTH_LONG).show();
        }
        //This runs the query to add the new course
        schedulerRepository.insert(save);

        //This sets displays the Add Assessment Button
        addAssessmentButton.setVisibility(View.VISIBLE);

        //This sets displays the Add Note Button
        addNoteButton.setVisibility(View.VISIBLE);

        //This sets the Delete button
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.VISIBLE);

        //This takes you back to the course list screen after saving the new course
        Intent intent = new Intent(CourseViewActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    /**
     * This will deletes the selected course unless there are assessments related to it.
     * @param view
     */
    public void deleteCourseButton(View view) {
        //this brings up a pop up box asking to confirm delete
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseViewActivity.this);
        builder.setMessage("Do you want to delete this course?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //once OK is clicked, course is deleted
                    public void onClick(DialogInterface dialog, int which) {
                        if (assessmentCount == 0) {
                            schedulerRepository.delete(selectedCourse);
                            Toast.makeText(getApplicationContext(), "Course deleted", Toast.LENGTH_LONG).show();
                            //This takes the user back to the course list screen once course deleted
                            Intent intent = new Intent(CourseViewActivity.this, CourseListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Can't delete a course with assigned assessments", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                //If use hits cancel instead of OK, it takes them back to courseViewActivity
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CourseViewActivity.this, CourseViewActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void addNewAssessment(View view) {
        currentCourseID = selectedCourse.getCourseID();
        System.out.println(currentCourseID);
        Intent intent = new Intent(CourseViewActivity.this, AssessmentViewActivity.class);
        startActivity(intent);
    }

    public void addNewNote(View view) {
        currentCourseID = selectedCourse.getCourseID();
        Intent intent = new Intent(CourseViewActivity.this, NoteActivity.class);
        startActivity(intent);
    }

    /**
     * This sets the selected item from the spinner in the textview
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
