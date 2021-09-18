package android.melissaeppersonc196.scheduler.Database;

import android.content.Context;
import android.melissaeppersonc196.scheduler.DAO.AssessmentsDAO;
import android.melissaeppersonc196.scheduler.DAO.CoursesDAO;
import android.melissaeppersonc196.scheduler.DAO.NotesDAO;
import android.melissaeppersonc196.scheduler.DAO.TermsDAO;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AssessmentsEntity.class, CoursesEntity.class, TermsEntity.class, NotesEntity.class}, version = 6, exportSchema = false)

public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract AssessmentsDAO assessmentDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract TermsDAO termsDAO();
    public abstract NotesDAO notesDAO();
    private static final int Thread_Numbers = 4;

    /**
     * database writer executor
     */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(Thread_Numbers);

    private static volatile SchedulerDatabase Instance;

    static SchedulerDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (SchedulerDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabase.class, "scheduler_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return Instance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //The following line deletes data when app restarts
            //comment out this line if you want to keep data
            databaseWriteExecutor.execute(() -> {
                //this populates the database in the background

                AssessmentsDAO aAssessmentsDAO = Instance.assessmentDAO();
                CoursesDAO aCoursesDAO = Instance.coursesDAO();
                TermsDAO aTermsDAO = Instance.termsDAO();
                NotesDAO aNotesDAO = Instance.notesDAO();

                //This starts the app with a clean database every time
                //delete this after testing
               // aAssessmentsDAO.deleteAllAssessments();
              //  aCoursesDAO.deleteAllCourses();
              //  aTermsDAO.deleteAllTerms();
                //aNotesDAO.deleteAllNotes();

                AssessmentsEntity assessments = new AssessmentsEntity(1, "Android Scheduler App", "Performance Assessment", "7/1/2021",1);
                aAssessmentsDAO.insert(assessments);
                assessments = new AssessmentsEntity(2, "A+ Certification Test Part 1", "Objective Assessment", "9/12/2021",2);
                aAssessmentsDAO.insert(assessments);
                assessments = new AssessmentsEntity(3, "Java Inventory App", "Performance Assessment", "5/20/2021",3);
                aAssessmentsDAO.insert(assessments);
                assessments = new AssessmentsEntity(4, "A+ Certification Test Part 2", "Objective Assessment", "10/12/2021",2);
                aAssessmentsDAO.insert(assessments);

                CoursesEntity courses = new CoursesEntity(1, "C196 Mobile App Developement", "6/1/2021", "7/31/2021", "In Progress", "Bob Jones", "(555)555-5555", "bob.jones@school.edu",1);
                aCoursesDAO.insert(courses);
                courses = new CoursesEntity(2, "C211 Computer Hardware", "1/1/2022", "4/12/2022", "plan to take", "Emily Smith", "(444)444-4444", "emily.smith@school.edu", 2);
                aCoursesDAO.insert(courses);
                courses = new CoursesEntity(3, "C170 Software 1", "1/1/2021", "5/20/2021", "complete", "John Davis", "(333)333-3333", "john.davis@school.edu", 3);
                aCoursesDAO.insert(courses);

                TermsEntity terms = new TermsEntity(1, "Term 1", "12/1/2020", "5/31/2021", 3);
                aTermsDAO.insert(terms);
                terms = new TermsEntity(2, "Term 2", "6/1/2021", "11/30/2021", 1);
                aTermsDAO.insert(terms);
                terms = new TermsEntity(3, "Term 3", "12/1/2021", "5/31/2022", 2);
                aTermsDAO.insert(terms);

                NotesEntity notes = new NotesEntity(1, "Focus on clean, functional code.", 1);
                aNotesDAO.insert(notes);
                notes = new NotesEntity(2, "Focus on learning the components of computers.", 2);
                aNotesDAO.insert(notes);
                notes = new NotesEntity(3, "Learn java basics.", 3);
                aNotesDAO.insert(notes);
                notes = new NotesEntity(4, "Memorize vocabulary.", 2);
                aNotesDAO.insert(notes);
            });
        }
    };
}
