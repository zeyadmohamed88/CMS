package conference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Session> sessionsList;

    // Constructor
    public Schedule() {
        this.sessionsList = new ArrayList<>();
    }

    // Add a session to the schedule
    public void addSession(Session session) {
        if (!sessionsList.contains(session)) {
            sessionsList.add(session);
            System.out.println("Session added to schedule: " + session.getSessionName());
        } else {
            System.out.println("Session already exists in schedule.");
        }
    }

    // Remove a session from the schedule
    public void removeSession(Session session) {
        if (sessionsList.contains(session)) {
            sessionsList.remove(session);
            System.out.println("Session removed from schedule: " + session.getSessionName());
        } else {
            System.out.println("Session not found in schedule.");
        }
    }

    // Get the list of scheduled sessions
    public List<Session> getSessionsList() {
        return sessionsList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Scheduled Sessions:\n");
        for (Session session : sessionsList) {
            builder.append(session.getSessionName()).append("\n");
        }
        return builder.toString();
    }
}
