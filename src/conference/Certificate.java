package conference;

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
    public void generateCertificate() {
        String content = "Certificate of Attendance\n"
                + "-------------------------\n"
                + "Certificate ID: " + certificateID + "\n"
                + "Attendee ID: " + attendeeID + "\n"
                + "Conference: " + conferenceName + "\n"
                + "Issued On: " + issueDate + "\n";

        // Write certificate to a text file
        try (FileWriter writer = new FileWriter("Certificate_" + attendeeID + ".txt")) {
            writer.write(content);
            System.out.println("Certificate generated for Attendee ID: " + attendeeID);
        } catch (IOException e) {
            System.err.println("Error generating certificate: " + e.getMessage());
        }
    }
}
