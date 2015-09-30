package dentrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    @RestController
    public static class PatientController {

        @RequestMapping(value = "/patients", method = RequestMethod.GET)
        public @ResponseBody
        List<DentrixConnector.Patient> patients(){
            try {
                System.out.println("REST to dentix.");
                DentrixConnector connector = new DentrixConnector("10.240.106.200", "kaKp6KxN", "C8gDK2N4H");
                System.out.println("REST dentix completed.");
                return connector.getPatients();
            }catch(Exception e){
                e.printStackTrace(System.err);
                return new ArrayList<DentrixConnector.Patient>();
            }
        }

    }
    public static void main(String[] args) {
        try {

            // Set up connection
            System.out.println("Connecting to dentix.");
            DentrixConnector connector = new DentrixConnector("10.240.106.200", "kaKp6KxN", "C8gDK2N4H");
            System.out.println("Connection completed.");
            List<DentrixConnector.Patient> patientsList = connector.getPatients();
            for(DentrixConnector.Patient p : patientsList){
                System.out.println(p.getId()+" "+p.getName()+" "+p.getLocation());
            }

        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        SpringApplication.run(Application.class, args);
    }
}
