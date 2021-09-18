package android.melissaeppersonc196.scheduler.DAO;

import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotesEntity terms);

    @Delete
    void delete(NotesEntity notes);

    @Query("DELETE FROM notesTable")
    void deleteAllNotes();

    @Query("SELECT * FROM notesTable ORDER BY noteID ASC")
    List<NotesEntity> getAllNotes();
}
