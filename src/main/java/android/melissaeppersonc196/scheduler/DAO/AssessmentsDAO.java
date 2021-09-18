package android.melissaeppersonc196.scheduler.DAO;

import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentsEntity assessments);

    @Delete
    void delete(AssessmentsEntity assessments);

    @Query("DELETE FROM assessmentsTable")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessmentsTable ORDER BY assessmentID ASC")
    List<AssessmentsEntity> getAllAssessments();

}
