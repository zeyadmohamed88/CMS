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

    // Save a list of objects (e.g., attendees, sessions, feedback) to a CSV file
    public static <T> void saveToFile(String fileName, List<T> data) {
        File dataDirectory = new File(DATA_DIR);
        if (!dataDirectory.exists()) {
            boolean dirCreated = dataDirectory.mkdirs();  // Create the directory if it doesn't exist
            if (dirCreated) {
                System.out.println("Data directory created: " + DATA_DIR);
            } else {
                System.err.println("Failed to create data directory: " + DATA_DIR);
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(fileName)))) {
            for (T item : data) {
                writer.write(objectToCSV(item));
                writer.newLine();
            }
            System.out.println("Data saved to file: " + getFilePath(fileName));
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load a list of objects from a CSV file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        File file = new File(getFilePath(fileName));

        // Check if the file exists, if not, initialize with default data
        if (!file.exists()) {
            System.out.println("File not found: " + getFilePath(fileName) + ". Initializing new data.");
            return initializeDefaultData(fileName);
        }

        List<T> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T item = csvToObject(line, fileName);
                if (item != null) {
                    data.add(item);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }

        return data;
    }

    // Convert an object to a CSV string based on the file type
    private static <T> String objectToCSV(T object) {
        if (object instanceof Session) {
            Session session = (Session) object;
            return session.getSessionID() + "," + session.getSessionName() + "," + session.getSessionDate() + "," + session.getTime() + "," + session.getRoom();
        } else if (object instanceof Attendee) {
            Attendee attendee = (Attendee) object;
            return attendee.getAttendeeID() + "," + attendee.getName() + "," + attendee.getEmail();
        } else if (object instanceof Speaker) {
            Speaker speaker = (Speaker) object;
            return speaker.getSpeakerID() + "," + speaker.getName() + "," + speaker.getBio() + "," + speaker.getEmail();
        } else if (object instanceof Feedback) {
            Feedback feedback = (Feedback) object;
            return feedback.getAttendeeID() + "," + feedback.getSessionID() + "," + feedback.getComment() + "," + feedback.getRating();
        }
        return "";
    }

    // Convert a CSV string to an object based on the file type
    private static <T> T csvToObject(String csvLine, String fileName) {
        String[] fields = csvLine.split(",");

        // For sessions.dat
        if ("sessions.dat".equals(fileName)) {
            if (fields.length == 5) {
                // Fields: sessionID, sessionName, sessionDate, time, room
                String sessionID = fields[0];
                String sessionName = fields[1];
                String sessionDate = fields[2];
                String time = fields[3];
                String room = fields[4];
                int sessionCount = Integer.parseInt(sessionID.substring(1)); // Assuming session ID is like "S1"
                return (T) new Session(sessionName, sessionDate, time, room, sessionCount);
            }
        }

        // For attendees.dat
        else if ("attendees.dat".equals(fileName)) {
            if (fields.length == 3) {
                // Fields: attendeeID, name, email
                return (T) new Attendee(fields[1], fields[2]);
            }
        }

        // For speakers.dat
        else if ("speakers.dat".equals(fileName)) {
            if (fields.length == 4) {
                // Fields: speakerID, name, bio, email
                return (T) new Speaker(fields[0], fields[1], fields[2], fields[3]);
            }
        }

        // For feedback.dat
        else if ("feedback.dat".equals(fileName)) {
            if (fields.length == 5) {
                // Fields: feedbackID, attendeeID, sessionID, comment, rating
                return (T) new Feedback(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]));
            }
        }

        return null;  // Return null if CSV line is invalid or unsupported file
    }

    // Initialize default data based on the file name
    private static <T> List<T> initializeDefaultData(String fileName) {
        List<T> defaultData = new ArrayList<>();
        if ("sessions.dat".equals(fileName)) {
            defaultData.add((T) new Session("S1", "Sample Session", "2025-01-01", "09:00 AM", "Room 1"));
        } else if ("attendees.dat".equals(fileName)) {
            defaultData.add((T) new Attendee("Default Attendee", "attendee@example.com"));
        } else if ("feedback.dat".equals(fileName)) {
            // Initialize default feedback (empty list as no feedback is provided yet)
        } else if ("speakers.dat".equals(fileName)) {
            defaultData.add((T) new Speaker("S1", "Default Speaker", "Doctor", "email@email.com"));
        }
        return defaultData;
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

        Conference conference = new Conference("GAF-AI 2025", "2025-01-01", "2025-01-07");

        for (Session session : sessions) {
            conference.openNewSession(session);
        }
        for (Attendee attendee : attendees) {
            conference.registerAttendee(attendee);
        }
        for (Feedback fb : feedback) {
            conference.collectFeedback(fb.getAttendeeID(), fb.getSessionID(), fb.getComment(), fb.getRating());
        }
        for (Speaker speaker : speakers) {
            conference.addSpeaker(speaker);
        }

        return conference;
    }
}
