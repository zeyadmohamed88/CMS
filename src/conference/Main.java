package conference;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create a list to store sessions
        List<Session> sessions = new ArrayList<>();  // Declare and initialize the sessions list

        // Create speakers using names from the rubric
        Speaker speaker1 = new Speaker("S1", "Dr. Abram", "Expert in AI and Healthcare");
        Speaker speaker2 = new Speaker("S2", "Dr. Nancy", "Specialist in Machine Learning");

        // Create sessions with sessionCount passed as sessions.size()
        Session session1 = new Session("AI in Healthcare", "2025-05-10", "10:00", "Room 101", sessions.size());
        Session session2 = new Session("Machine Learning Advancements", "2025-05-11", "11:00", "Room 102", sessions.size());

        // Add sessions to the sessions list
        sessions.add(session1);
        sessions.add(session2);

        // Create the conference
        Conference conference = new Conference("GAF-AI 2025", "2025-05-01", "2025-05-05");

        // Assign speakers to sessions
        conference.assignSpeakerToSession(speaker1, session1);
        conference.assignSpeakerToSession(speaker2, session2);

        // Add speakers to the conference
        conference.addSpeaker(speaker1);
        conference.addSpeaker(speaker2);

        // List all speakers and their sessions
        System.out.println("List of Speakers and Their Sessions:");
        conference.listSpeakersAndSessions();
    }
}
