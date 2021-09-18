package android.melissaeppersonc196.scheduler.DAO;

import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CoursesEntity courses);

    @Delete
    void delete(CoursesEntity courses);

    @Query("DELETE FROM coursesTable")
    void deleteAllCourses();

    @Query("SELECT * FROM coursesTable ORDER BY courseID ASC")
    List<CoursesEntity> getAllCourses();
}
