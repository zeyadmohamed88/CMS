package conference;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ConferenceGUI extends Application {

    Conference conference = FileUtils.loadConferenceData();
    private ObservableList<Session> sessions = FXCollections.observableArrayList();
    private ObservableList<Speaker> speakers = FXCollections.observableArrayList();
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList();
    private ListView<Session> scheduleListView;
    private Attendee currentAttendee;

    @Override
    public void start(Stage primaryStage) {
        if (conference != null) {
            sessions.addAll(conference.getListOfSessions());
            speakers.addAll(conference.getListOfSpeakers());
            attendees.addAll(conference.getListOfAttendees());
        } else {
            showAlert("Error", "Conference data could not be loaded.");
        }

        openLoginPage(primaryStage);
    }

    private void saveDataBeforeClosing() {
        if (conference != null) {
            FileUtils.saveConferenceData(conference);
        }
    }

    @Override
    public void stop() {
        saveDataBeforeClosing();
    }

    private void openLoginPage(Stage primaryStage) {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(15);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label label = new Label("Please login:");
        GridPane.setConstraints(label, 0, 0, 2, 1);
        layout.getChildren().add(label);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        GridPane.setConstraints(nameLabel, 0, 1);
        GridPane.setConstraints(nameField, 1, 1);
        layout.getChildren().addAll(nameLabel, nameField);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        GridPane.setConstraints(emailLabel, 0, 2);
        GridPane.setConstraints(emailField, 1, 2);
        layout.getChildren().addAll(emailLabel, emailField);

        Button loginAsAttendeeButton = new Button("Login as Attendee");
        loginAsAttendeeButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                Attendee newAttendee = new Attendee(name, email);
                newAttendee.setAttendeeID("A" + (attendees.size() + 1));
                attendees.add(newAttendee);
                currentAttendee = newAttendee;
                openAttendeePage(primaryStage, newAttendee);
            }
        });
        GridPane.setConstraints(loginAsAttendeeButton, 0, 3, 2, 1);
        layout.getChildren().add(loginAsAttendeeButton);

        Button loginAsSpeakerButton = new Button("Login as Speaker");
        loginAsSpeakerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                String bio = "";  // You can leave it empty or have default text
                Speaker newSpeaker = new Speaker("S" + (speakers.size() + 1), name, bio, email);
                speakers.add(newSpeaker);
                openSpeakerPage(primaryStage, newSpeaker);
            }
        });
        GridPane.setConstraints(loginAsSpeakerButton, 0, 4, 2, 1);
        layout.getChildren().add(loginAsSpeakerButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAttendeePage(Stage primaryStage, Attendee attendee) {
        VBox layout = new VBox(10);
        Label label = new Label("Attendee Dashboard");

        Button viewScheduleButton = new Button("View Schedule");
        viewScheduleButton.setOnAction(e -> showAttendeeSchedule(attendee, primaryStage));
        layout.getChildren().add(viewScheduleButton);

        Button viewSessionsButton = new Button("View Available Sessions");
        viewSessionsButton.setOnAction(e -> showSessionsList(primaryStage));
        layout.getChildren().add(viewSessionsButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openLoginPage(primaryStage));
        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Attendee Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openSpeakerPage(Stage primaryStage, Speaker speaker) {
        VBox layout = new VBox(10);
        Label label = new Label("Speaker Dashboard");

        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> openAddSessionForm(primaryStage));
        layout.getChildren().add(addSessionButton);

        Button viewSessionsButton = new Button("View My Sessions");
        viewSessionsButton.setOnAction(e -> showSpeakerSessions(primaryStage, speaker));
        layout.getChildren().add(viewSessionsButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openLoginPage(primaryStage));
        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Speaker Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAttendeeSchedule(Attendee attendee, Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label(attendee.getName() + "'s Schedule:");

        scheduleListView = new ListView<>(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        layout.getChildren().addAll(label, scheduleListView);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openAttendeePage(primaryStage, attendee));
        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage scheduleStage = new Stage();
        scheduleStage.setTitle("Schedule for " + attendee.getName());
        scheduleStage.setScene(scene);
        scheduleStage.show();
    }

    private void showSessionsList(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Available Sessions");

        ListView<Session> sessionListView = new ListView<>(sessions);
        sessionListView.setItems(sessions);

        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> {
            Session selectedSession = sessionListView.getSelectionModel().getSelectedItem();
            if (selectedSession != null) {
                addSessionToAttendeeSchedule(currentAttendee, sessionListView);
            } else {
                showAlert("Error", "Please select a session to add.");
            }
        });

        Button removeSessionButton = new Button("Remove Session");
        removeSessionButton.setOnAction(e -> {
            Session selectedSession = sessionListView.getSelectionModel().getSelectedItem();
            if (selectedSession != null) {
                removeSessionFromAttendeeSchedule(currentAttendee, sessionListView);
            } else {
                showAlert("Error", "Please select a session to remove.");
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            Stage sessionStage = (Stage) closeButton.getScene().getWindow();
            sessionStage.close();
        });

        layout.getChildren().addAll(label, sessionListView, addSessionButton, removeSessionButton, closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Sessions List");
        sessionStage.setScene(scene);
        sessionStage.show();
    }

    private void openAddSessionForm(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Add Session");

        Label nameLabel = new Label("Session Name:");
        TextField nameField = new TextField();
        Label dateLabel = new Label("Session Date:");
        TextField dateField = new TextField();
        Label timeLabel = new Label("Session Time:");
        TextField timeField = new TextField();
        Label roomLabel = new Label("Session Room:");
        TextField roomField = new TextField();

        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> {
            String sessionName = nameField.getText();
            String sessionDate = dateField.getText();
            String sessionTime = timeField.getText();
            String sessionRoom = roomField.getText();
            if (sessionName.isEmpty() || sessionDate.isEmpty() || sessionTime.isEmpty() || sessionRoom.isEmpty()) {
                showAlert("Error", "All fields are required.");
            } else {
                Session newSession = new Session(sessionName, sessionDate, sessionTime, sessionRoom, sessions.size());
                sessions.add(newSession);
                conference.addSession(newSession);
                showAlert("Success", "Session added successfully!");
            }
        });

        layout.getChildren().addAll(nameLabel, nameField, dateLabel, dateField, timeLabel, timeField, roomLabel, roomField, addSessionButton);

        Scene scene = new Scene(layout, 300, 300);
        Stage addSessionStage = new Stage();
        addSessionStage.setTitle("Add Session");
        addSessionStage.setScene(scene);
        addSessionStage.show();
    }

    private void showSpeakerSessions(Stage primaryStage, Speaker speaker) {
        VBox layout = new VBox(10);
        Label label = new Label("Sessions of " + speaker.getName());

        ListView<Session> speakerSessionsListView = new ListView<>();
        speakerSessionsListView.getItems().setAll(speaker.getSessions());

        Button generateCertificatesButton = new Button("Generate Certificates for Attendees");
        generateCertificatesButton.setOnAction(e -> {
            Session selectedSession = speakerSessionsListView.getSelectionModel().getSelectedItem();
            if (selectedSession != null) {
                List<Attendee> attendeesOfSession = selectedSession.getAttendees();

                if (attendeesOfSession.isEmpty()) {
                    showAlert("Error", "No attendees found for this session.");
                    return;
                }

                for (Attendee attendee : attendeesOfSession) {
                    Certificate certificate = new Certificate();
                    certificate.setCertificateID("C" + (attendeesOfSession.indexOf(attendee) + 1));
                    certificate.setAttendeeID(attendee.getAttendeeID());
                    certificate.setConferenceName("Sample Conference");
                    certificate.setIssueDate(java.time.LocalDate.now().toString());
                    Session session = new Session("AI and Machine Learning", "2025-01-01", "10:00 AM", "Room 101", 5);
                    certificate.generateCertificate(attendee, session);

                }

                showAlert("Success", "Certificates generated for all attendees of the session.");
            } else {
                showAlert("Error", "Please select a session first.");
            }
        });

        layout.getChildren().addAll(label, speakerSessionsListView, generateCertificatesButton);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            Stage sessionStage = (Stage) closeButton.getScene().getWindow();
            sessionStage.close();
        });
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Speaker Sessions");
        sessionStage.setScene(scene);
        sessionStage.show();
    }

    private void addSessionToAttendeeSchedule(Attendee attendee, ListView<Session> sessionListView) {
        Session selectedSession = sessionListView.getSelectionModel().getSelectedItem();
        if (selectedSession != null && !attendee.getSchedule().getSessionsList().contains(selectedSession)) {
            attendee.getSchedule().addSession(selectedSession);

            // Check eligibility for certificate
            if (attendee.getSchedule().getSessionsList().contains(selectedSession)) {
                showAlert("Success", "You are now eligible for a certificate for this session!");
            }
        } else {
            showAlert("Error", "This session is already added.");
        }
    }


    private void removeSessionFromAttendeeSchedule(Attendee attendee, ListView<Session> sessionListView) {
        Session selectedSession = sessionListView.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            attendee.getSchedule().removeSession(selectedSession);
            sessionListView.setItems(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        } else {
            showAlert("Error", "Please select a session to remove.");
        }
    }

    private boolean isEmailUnique(String email) {
        for (Attendee attendee : attendees) {
            if (attendee.getEmail().equals(email)) {
                return false;
            }
        }
        for (Speaker speaker : speakers) {
            if (speaker.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public void handleLogin(String email, String username, String password) {
        if (!isEmailUnique(email)) {
            showAlert("Error", "Email already in use.");
            return;
        }

        // Proceed with login logic: check credentials, load data, etc.
        boolean isValidUser = authenticateUser(email, password);

        if (isValidUser) {
            // Successful login: load relevant data and transition to next screen
            System.out.println("Login successful for: " + email);
            // Proceed with user-specific functionality (sessions, certificates, etc.)
        } else {
            // Invalid credentials, show alert
            showAlert("Error", "Invalid credentials.");
        }
    }

    private boolean authenticateUser(String email, String password) {
        // Here, check the credentials from your list or backend
        // You can compare the email and password with the stored values for the respective type (attendee/speaker)
        return true; // For simplicity, assume valid user
    }


    private Attendee getCurrentAttendee() {
        return currentAttendee;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
