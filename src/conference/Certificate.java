package conference;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Certificate {
    private String certificateID;
    private String attendeeID;
    private String conferenceName;
    private String issueDate;

    // Getters and setters
    public String getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(String certificateID) {
        this.certificateID = certificateID;
    }

    public String getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    // Method to generate a certificate
    public void generateCertificate(Attendee attendee, Session session) {
        String certificateText = "Certificate of Attendance\n\n";
        certificateText += "This is to certify that " + attendee.getName() + "\n";
        certificateText += "has attended the session: " + session.getSessionName() + "\n";
        certificateText += "Thank you for attending!\n";
        certificateText += "Date: " + java.time.LocalDate.now().toString();

        // Generate a simple text file as certificate for now
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Certificates/" + attendee.getAttendeeID() + "_" + session.getSessionID() + ".txt"))) {
            writer.write(certificateText);
            System.out.println("Certificate generated for " + attendee.getName());
        } catch (IOException e) {
            showAlert("Error", "Error generating certificate: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
