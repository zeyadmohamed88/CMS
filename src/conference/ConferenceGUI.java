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

    // Load the conference object containing sessions, speakers, and attendees
    Conference conference = FileUtils.loadConferenceData();

    // Observable list to store sessions, speakers, and attendees
    private ObservableList<Session> sessions = FXCollections.observableArrayList();
    private ObservableList<Speaker> speakers = FXCollections.observableArrayList();
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList();
    private ListView<Session> scheduleListView;

    @Override
    public void start(Stage primaryStage) {
        // Load data at the start of the application
        if (conference != null) {
            sessions.addAll(conference.getListOfSessions());
            speakers.addAll(conference.getListOfSpeakers());
            attendees.addAll(conference.getListOfAttendees());
        } else {
            showAlert("Error", "Conference data could not be loaded.");
        }

        // Start with the login page
        openLoginPage(primaryStage);
    }

    // Save data before closing the application
    private void saveDataBeforeClosing() {
        if (conference != null) {
            FileUtils.saveConferenceData(conference); // Save the loaded conference data to files
        }
    }

    @Override
    public void stop() {
        saveDataBeforeClosing();  // Save data when the application is closed
    }

    // Method to open the login page
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

        // Login as Attendee
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
                openAttendeePage(primaryStage, newAttendee);
            }
        });
        GridPane.setConstraints(loginAsAttendeeButton, 0, 3, 2, 1);
        layout.getChildren().add(loginAsAttendeeButton);

        // Login as Speaker
        Button loginAsSpeakerButton = new Button("Login as Speaker");
        loginAsSpeakerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                Speaker newSpeaker = new Speaker("S" + (speakers.size() + 1), name, email);
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

        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> addSessionToAttendeeSchedule(attendee, scheduleListView));
        layout.getChildren().add(addSessionButton);

        Button removeSessionButton = new Button("Remove Session");
        removeSessionButton.setOnAction(e -> removeSessionFromAttendeeSchedule(attendee, scheduleListView));
        layout.getChildren().add(removeSessionButton);

        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setOnAction(e -> openFeedbackForm(attendee, primaryStage));
        layout.getChildren().add(submitFeedbackButton);

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

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            Stage sessionStage = (Stage) closeButton.getScene().getWindow();
            sessionStage.close();
        });
        layout.getChildren().addAll(label, sessionListView, closeButton);

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

        // Explicitly specify the type parameter <Session>
        ListView<Session> speakerSessionsListView = new ListView<>();

        // Assuming speaker.getSessions() returns a List<Session>, you can set the items for the ListView
        speakerSessionsListView.getItems().setAll(speaker.getSessions());

        layout.getChildren().addAll(label, speakerSessionsListView);

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


    private void addSessionToAttendeeSchedule(Attendee attendee, ListView<Session> scheduleListView) {
        // Logic to add a session to the attendee's schedule
        Session selectedSession = scheduleListView.getSelectionModel().getSelectedItem();
        if (selectedSession != null && !attendee.getSchedule().getSessionsList().contains(selectedSession)) {
            attendee.getSchedule().addSession(selectedSession);
            scheduleListView.setItems(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        } else {
            showAlert("Error", "This session is already added.");
        }
    }

    private void removeSessionFromAttendeeSchedule(Attendee attendee, ListView<Session> scheduleListView) {
        // Logic to remove a session from the attendee's schedule
        Session selectedSession = scheduleListView.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            attendee.getSchedule().removeSession(selectedSession);
            scheduleListView.setItems(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        } else {
            showAlert("Error", "Please select a session to remove.");
        }
    }

    private void openFeedbackForm(Attendee attendee, Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Provide Feedback");

        // Text area for feedback input
        TextArea feedbackArea = new TextArea();
        feedbackArea.setPromptText("Write your feedback...");

        // Rating system (optional, add a simple integer scale from 1 to 5)
        Label ratingLabel = new Label("Rate the session:");
        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5); // Rating options 1 to 5
        ratingComboBox.setValue(5); // Default rating is 5

        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setOnAction(e -> {
            String feedbackText = feedbackArea.getText();
            Integer rating = ratingComboBox.getValue();  // Get the selected rating

            if (feedbackText.isEmpty()) {
                showAlert("Error", "Feedback cannot be empty.");
            } else if (rating == null) {
                showAlert("Error", "Please select a rating.");
            } else {
                // Save the feedback for the current session
                Session currentSession = attendee.getCurrentSession();  // Get the current session the attendee is attending

                // Submit feedback using the Attendee class method
                attendee.submitFeedback(currentSession, feedbackText, rating);

                showAlert("Success", "Feedback submitted successfully!");
                primaryStage.close();  // Close the feedback window after submission
            }
        });

        layout.getChildren().addAll(label, feedbackArea, ratingLabel, ratingComboBox, submitFeedbackButton);

        Scene scene = new Scene(layout, 400, 250);
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback");
        feedbackStage.setScene(scene);
        feedbackStage.show();
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
