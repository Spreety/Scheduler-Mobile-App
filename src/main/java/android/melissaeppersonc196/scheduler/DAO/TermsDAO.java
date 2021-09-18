package android.melissaeppersonc196.scheduler.DAO;

import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermsEntity terms);

    @Delete
    void delete(TermsEntity terms);

    @Query("DELETE FROM termsTable")
    void deleteAllTerms();

    @Query("SELECT * FROM termsTable ORDER BY termID ASC")
    List<TermsEntity> getAllTerms();
}
