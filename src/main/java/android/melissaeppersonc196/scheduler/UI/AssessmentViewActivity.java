package android.melissaeppersonc196.scheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melissaeppersonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.melissaeppersonc196.R.layout.activity_assessment_view;

public class AssessmentViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SchedulerRepository schedulerRepository;

    static int ID2;
    int ID;
    String title;
    String type;
    String endDate;
    int courseID;
    EditText editTitle;
    EditText editType;
    EditText editEndDate;
    TextView pickDate;
    String assessmentTypeSpinner;
    Calendar calendarView = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener  selectedDate;
    Long date;
    List<AssessmentsEntity> allAssessments;
    AssessmentsEntity selectedAssessment;
    private Spinner typeSpinner;

    /**
     * This sets the text in the assessment view screen. Either the hint text will display
     * or the selected assessment text will show.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_assessment_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ID = getIntent().getIntExtra("assessmentID", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        endDate = getIntent().getStringExtra("endDate");
        courseID = getIntent().getIntExtra("courseID", -1);
        ID2 = courseID;
        schedulerRepository = new SchedulerRepository(getApplication());
        allAssessments = schedulerRepository.getAllAssessments();
        //This gets the selected assessment
        for(AssessmentsEntity p:allAssessments) {
            if(p.getAssessmentID() == ID)selectedAssessment = p;
        }
        editTitle = findViewById(R.id.assessmentName);
        typeSpinner = findViewById(R.id.type_spinner);
        editEndDate = findViewById(R.id.assessmentEndDate);

        //This sets the text in the assessment detail screen to match what is clicked on
        if (ID != -1) {
            editTitle.setText(title);
            typeSpinner.setSelection(getIndex(typeSpinner, type));
            //typeSpinner.setSelection(Integer.parseInt(type));

            //editType.setText(type);
            editEndDate.setText(endDate);
            System.out.println(ID);
        }
        schedulerRepository = new SchedulerRepository(getApplication());
        allAssessments = schedulerRepository.getAllAssessments();
      /*  pickDate = findViewById(R.id.selectDate);
        selectedDate = (view, year, month, dayOfMonth) -> {
            calendarView.set(Calendar.YEAR, year);
            calendarView.set(Calendar.MONTH, month);
            calendarView.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String dateFormat = "MM/dd/yyyy";
            SimpleDateFormat simDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

            labelUpdate();
        };

        pickDate.setOnClickListener(v -> new DatePickerDialog(AssessmentViewActivity.this, selectedDate, calendarView
                .get(Calendar.YEAR), calendarView.get(Calendar.MONTH),
                calendarView.get(Calendar.DAY_OF_MONTH)).show());*/

        //This sets up the status spinner
        typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

    }

    //This is a way to get the position of the spinner
    private int getIndex(Spinner typeSpinner, String myString){
        int index = 0;
        for (int i = 0;
             i < typeSpinner.getCount(); i++){
            if (typeSpinner.getItemAtPosition(i).toString().trim().equals(myString.trim())) {
                return i;
            }
            System.out.println(i);
        }

        return index;
    }

    /**
     * This inflates the option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
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

            case R.id.alerts:

                Intent intent = new Intent(AssessmentViewActivity.this,Receiver.class);
                intent.putExtra("key","Assessment: " + title + " is scheduled for today.");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentViewActivity.this,++MainActivity.alertCount,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String selectedDate = editEndDate.getText().toString();
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Date chosenDate = null;
                try {
                    chosenDate = simDateFormat.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                date = chosenDate.getTime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * this takes all the info entered in the activity view screen and saves it to the database as a new assessment
     * @param view
     */
    public void saveAssessmentButton(View view) {
        AssessmentsEntity save;
        String assessmentType = assessmentTypeSpinner;

        if (ID != -1) save = new AssessmentsEntity(ID, editTitle.getText().toString(), assessmentType, editEndDate.getText().toString(), courseID);

        else {
            List<AssessmentsEntity> allAssessments = schedulerRepository.getAllAssessments();
            ID = allAssessments.get(allAssessments.size() - 1).getAssessmentID();
            save = new AssessmentsEntity(++ID, editTitle.getText().toString(), assessmentType, editEndDate.getText().toString(), courseID);
        }

        schedulerRepository.insert(save);

        //This takes you back to the assessment list screen after saving the new assessment
        Intent intent = new Intent(AssessmentViewActivity.this, AssessmentListActivity.class);
        startActivity(intent);
    }

    /**
     * This will delete the selected assessment
     * @param view
     */
    public void deleteAssessmentButton(View view) {
        //this brings up a pop up box asking to confirm delete
        AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentViewActivity.this);
        builder.setMessage("Do you want to delete this assessment?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //once OK is clicked, assessment is deleted
                    public void onClick(DialogInterface dialog, int which) {
                        schedulerRepository.delete(selectedAssessment);
                        Toast.makeText(getApplicationContext(), "Assessment deleted", Toast.LENGTH_LONG).show();
                        //This takes the user back to the assessment list screen once assessment deleted
                        Intent intent = new Intent(AssessmentViewActivity.this, AssessmentListActivity.class);
                        startActivity(intent);
                    }
                })
                //If use hits cancel instead of OK, it takes them back to assessmentViewActivity
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AssessmentViewActivity.this, AssessmentViewActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * This triggers a response to an item being selected from the type spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //this saves the selected item from the spinner
        assessmentTypeSpinner = (typeSpinner.getSelectedItem().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}