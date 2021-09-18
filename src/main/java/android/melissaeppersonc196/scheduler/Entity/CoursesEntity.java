package android.melissaeppersonc196.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coursesTable")
public class CoursesEntity {
    @PrimaryKey
    private int courseID;

    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termID;

    @Override
    public String toString() {
        return "CoursesEntity{" +
                "courseID=" + courseID +
                ", title=" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate + '\'' +
                ", status=" + status + '\'' +
                ", instructorName=" + instructorName + '\'' +
                ", instructorPhone=" + instructorPhone + '\'' +
                ", instructorEmail=" + instructorEmail +
                ", termID=" + termID +
                '}';
    }

    public CoursesEntity(int courseID, String title, String startDate, String endDate, String status, String instructorName, String instructorPhone, String instructorEmail, int termID) {
        this.courseID = courseID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termID = termID;

    }

    public int getCourseID() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public int getTermID() {
        return termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
