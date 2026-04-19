import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://dpg-d7i32pnavr4c73fdkci0-a.oregon-postgres.render.com:5432/hospital_l0or?sslmode=require";
        String user = "hospital_l0or_user";
        String password = "V9NVq474a0ZMg2glukhEFJMEnsqTFOkh";
        try {
            System.out.println("Connecting...");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Success!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
