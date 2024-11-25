package conference;

import java.io.Serializable;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization
    private String feedbackID;
    private String attendeeID;
    private String sessionID;   // Session ID this feedback is linked to
    private String comment;
    private int rating;         // Rating between 1 and 5

    // Constructor
    public Feedback(String feedbackID, String attendeeID, String sessionID, String comment, int rating) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.sessionID = sessionID;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and setters
    public String getFeedbackID() {
        return feedbackID;
    }

    public String getAttendeeID() {
        return attendeeID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Feedback [ID=" + feedbackID + ", Attendee=" + attendeeID + ", Session=" + sessionID
                + ", Comment=" + comment + ", Rating=" + rating + "]";
    }
}
