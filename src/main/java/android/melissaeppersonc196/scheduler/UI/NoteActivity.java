package android.melissaeppersonc196.scheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melissaeppersonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.melissaeppersonc196.R.layout.activity_add_note;
import static com.example.melissaeppersonc196.R.layout.activity_assessment_view;

public class NoteActivity extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;

    int ID;
    int courseID;
    int selectedCourseID;
    String note;
    EditText editNote;
    TextView pickDate;
    String courseTitle;
    public static int alertCount;
    Calendar calendarView = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener  selectedDate;
    Long date;
    List<NotesEntity> allNotes;
    NotesEntity selectedNote;

    /**
     * This sets the text in the note view screen. Either the hint text will display
     * or the selected note text will show.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ID = getIntent().getIntExtra("noteID", -1);
        note = getIntent().getStringExtra("note");
        courseID = getIntent().getIntExtra("courseID", -1);
        schedulerRepository = new SchedulerRepository(getApplication());
        allNotes = schedulerRepository.getAllNotes();
        for (NotesEntity p : allNotes) {
            if (p.getNoteID() == ID) selectedNote = p;
            //RecyclerView recyclerView = findViewById(R.id.courseListText);
        }
        editNote = findViewById(R.id.note);

            //This sets the text in the assessment detail screen to match what is clicked on
            if (ID != -1) {
                editNote.setText(note);
            }
    }

    /**
     * This inflates the option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
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

            //This is the menu item for creating notifications
            case R.id.alerts:
                Intent intent=new Intent(NoteActivity.this,Receiver.class);
                intent.putExtra("key","This is a short message");
                PendingIntent sender = PendingIntent.getBroadcast(NoteActivity.this,++alertCount,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String selectedDate = pickDate.getText().toString();
                String dateFormat = "MM/dd/yyyy"; //In which you need put here
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

            //this sets up the menu option to share the notes
            case R.id.shareNote:
                courseTitle = CourseViewActivity.currentCourseTitle;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                //This is the main text
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseTitle + " Notes: " + note);
                //This is the title
                sendIntent.putExtra(Intent.EXTRA_TITLE, courseTitle + " Notes");
                sendIntent.setType("text/plain");

                Intent shareNoteIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareNoteIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
        /**
         * this takes all the info entered in the activity view screen and saves it to the database as a new assessment
         * @param view
         */
    public void saveNoteButton(View view) {
        NotesEntity save;
        selectedCourseID = CourseViewActivity.currentCourseID;

            List<NotesEntity> allNotes = schedulerRepository.getAllNotes();
            ID = allNotes.get(allNotes.size()-1).getNoteID();
            save = new NotesEntity(++ID, editNote.getText().toString(), selectedCourseID);
            System.out.println(save);

        schedulerRepository.insert(save);

        //This takes you back to the course view screen after saving the new note
        Intent intent = new Intent(NoteActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    public void deleteNoteButton(View view) {
        //this brings up a pop up box asking to confirm delete
        AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
        builder.setMessage("Do you want to delete this note?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //once OK is clicked, assessment is deleted
                    public void onClick(DialogInterface dialog, int which) {
                        schedulerRepository.delete(selectedNote);
                        Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_LONG).show();
                        //This takes the user back to the courseViewActivity screen once note deleted
                        Intent intent = new Intent(NoteActivity.this, CourseViewActivity.class);
                        startActivity(intent);
                    }
                })
                //If use hits cancel instead of OK, it takes them back to assessmentViewActivity
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(NoteActivity.this, NoteActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
