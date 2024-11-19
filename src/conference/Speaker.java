package conference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speaker implements Serializable {
    private static final long serialVersionUID = 1L;
    private String speakerID;
    private String name;
    private String bio;
    private List<Session> sessionList;

    // Constructor
    public Speaker(String speakerID, String name, String bio) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.sessionList = new ArrayList<>();
    }

    // Add a session to the speaker's list
    public void addSession(Session session) {
        if (!sessionList.contains(session)) {
            sessionList.add(session);
        }
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

    public List<Session> getSessionList() {
        return sessionList;
    }

    @Override
    public String toString() {
        return "Speaker [ID=" + speakerID + ", Name=" + name + ", Bio=" + bio + "]";
    }
}
