import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        StudentCourseDb studentDb = new StudentCourseDb();
        Connection conn = studentDb.openDBConnection();
        System.out.println("connection: "+conn);

    }
}
