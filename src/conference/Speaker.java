package conference;

import java.util.ArrayList;
import java.util.List;

public class Speaker {
    private String speakerID;
    private String name;
    private String bio;
    private List<Session> sessions;  // List of sessions the speaker is assigned to

    // Constructor
    public Speaker(String speakerID, String name, String bio) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.sessions = new ArrayList<>();
    }

    // Add a session to the speaker's list
    public void addSession(Session session) {
        if (!sessions.contains(session)) {
            sessions.add(session);
            System.out.println("Session " + session.getSessionName() + " assigned to speaker: " + name);
        } else {
            System.out.println("Speaker " + name + " is already assigned to session: " + session.getSessionName());
        }
    }

    // Get all sessions assigned to the speaker
    public List<Session> getSessions() {
        return sessions;
    }

    // Display all sessions for the speaker
    public void displaySessions() {
        System.out.println(name + " is assigned to the following sessions:");
        for (Session session : sessions) {
            System.out.println("- " + session.getSessionName());
        }
    }

    public String getSpeakerID() {
        return speakerID;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return "Speaker [ID=" + speakerID + ", Name=" + name + ", Bio=" + bio + "]";
    }
}
