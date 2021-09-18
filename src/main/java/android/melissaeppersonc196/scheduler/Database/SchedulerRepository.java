package android.melissaeppersonc196.scheduler.Database;

import android.app.Application;
import android.melissaeppersonc196.scheduler.DAO.AssessmentsDAO;
import android.melissaeppersonc196.scheduler.DAO.CoursesDAO;
import android.melissaeppersonc196.scheduler.DAO.NotesDAO;
import android.melissaeppersonc196.scheduler.DAO.TermsDAO;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;

import java.util.List;

public class SchedulerRepository {
    private AssessmentsDAO aAssessmentsDAO;
    private CoursesDAO aCoursesDAO;
    private TermsDAO aTermsDAO;
    private NotesDAO aNotesDAO;
    private List<AssessmentsEntity> aAllAssessments;
    private List<CoursesEntity> aAllCourses;
    private List<TermsEntity> aAllTerms;
    private List<NotesEntity> aAllNotes;
   // private assessmentID;
  //  private courseID;
  //  private termID;

    public SchedulerRepository(Application application) {
        SchedulerDatabase db=SchedulerDatabase.getDatabase(application);
        aAssessmentsDAO=db.assessmentDAO();
        aCoursesDAO=db.coursesDAO();
        aTermsDAO=db.termsDAO();
        aNotesDAO=db.notesDAO();

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AssessmentsEntity> getAllAssessments(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAllAssessments=aAssessmentsDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return aAllAssessments;
    }

    public List<CoursesEntity> getAllCourses() {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAllCourses=aCoursesDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return aAllCourses;
    }

    public List<TermsEntity> getAllTerms(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAllTerms=aTermsDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return aAllTerms;
    }

    public List<NotesEntity> getAllNotes(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAllNotes=aNotesDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return aAllNotes;
    }

    public void insert (AssessmentsEntity assessmentsEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAssessmentsDAO.insert(assessmentsEntity);

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void insert (CoursesEntity coursesEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aCoursesDAO.insert(coursesEntity);

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void insert (TermsEntity termsEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aTermsDAO.insert(termsEntity);

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void insert (NotesEntity notesEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aNotesDAO.insert(notesEntity);

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete (AssessmentsEntity assessmentsEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aAssessmentsDAO.delete(assessmentsEntity);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete (CoursesEntity coursesEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aCoursesDAO.delete(coursesEntity);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete (TermsEntity termsEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aTermsDAO.delete(termsEntity);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete (NotesEntity notesEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            aNotesDAO.delete(notesEntity);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
