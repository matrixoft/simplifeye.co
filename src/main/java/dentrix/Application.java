package dentrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
//            String query = "SELECT Name,Description,Qty,Cost FROM Stock";
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            Connection con = DriverManager.getConnection("jdbc:odbc:DSN=c-treeACE ODBC Driver;host=104.154.33.239;UID=kaKp6KxN;PWD=C8gDK2N4H;Database=DentrixSQL;port=6597;");
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                String name = rs.getString("Name");
//                String desc = rs.getString("Description");
//                int qty = rs.getInt("Qty");
//                float cost = rs.getFloat("Cost");
//                System.out.println(name + ", " + desc + "\t: " + qty + "\t@ $" + cost);
//            }
//            con.close();

            // Set up connection
            DentrixConnector connector = new DentrixConnector("10.240.106.200", "kaKp6KxN", "C8gDK2N4H");
            System.out.println("Connection completed.");

        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        SpringApplication.run(Application.class, args);
    }
}
