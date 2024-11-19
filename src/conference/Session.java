package conference;

import java.io.Serializable;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sessionID;
    private String sessionName;
    private String sessionDate;
    private String time;
    private String room;
    private Speaker speaker;

    // Constructor
    public Session(String sessionName, String sessionDate, String time, String room) {
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
        this.time = time;
        this.room = room;
    }

    // Getters and setters
    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "Session [ID=" + sessionID + ", Name=" + sessionName + ", Date=" + sessionDate + ", Time=" + time + ", Room=" + room + ", Speaker=" + (speaker != null ? speaker.getName() : "None") + "]";
    }
}
