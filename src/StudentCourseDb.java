import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.*;

public class StudentCourseDb {

    public void createTables(){

    }

    public void dropTables(){

    }

    public void insetDate(){

    }

    public Connection openDBConnection(){
        String url = "jdbc:sqlite:StudentCourse.db";
        Connection conn = null;
        try {
            conn = getConnection(url);
        } catch
        (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }
}
