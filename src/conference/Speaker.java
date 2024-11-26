package conference;

import java.util.ArrayList;
import java.util.List;

public class Speaker {
    private String speakerID;
    private String name;
    private String bio;
    private List<Session> sessions;  // List of sessions the speaker is associated with

    // Constructor
    public Speaker(String speakerID, String name, String bio) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.sessions = new ArrayList<>();
    }

    // Add a session to the speaker's list
    public void addSession(Session session) {
        sessions.add(session);
    }

    // Get all sessions assigned to the speaker
    public List<Session> getSessions() {
        return sessions;
    }

    // Getters
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
