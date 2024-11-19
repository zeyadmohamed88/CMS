package conference;

public class Main {
    public static void main(String[] args) {
        // Initialize the conference
        Conference conference = new Conference("GAF-AI 2025", "2025-06-01", "2025-06-07");

        // Register attendees
        Attendee attendee1 = new Attendee("Dr. Abram", "abram@example.com");
        Attendee attendee2 = new Attendee("Dr. Jane", "jane@example.com");
        conference.registerAttendee(attendee1);
        conference.registerAttendee(attendee2);

        // Add a new session (triggers notifications for all attendees)
        Session session1 = new Session("Introduction to LLMs", "2025-06-02", "10:00 AM", "Room 101");
        conference.openNewSession(session1);

        // Add a session to an attendee's schedule (triggers notification for the attendee)
        conference.addSessionToAttendeeSchedule(attendee1, session1);

        // Remove a session from an attendee's schedule (triggers notification for the attendee)
        conference.removeSessionFromAttendeeSchedule(attendee1, session1);
    }
}
