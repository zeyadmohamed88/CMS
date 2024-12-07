package conference;

public class Feedback {

    private String feedbackID;
    private String attendeeID;
    private String sessionID;   // Session ID this feedback is linked to
    private String comment;
    private int rating;         // Rating between 1 and 5

    // Constructor to create Feedback object with details
    public Feedback(String feedbackID, String attendeeID, String sessionID, String comment, int rating) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.sessionID = sessionID;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters
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

    // Method to convert the Feedback object to a CSV string
    public String toCSV() {
        return feedbackID + "," + attendeeID + "," + sessionID + "," + comment + "," + rating;
    }

    // Static method to create a Feedback object from a CSV string
    public static Feedback fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length == 5) {
            // Construct Feedback object from CSV string
            return new Feedback(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]));
        }
        return null;  // Return null if the CSV line is not valid
    }

    @Override
    public String toString() {
        return "Feedback [ID=" + feedbackID + ", Attendee=" + attendeeID + ", Session=" + sessionID
                + ", Comment=" + comment + ", Rating=" + rating + "]";
    }
}
