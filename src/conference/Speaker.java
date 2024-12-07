package conference;

import java.util.ArrayList;
import java.util.List;

public class Speaker {
    private String speakerID;
    private String name;
    private String bio;
    private String email;  // New field for email
    private List<Session> sessions;  // List of sessions the speaker is assigned to

    // Constructor with email and an empty session list
    public Speaker(String speakerID, String name, String bio, String email) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.email = email;  // Initialize email
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

    // Getter for speakerID
    public String getSpeakerID() {
        return speakerID;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for bio
    public String getBio() {
        return bio;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Override toString method to display speaker details
    @Override
    public String toString() {
        return "Speaker [ID=" + speakerID + ", Name=" + name + ", Bio=" + bio + ", Email=" + email + "]";
    }

    // Convert Speaker object to a CSV-friendly format (String)
    public String toCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append(speakerID).append(",")
                .append(name).append(",")
                .append(bio).append(",")
                .append(email);
        return csv.toString();
    }

    // Method to add session from a CSV line (for loading session data)
    public void addSessionFromCSV(String sessionID, String sessionName, String sessionDate, String sessionTime, String sessionRoom) {
        // Create a session from the CSV data and add it to the speaker
        Session session = new Session(sessionName, sessionDate, sessionTime, sessionRoom, Integer.parseInt(sessionID.substring(1)));
        addSession(session);
    }

    // Method to check if the speaker is assigned to a session
    public boolean isAssignedToSession(Session session) {
        return sessions.contains(session);
    }
}
