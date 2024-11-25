package conference;
import java.util.List;
import java.util.ArrayList;

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

        // Collect feedback for the session
        conference.collectFeedback("A1", "S1", "Great session on AI applications in healthcare!", 5);  // John Doe's feedback
        conference.collectFeedback("A2", "S1", "Very informative, but a bit too technical.", 4);  // Jane Smith's feedback

        // Get feedback for session
        List<Feedback> sessionFeedback = conference.getFeedbackForSession("S1");
        System.out.println("\nFeedback for session AI in Healthcare:");
        for (Feedback feedback : sessionFeedback) {
            System.out.println(feedback);
        }

        // Get feedback for an attendee
        List<Feedback> attendee1Feedback = conference.getFeedbackForAttendee("A1");
        System.out.println("\nFeedback by John Doe:");
        for (Feedback feedback : attendee1Feedback) {
            System.out.println(feedback);
        }
    }
}

