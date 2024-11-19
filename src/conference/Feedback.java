package conference;

import java.io.Serializable;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization
    private String feedbackID;
    private String attendeeID;
    private String comment;
    private int rating;

    // Constructor
    public Feedback(String feedbackID, String attendeeID, String comment, int rating) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
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

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Feedback [ID=" + feedbackID + ", Attendee=" + attendeeID + ", Comment=" + comment + ", Rating=" + rating + "]";
    }
}