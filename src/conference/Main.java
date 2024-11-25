package conference;

public class Main {
    public static void main(String[] args) {
        // Create some attendees and sessions
        Attendee attendee1 = new Attendee("John Doe", "john@example.com");
        Attendee attendee2 = new Attendee("Jane Smith", "jane@example.com");
        Session session1 = new Session("AI in Healthcare", "2025-05-10", "10:00", "Room 101");

        // Register attendees and open session
        Conference conference = new Conference("GAF-AI 2025", "2025-05-01", "2025-05-05");
        conference.registerAttendee(attendee1);
        conference.registerAttendee(attendee2);
        conference.openNewSession(session1);

        // Mark attendance for the session
        attendee1.markAttendance(session1);  // John Doe attends
        attendee2.markAttendance(session1);  // Jane Smith attends

        // Generate certificates for all attendees who attended at least one session
        conference.generateCertificates();  // This will generate certificates for John and Jane
    }
}
