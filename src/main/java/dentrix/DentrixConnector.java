package dentrix;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.UnsupportedOperationException;

public class DentrixConnector  {

    private Connection connection;

    public DentrixConnector(String host, String db_username, String db_password) throws SQLException, ClassNotFoundException {
        // Connect via JDBC since its by far the best way to connect to the database
        String ctree_driver = "ctree.jdbc.ctreeDriver";
//        String dentrix_connection_string = "jdbc:ctree:6597@10.240.106.200:DentrixSQL";
        String dentrix_connection_string = "jdbc:ctree:6597@"+host+":DentrixSQL";
        // Load the ctree jdbc driver class since it isn't loaded by this point and we need it to connect to the db
        Class.forName(ctree_driver);
        connection = DriverManager.getConnection(dentrix_connection_string, db_username, db_password);
    }

    public static class Patient{
        String id;
        String name;
        String location;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
    public List<Patient> getPatients() throws SQLException {
        Statement statement = connection.createStatement();
        String list_patients_query = "SELECT patient_id, first_name, last_name, address_line1, address_line2, city, state, zipcode FROM admin.v_patient";

        ResultSet results = statement.executeQuery(list_patients_query);

        // Extract data from the result set
        // Initialize patients list
        List<Patient> patients = new ArrayList<Patient>();

        // Loop through the results and generate a new patient object and place it into the list
        while (results.next()) {
            // Since the database sometimes formats things weirdly, here we reformat it to make more sense before it gets
            // packaged as a protobuf packet
            String patient_id = results.getString("patient_id");
            String name = results.getString("first_name").trim() + " " + results.getString("last_name").trim();
            String location = results.getString("address_line1").trim() + "\n"
                    + results.getString("address_line2").trim() + "\n"
                    + results.getString("city").trim() + " "
                    + results.getString("state").trim() + " "
                    + results.getString("zipcode").trim();

            // Build the patient object
            Patient patient = new Patient();
            patient.setId(patient_id);
            patient.setName(name);
            patient.setLocation(location);

            // Add the patient to the list
            patients.add(patient);
        }

        // Clean up...
        results.close();
        statement.close();

        return patients;
    }

//    public List<Image> getImages(String patientID) {
//        // Dentrix API does not give us access to any x-rays or images so...
//        throw new UnsupportedOperationException("Current Dentrix API does not expose access to images.");
//    }
//
//    public boolean addNote(String patientID, String audio, String transcribedText) {
//        // Adding notes is not supported...
//        return false;
//    }
//
//
//    // TODO: (Nathan) Generalize the note getting methods into one method.
//    public List<Note> getPatientNotes(String patientID) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("SELECT note_text FROM admin.v_clinical_notes WHERE patient_id=?");
//
//        statement.setString(1, patientID);
//
//        ResultSet results = statement.executeQuery();
//
//        List<Note> notes = new ArrayList<Note>();
//
//        while(results.next()) {
//            String noteText = results.getString("note_text");
//
//            Note note = Note.newBuilder()
//                    .setNoteText(noteText)
//                    .setNoteType(Note.NoteType.NOTE)
//                    .build();
//
//            notes.add(note);
//        }
//
//        results.close();
//        statement.close();
//
//        return notes;
//    }
//
//    public List<Note> getAppointmentNotes(String patientID) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("SELECT note FROM admin.v_appointment WHERE patient_id=?");
//
//        statement.setString(1, patientID);
//
//        ResultSet results = statement.executeQuery();
//
//        List<Note> notes = new ArrayList<Note>();
//
//        while(results.next()) {
//            String noteText = results.getString("note");
//
//            Note note = Note.newBuilder()
//                    .setNoteText(noteText)
//                    .setNoteType(Note.NoteType.APPOINTMENT)
//                    .build();
//
//            notes.add(note);
//        }
//
//        results.close();
//        statement.close();
//
//        return notes;
//    }
//
//    public List<Note> getAlertNotes(String patientID) throws SQLException {
//        CallableStatement statement = connection.prepareCall("CALL admin.sp_getpatientmedicalalerts(?)");
//
//        statement.setString(1, patientID);
//
//        ResultSet results = statement.executeQuery();
//
//        List<Note> notes = new ArrayList<Note>();
//
//        while(results.next()) {
//            String noteText = results.getString("med_alert");
//
//            Note note = Note.newBuilder()
//                    .setNoteText(noteText)
//                    .setNoteType(Note.NoteType.ALERT)
//                    .build();
//
//            notes.add(note);
//        }
//
//        results.close();
//        statement.close();
//
//        return notes;
//    }
}
