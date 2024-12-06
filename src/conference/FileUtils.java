package conference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String DATA_DIR = System.getProperty("user.dir") + File.separator + "data";

    // Helper method to get the full file path
    private static String getFilePath(String fileName) {
        return DATA_DIR + File.separator + fileName;
    }

    // Save a list of objects (e.g., attendees, sessions, feedback) to a file
    public static <T> void saveToFile(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)))) {
            oos.writeObject(data);
            System.out.println("Data saved to file: " + getFilePath(fileName));
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load a list of objects from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath(fileName)))) {
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + getFilePath(fileName) + ". Initializing new data.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Save sessions to a file
    public static void saveSessions(List<Session> sessions) {
        saveToFile("sessions.dat", sessions);
    }

    // Load sessions from a file
    public static List<Session> loadSessions() {
        return loadFromFile("sessions.dat");
    }

    // Save attendees to a file
    public static void saveAttendees(List<Attendee> attendees) {
        saveToFile("attendees.dat", attendees);
    }

    // Load attendees from a file
    public static List<Attendee> loadAttendees() {
        return loadFromFile("attendees.dat");
    }

    // Save feedback to a file
    public static void saveFeedback(List<Feedback> feedbackList) {
        saveToFile("feedback.dat", feedbackList);
    }

    // Load feedback from a file
    public static List<Feedback> loadFeedback() {
        return loadFromFile("feedback.dat");
    }

    // Save speakers to a file
    public static void saveSpeakers(List<Speaker> speakers) {
        saveToFile("speakers.dat", speakers);
    }

    // Load speakers from a file
    public static List<Speaker> loadSpeakers() {
        return loadFromFile("speakers.dat");
    }

    // Save sessions, attendees, feedback, and speakers to file
    public static void saveConferenceData(Conference conference) {
        saveSessions(conference.getListOfSessions());
        saveAttendees(conference.getListOfAttendees());
        saveFeedback(conference.getFeedbackList());
        saveSpeakers(conference.getListOfSpeakers());
    }

    // Load sessions, attendees, feedback, and speakers from file
    public static Conference loadConferenceData() {
        List<Session> sessions = loadSessions();
        List<Attendee> attendees = loadAttendees();
        List<Feedback> feedback = loadFeedback();
        List<Speaker> speakers = loadSpeakers();

        // Recreate the conference instance and restore the data
        Conference conference = new Conference("GAF-AI 2025", "2025-01-01", "2025-01-07");

        for (Session session : sessions) {
            conference.openNewSession(session);  // Add sessions back to the conference
        }
        for (Attendee attendee : attendees) {
            conference.registerAttendee(attendee);  // Register attendees
        }
        for (Feedback fb : feedback) {
            conference.collectFeedback(fb.getAttendeeID(), fb.getSessionID(), fb.getComment(), fb.getRating());  // Collect feedbacks
        }
        for (Speaker speaker : speakers) {
            conference.addSpeaker(speaker);  // Add speakers back to the conference
        }

        return conference;
    }
}
