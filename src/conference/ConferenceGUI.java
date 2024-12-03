package conference;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConferenceGUI extends Application {

    // Observable list to store sessions, speakers, and attendees
    private ObservableList<Session> sessions = FXCollections.observableArrayList();
    private ObservableList<Speaker> speakers = FXCollections.observableArrayList();
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList();
    private ListView<Session> scheduleListView;

    @Override
    public void start(Stage primaryStage) {
        openLoginPage(primaryStage); // Start the login page
    }

    // Method to open the login page
    private void openLoginPage(Stage primaryStage) {
        GridPane layout = new GridPane();
        layout.setHgap(10);  // Horizontal gap between columns
        layout.setVgap(15);  // Vertical gap between rows
        layout.setPadding(new javafx.geometry.Insets(20)); // Add padding for better layout

        Label label = new Label("Please login:");
        GridPane.setConstraints(label, 0, 0, 2, 1);  // Span over two columns for alignment
        layout.getChildren().add(label);

        // Labels and TextFields for Name and Email
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

        // Button to login as Attendee
        Button loginAsAttendeeButton = new Button("Login as Attendee");
        loginAsAttendeeButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            // Basic validation
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                // Create new Attendee and set details
                Attendee newAttendee = new Attendee(name, email);
                newAttendee.setAttendeeID("A" + (attendees.size() + 1));  // Generate a unique ID for the attendee
                attendees.add(newAttendee);  // Add to attendees list

                // Navigate to Attendee Dashboard
                openAttendeePage(primaryStage, newAttendee); // Open Attendee Dashboard
            }
        });
        GridPane.setConstraints(loginAsAttendeeButton, 0, 3, 2, 1); // Span both columns for centering
        layout.getChildren().add(loginAsAttendeeButton);

        // Button to login as Speaker
        Button loginAsSpeakerButton = new Button("Login as Speaker");
        loginAsSpeakerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            // Basic validation
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                // Create new Speaker and set details
                Speaker newSpeaker = new Speaker("S" + (speakers.size() + 1), name, email);
                speakers.add(newSpeaker);  // Add to speakers list

                // Navigate to Speaker Dashboard
                openSpeakerPage(primaryStage, newSpeaker); // Open Speaker Dashboard
            }
        });
        GridPane.setConstraints(loginAsSpeakerButton, 0, 4, 2, 1); // Span both columns for centering
        layout.getChildren().add(loginAsSpeakerButton);

        // Set the scene for login page
        Scene scene = new Scene(layout, 300, 250);  // Increased height for better button visibility
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to open the Attendee Dashboard after login
    private void openAttendeePage(Stage primaryStage, Attendee attendee) {
        VBox layout = new VBox(10);
        Label label = new Label("Attendee Dashboard");

        // Button to view and manage the attendee's schedule
        Button viewScheduleButton = new Button("View Schedule");
        viewScheduleButton.setOnAction(e -> showAttendeeSchedule(attendee, primaryStage));  // Show attendee's schedule
        layout.getChildren().add(viewScheduleButton);

        // Button to view available sessions
        Button viewSessionsButton = new Button("View Available Sessions");
        viewSessionsButton.setOnAction(e -> showSessionsList(primaryStage)); // Show available sessions
        layout.getChildren().add(viewSessionsButton);

        // Back button to go back to login page
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openLoginPage(primaryStage));  // Navigate back to the login page
        layout.getChildren().add(backButton);

        // Set the scene for the Attendee Dashboard
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Attendee Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to open the Speaker Dashboard after login
    private void openSpeakerPage(Stage primaryStage, Speaker speaker) {
        VBox layout = new VBox(10);
        Label label = new Label("Speaker Dashboard");

        // Button to add a session
        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> openAddSessionForm(primaryStage)); // Open the form to add a new session
        layout.getChildren().add(addSessionButton);

        // Button to view the sessions assigned to the speaker
        Button viewSessionsButton = new Button("View My Sessions");
        viewSessionsButton.setOnAction(e -> showSpeakerSessions(primaryStage, speaker)); // Show the speaker's assigned sessions
        layout.getChildren().add(viewSessionsButton);

        // Back button to go back to login page
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openLoginPage(primaryStage)); // Navigate back to the login page
        layout.getChildren().add(backButton);

        // Set the scene for the Speaker Dashboard
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Speaker Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show an attendee's schedule after registration
    private void showAttendeeSchedule(Attendee attendee, Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label(attendee.getName() + "'s Schedule:");

        // ListView to display the attendee's schedule (sessions)
        ListView<Session> scheduleListView = new ListView<>(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        layout.getChildren().addAll(label, scheduleListView);

        // Button to add a session to the schedule
        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> addSessionToAttendeeSchedule(attendee, scheduleListView)); // Add session functionality
        layout.getChildren().add(addSessionButton);

        // Button to remove a session from the schedule
        Button removeSessionButton = new Button("Remove Session");
        removeSessionButton.setOnAction(e -> removeSessionFromAttendeeSchedule(attendee, scheduleListView)); // Remove session functionality
        layout.getChildren().add(removeSessionButton);

        // Button to submit feedback for a session
        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setOnAction(e -> openFeedbackForm(attendee, primaryStage)); // Open feedback form
        layout.getChildren().add(submitFeedbackButton);

        // Show the attendee schedule in a new window
        Scene scene = new Scene(layout, 400, 300);
        Stage scheduleStage = new Stage();
        scheduleStage.setTitle("Schedule for " + attendee.getName());
        scheduleStage.setScene(scene);
        scheduleStage.show();
    }

    // Method to open the feedback form for the attendee
    // In the Attendee Dashboard, where attendees can submit feedback
    private void openFeedbackForm(Attendee attendee, Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Submit Feedback");

        // TextField for the rating (1 to 5)
        Label ratingLabel = new Label("Rating (1-5):");
        TextField ratingField = new TextField();
        layout.getChildren().addAll(ratingLabel, ratingField);

        // TextArea for the comment
        Label commentLabel = new Label("Comment:");
        TextArea commentArea = new TextArea();
        layout.getChildren().addAll(commentLabel, commentArea);

        // Button to submit feedback
        Button submitButton = new Button("Submit Feedback");
        submitButton.setOnAction(e -> {
            // Get the selected session from the attendee's schedule
            Session selectedSession = scheduleListView.getSelectionModel().getSelectedItem();

            if (selectedSession != null) {
                // Get the rating and comment from the input fields
                int rating = Integer.parseInt(ratingField.getText()); // Ensure it’s a valid integer
                String comment = commentArea.getText(); // Get the comment text

                // Submit feedback to the selected session
                attendee.submitFeedback(selectedSession, comment, rating);

                // Show a success message
                showAlert("Feedback Submitted", "Your feedback has been successfully submitted.");

                // Close the feedback form
                ((Stage) layout.getScene().getWindow()).close();
            } else {
                // Handle the case where no session was selected
                showAlert("Error", "Please select a session first.");
            }
        });

        layout.getChildren().add(submitButton);

        // Create a new scene for the feedback form
        Scene scene = new Scene(layout, 300, 250);
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback Form");
        feedbackStage.setScene(scene);
        feedbackStage.show();
    }



    // Method to add a session to an attendee's schedule
    private void addSessionToAttendeeSchedule(Attendee attendee, ListView<Session> scheduleListView) {
        if (!sessions.isEmpty()) {
            attendee.addSessionToSchedule(sessions.get(0)); // Just adding the first session for now

            // Update the ListView to reflect the new schedule
            scheduleListView.setItems(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        }
    }

    // Method to remove a session from an attendee's schedule
    private void removeSessionFromAttendeeSchedule(Attendee attendee, ListView<Session> scheduleListView) {
        if (!attendee.getSchedule().getSessionsList().isEmpty()) {
            attendee.getSchedule().removeSession(attendee.getSchedule().getSessionsList().get(0)); // Removing the first session for demo

            // Update the ListView to reflect the new schedule
            scheduleListView.setItems(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        }
    }

    // Method to show all available sessions
    private void showSessionsList(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Available Sessions");

        // ListView to display sessions
        ListView<Session> sessionListView = new ListView<>(sessions);
        sessionListView.setItems(sessions);

        // Button to close the session list
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            Stage sessionStage = (Stage) closeButton.getScene().getWindow(); // Get the current window (child stage)
            sessionStage.close(); // Close the current window
        });
        layout.getChildren().addAll(label, sessionListView, closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Sessions List");
        sessionStage.setScene(scene);
        sessionStage.show();
    }


    // Method to show the sessions assigned to a speaker
    private void showSpeakerSessions(Stage primaryStage, Speaker speaker) {
        VBox layout = new VBox(10);
        Label label = new Label("Speaker's Sessions:");

        // Create a list view to show the sessions assigned to the speaker
        ListView<Session> speakerSessionsListView = new ListView<>();
        speakerSessionsListView.setItems(FXCollections.observableArrayList(speaker.getSessions()));

        // Add a button to view feedback for each session
        Button viewFeedbackButton = new Button("View Feedback");
        viewFeedbackButton.setOnAction(e -> openSessionFeedbackForm(primaryStage, speakerSessionsListView.getSelectionModel().getSelectedItem()));
        layout.getChildren().addAll(label, speakerSessionsListView, viewFeedbackButton);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Speaker's Sessions");
        sessionStage.setScene(scene);
        sessionStage.show();
    }

    // Method to view feedback for a selected session
    private void openSessionFeedbackForm(Stage primaryStage, Session session) {
        VBox layout = new VBox(10);
        Label label = new Label("Feedback for Session: " + session.getSessionName());

        // ListView to display all feedback for the session
        ListView<Feedback> feedbackListView = new ListView<>(FXCollections.observableArrayList(session.getFeedbackList()));
        layout.getChildren().addAll(label, feedbackListView);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Session Feedback");
        feedbackStage.setScene(scene);
        feedbackStage.show();
    }


    // Method to open the form to add a session (Speaker Dashboard)
    private void openAddSessionForm(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Add Session");

        // TextFields for session details
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

            // Basic validation
            if (sessionName.isEmpty() || sessionDate.isEmpty() || sessionTime.isEmpty() || sessionRoom.isEmpty()) {
                showAlert("Error", "All fields are required!");
            } else {
                // Create a new session and add it to the list
                Session newSession = new Session(sessionName, sessionDate, sessionTime, sessionRoom, sessions.size());
                sessions.add(newSession);

                // Notify speaker of session creation
                showAlert("Success", "Session added successfully!");
                ((Stage) layout.getScene().getWindow()).close(); // Close the add session form
            }
        });

        layout.getChildren().addAll(label, nameLabel, nameField, dateLabel, dateField, timeLabel, timeField, roomLabel, roomField, addSessionButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage addSessionStage = new Stage();
        addSessionStage.setTitle("Add Session");
        addSessionStage.setScene(scene);
        addSessionStage.show();
    }

    // Method to show an alert (Error messages, etc.)
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
