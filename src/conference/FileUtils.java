package conference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // Save a list of objects (e.g., attendees, sessions, feedback) to a file
    public static <T> void saveToFile(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
            System.out.println("Data saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load a list of objects from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName + ". Initializing new data.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Save attendees to a file
    public static void saveAttendees(List<Attendee> attendees) {
        saveToFile("attendees.dat", attendees);  // Save attendees list to "attendees.dat"
    }

    // Load attendees from a file
    public static List<Attendee> loadAttendees() {
        return loadFromFile("attendees.dat");  // Load attendees from "attendees.dat"
    }

    // Save sessions to a file
    public static void saveSessions(List<Session> sessions) {
        saveToFile("sessions.dat", sessions);  // Save sessions list to "sessions.dat"
    }

    // Load sessions from a file
    public static List<Session> loadSessions() {
        return loadFromFile("sessions.dat");  // Load sessions from "sessions.dat"
    }

    // Save feedback to a file
    public static void saveFeedback(List<Feedback> feedbackList) {
        saveToFile("feedback.dat", feedbackList);  // Save feedback list to "feedback.dat"
    }

    // Load feedback from a file
    public static List<Feedback> loadFeedback() {
        return loadFromFile("feedback.dat");  // Load feedback from "feedback.dat"
    }
}
