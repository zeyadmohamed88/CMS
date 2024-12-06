package conference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionID;
    private String sessionName;
    private String sessionDate;
    private String time;
    private String room;
    private Speaker speaker;
    private List<Attendee> attendeesList;  // List to track attendees
    private List<Feedback> feedbackList;    // List to store feedback for this session

    // Constructor that includes sessionCount for generating session ID
    public Session(String sessionName, String sessionDate, String time, String room, int sessionCount) {
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
        this.time = time;
        this.room = room;
        this.attendeesList = new ArrayList<>();  // Initialize the attendees list
        this.feedbackList = new ArrayList<>();   // Initialize the feedback list
        this.sessionID = "S" + (sessionCount + 1); // Generate session ID (e.g., S1, S2, ...)
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

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    // Method to mark attendance for an attendee
    public void markAttendance(Attendee attendee) {
        if (!attendeesList.contains(attendee)) {
            attendeesList.add(attendee);
            System.out.println("Attendance marked for: " + attendee.getName());
        } else {
            System.out.println("Attendee " + attendee.getName() + " is already marked as present.");
        }
    }

    // Method to get the list of attendees
    public List<Attendee> getAttendeesList() {
        return attendeesList;
    }

    // Method to add feedback to this session
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }

    // Method to get the list of feedback for this session
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    // Method to collect feedback for this session
    public void collectFeedback(Attendee attendee, String comment, int rating) {
        // Check if the session has already been attended by the attendee
        if (!attendeesList.contains(attendee)) {
            System.out.println("Attendee has not attended this session yet.");
            return;  // Exit if attendee has not attended this session
        }

        // Create a unique feedback ID
        String feedbackID = "F" + (feedbackList.size() + 1);

        // Create the Feedback object and add it to the session's feedback list
        Feedback feedback = new Feedback(feedbackID, attendee.getAttendeeID(), this.sessionID, comment, rating);
        feedbackList.add(feedback);

        System.out.println("Feedback collected: " + feedback);
    }

    @Override
    public String toString() {
        return "Session [ID=" + sessionID + ", Name=" + sessionName + ", Date=" + sessionDate + ", Time=" + time + ", Room=" + room + ", Speaker=" + (speaker != null ? speaker.getName() : "None") + "]";
    }
}
