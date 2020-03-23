import java.sql.*;
import static java.sql.DriverManager.*;

/*
Get the studentList, from sql/console.
 */

public class StudentCourseDb {
    Connection conn = null;

    public ResultSet getStudentList(){
       String sql;
       sql = "SELECT " +
                " S.STUDENT_ID, " +
                " S.NAME, " +
                 " S.LAST_NAME, " +
                 " C.NAME as CITY " +
               " FROM " +
                " STUDENT S " +
                " JOIN CITY C ON S.CITY = C.CITY_ID ";


       Statement stmt = null;
       try {
           stmt = conn.createStatement();
       } catch (SQLException e) {
           closeDbConnection();
           e.printStackTrace();
           return null;
       }

       ResultSet rs = null;

       try {
           rs = stmt.executeQuery(sql);
       } catch (SQLException e) {
           e.printStackTrace();
       }

       //closeDbConnection(conn);
       return rs;
   }

   /*
   Prepare Statement, for student average
    */
    public ResultSet getStudentAverage(int studentID){
        String sql;
        sql = "SELECT " +
                "    AVG(GS.GRADE) AS AVERAGE, " +
                "    SC.STUDENT_ID " +
                " FROM " +
                "      STUDENT_CALENDER      SC " +
                "      LEFT JOIN GRADE_SCALE GS on SC.GRADE_SCALE_ID = GS.GRADE_SCALE_ID " +
                " WHERE " +
                "       STUDENT_ID = ? " +
                " GROUP BY" +
                "       SC.STUDENT_ID" ;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setInt(1,  studentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //closeDbConnection(conn);
        return rs;
    }



    /*
    Get the CourceList, from sql/console.
     */

    public ResultSet getCourseList() {
        String sql;
        sql = "SELECT"+
                    " CC.COURSE_CALENDER_ID, " +
                    " C.NAME, " +
                    " C.DESCRIPTION, " +
                    " S.SEASON || '-' || S.YEAR AS SEMESTER " +
                " FROM" +
                    " COURSE_CALENDER CC " +
                    " JOIN COURSE C ON C.COURSE_ID = CC.COURSE_ID " +
                    " JOIN SEMESTER S ON CC.SEMESTER_ID = S.SEMESTER_ID " ;

        Statement stmt = null;

       //System.out.println("sql: " + sql);

        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            closeDbConnection();
            e.printStackTrace();
            return null;
        }

        ResultSet rs = null;

        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //closeDbConnection(conn);
        return rs;
    }

    /*
     Prepare Statement, for course average
    */

    public ResultSet getCourseAverage(int courseID){
        String sql;
        sql = "SELECT"+
                         " AVG(GS.GRADE) AS AVERAGE, "+
                         " SC.COURCE_CALENDER_ID "+
                         " FROM "+
                         " STUDENT_CALENDER SC "+
                         " LEFT JOIN GRADE_SCALE GS ON SC.GRADE_SCALE_ID = GS.GRADE_SCALE_ID "  +
                " WHERE "+
                        " COURCE_CALENDER_ID = ? "+
                " GROUP BY "+
                        " SC.COURCE_CALENDER_ID ";

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setInt(1,  courseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //closeDbConnection(conn);
        return rs;

    }

    /*
   Get the StudNullList, from sql/console.
    */

    public ResultSet getStudNullList(){
        String sql;
        sql = "SELECT" +
                  " SC.STUDENT_CALENDER_ID, " +
                  " STU.NAME, " +
                  " STU.LAST_NAME, " +
                  " C.NAME, " +
                  " GS.GRADE, " +
                  " SEM.YEAR, " +
                  " SEM.SEASON, " +
                  " T.NAME " +
                " FROM " +
                  " STUDENT_CALENDER SC " +
                  " JOIN STUDENT STU ON STU.STUDENT_ID = SC.STUDENT_ID " +
                  " JOIN COURSE_CALENDER CC ON CC.COURSE_CALENDER_ID = SC.COURCE_CALENDER_ID " +
                  " JOIN COURSE C ON CC.COURSE_ID = C.COURSE_ID " +
                  " LEFT JOIN GRADE_SCALE GS ON GS.GRADE_SCALE_ID = SC.GRADE_SCALE_ID " +
                  " JOIN SEMESTER SEM ON CC.SEMESTER_ID = SEM.SEMESTER_ID " +
                  " JOIN TEACHER T ON CC.TEACHER_ID = T.TEACHER_ID " +
                "WHERE " +
                  " SC.GRADE_SCALE_ID IS NULL ";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            closeDbConnection();
            e.printStackTrace();
            return null;
        }

        ResultSet rs = null;

        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //closeDbConnection(conn);
        return rs;
    }

      /*
     Prepare Statement, for update
    */

    public int updateStudGrade(String studGrade, int studCalID){
        String sql;
        sql = "UPDATE STUDENT_CALENDER " +
                " SET GRADE_SCALE_ID = (SELECT GRADE_SCALE_ID FROM GRADE_SCALE WHERE GRADE = ?) " +
                " WHERE " +
                        " STUDENT_CALENDER_ID = ? " ;


        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setString(1, studGrade);
            pstmt.setInt(2,  studCalID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        rowAffected writes out to the user, how many rows is changed in the sql.
         */
        int rowAffected = 0;
        try {

            rowAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowAffected;
    }



    //closeDbConnection(conn);

    public void testDbConnection(){

        Connection conn = openDBConnection();
        System.out.println("connection: "+conn);
        closeDbConnection();
    }

    public Connection openDBConnection(){
        String url = "jdbc:sqlite:StudentCourse.db";

        try {
            conn = getConnection(url);
        } catch
        (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*
    To avoid memory- and resource leak, always remember to close a connection after use.
    Everytime you make an 'open' resource, always have a 'close'.
     */
    public void closeDbConnection(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
