import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    boolean interactingWithUser = true;
    Scanner keyboard = new Scanner(System.in);
      // studentCourseDb is the interface to the sql database
            StudentCourseDb studentCourseDb = new StudentCourseDb();


    public void startInterface () throws SQLException {
        studentCourseDb.openDBConnection();
        while (interactingWithUser) {

            System.out.println("Main menu. You have the following options: " + "\n");
            System.out.println("Press 1 if you want to see the average grade of a specific student. ");
            System.out.println("Press 2 if you want to see the average grade of a specific course. ");
            System.out.println("Press 3 if you want to assign a grade to a specific student. ");
            System.out.println("Press x if you want to leave the student course registration. ");

            String input = keyboard.next();

            if (input.equalsIgnoreCase("x")){
                System.out.println("Goodbye, have a nice day.");
                interactingWithUser = false;

            } else if (input.equalsIgnoreCase("1")) {
                aveGradeForStud();

            } else if (input.equalsIgnoreCase("2")) {
                aveGradeForCourse();

            } else if (input.equalsIgnoreCase("3")) {
                assignGradeForStud();

            }  else {
                System.out.println( input + " is not a valid option.");
            }
        }

        /*
        Closing connection
        */
        studentCourseDb.closeDbConnection();

    }

    // Three functions, that handles the three different options, the user can select, in the main menu.

    private void aveGradeForStud() throws SQLException {
       /*
       Print StudentList to user
       */

       ResultSet rs = studentCourseDb.getStudentList();
       while (rs != null & rs.next()) {
          System.out.println(rs.getInt("STUDENT_ID") + "\t" + rs.getString("NAME") + "\t\t\t" + rs.getString("LAST_NAME") + "\t\t\t" + rs.getString("CITY"));
       }

       /*
       The user selects a student.
       Returns the average grade from that student.
       */

       System.out.println("\n " + "Type in a student ID, and the average grade of that student will be returned");
       int studID = keyboard.nextInt();
       System.out.println("The average grade of the student with the student ID " + studID + ", is: ");
       rs = studentCourseDb.getStudentAverage(studID);
       if (rs != null) {
         System.out.println(rs.getFloat("AVERAGE") + "\n");
       }
    }

    private void aveGradeForCourse() throws SQLException {
        /*
        Print CourseList to user
        */
        ResultSet rs = studentCourseDb.getCourseList();
        while (rs != null & rs.next()) {
            System.out.println(rs.getInt("COURSE_CALENDER_ID") + "\t" + rs.getString("NAME") + "\t" + rs.getString("DESCRIPTION") + "\t" + rs.getString("SEMESTER"));
        }

        /*
        The user selects a course.
        Returns the average grade for that course.
        */

        System.out.println("\n" + "Type in a course ID, and the average grade of that course will be returned");
        int courseID = keyboard.nextInt();
        System.out.println("The average grade of the course with the course ID " + courseID + ", is: ");
        rs = studentCourseDb.getCourseAverage(courseID);
        if (rs != null){
           System.out.println(rs.getFloat("AVERAGE") + "\n");
        }
    }

    private void assignGradeForStud() throws SQLException {
        /*
        Print StudNullList to user
        */

        ResultSet rs = studentCourseDb.getStudNullList();
        while (rs != null & rs.next()){
            System.out.println(rs.getInt("STUDENT_CALENDER_ID") + "\t" + rs.getString("NAME") + "\t" + rs.getString("LAST_NAME") + "\t\t\t" + rs.getString("GRADE") + "\t\t" + rs.getString("YEAR") + "\t\t" + rs.getString("SEASON"));
        }

        /*
        The user selects a student.
        Gives that student a grade.
        */

        System.out.println("\n " + "Type in the student calender ID, on the student you want to give a grade: ");
        int studCalID = keyboard.nextInt();
        System.out.println("Type in the grade: ");
        String studGrade = keyboard.next();
        int rowAffedted = studentCourseDb.updateStudGrade(studGrade, studCalID);
        System.out.println("Row Affected: " + rowAffedted);
    }
}
