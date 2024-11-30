package conference;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConferenceGUI extends Application {

    // Observable list to store sessions
    private ObservableList<Session> sessions = FXCollections.observableArrayList();
    // Observable list to store speakers
    private ObservableList<Speaker> speakers = FXCollections.observableArrayList();
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList(); // List to store attendees

    @Override
    public void start(Stage primaryStage) {
        openRoleSelectionPage(primaryStage); // Start role selection
    }

    // Method to open the form to add a new session
    private void openAddSessionForm(Stage primaryStage) {
        VBox sessionLayout = new VBox(10);

        // Labels and TextFields for session details
        Label nameLabel = new Label("Session Name:");
        TextField nameField = new TextField();

        Label dateLabel = new Label("Date (YYYY-MM-DD):");
        TextField dateField = new TextField();

        Label timeLabel = new Label("Time (HH:MM):");
        TextField timeField = new TextField();

        Label roomLabel = new Label("Room:");
        TextField roomField = new TextField();

        // Dropdown for selecting speaker
        Label speakerLabel = new Label("Select Speaker:");
        ComboBox<Speaker> speakerComboBox = new ComboBox<>(speakers);

        // Button to add the session
        Button addSessionButton = new Button("Add Session");

        addSessionButton.setOnAction(e -> {
            String sessionName = nameField.getText();
            String sessionDate = dateField.getText();
            String sessionTime = timeField.getText();
            String sessionRoom = roomField.getText();

            // Basic validation
            if (sessionName.isEmpty() || sessionDate.isEmpty() || sessionTime.isEmpty() || sessionRoom.isEmpty() || speakerComboBox.getValue() == null) {
                showAlert("Error", "All fields must be filled!");
            } else {
                // Generate session ID and create a new session
                int sessionCount = sessions.size();  // Get the current count of sessions
                Speaker selectedSpeaker = speakerComboBox.getValue();

                // Create a new session
                Session newSession = new Session(sessionName, sessionDate, sessionTime, sessionRoom, sessionCount);
                newSession.setSpeaker(selectedSpeaker);
                sessions.add(newSession); // Add session to the sessions list

                // Optionally, you could display the session immediately in the list or any other feedback
                System.out.println("Session Added: " + newSession);
                ((Stage) sessionLayout.getScene().getWindow()).close(); // Close only the add session form
            }
        });

        // Cancel button to close the form
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> ((Stage) sessionLayout.getScene().getWindow()).close());

        sessionLayout.getChildren().addAll(nameLabel, nameField, dateLabel, dateField, timeLabel, timeField, roomLabel, roomField, speakerLabel, speakerComboBox, addSessionButton, cancelButton);

        // Create a new scene for the session form
        Scene sessionScene = new Scene(sessionLayout, 300, 350);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Add Session");
        sessionStage.setScene(sessionScene);
        sessionStage.show();
    }


    // Role Selection Page (First Page)
    private void openRoleSelectionPage(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Select your role:");

        // Buttons for selecting role
        Button speakerButton = new Button("Speaker");
        Button attendeeButton = new Button("Attendee");

        speakerButton.setOnAction(e -> openSpeakerPage(primaryStage)); // Navigate to speaker page
        attendeeButton.setOnAction(e -> openAttendeePage(primaryStage)); // Navigate to attendee page

        layout.getChildren().addAll(label, speakerButton, attendeeButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Role Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Speaker Page (Options for Speaker)
    // Speaker Page (Options for Speaker)
    private void openSpeakerPage(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Speaker Options:");

        // Button to add session (for Speakers only)
        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> openAddSessionForm(primaryStage));

        // Button to show sessions
        Button showSessionsButton = new Button("Show Sessions");
        showSessionsButton.setOnAction(e -> showSpeakerSessions(primaryStage));

        layout.getChildren().addAll(label, addSessionButton, showSessionsButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Speaker Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Attendee Page (Options for Attendee)
    private void openAttendeePage(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Attendee Options:");

        // Button to register attendee
        Button registerButton = new Button("Register Attendee");
        registerButton.setOnAction(e -> openRegistrationForm(primaryStage));

        // Button to show sessions
        Button showSessionsButton = new Button("Show Sessions");
        showSessionsButton.setOnAction(e -> showSessionsList(primaryStage));

        layout.getChildren().addAll(label, registerButton, showSessionsButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Attendee Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Method to open the form to register an attendee
    private void openRegistrationForm(Stage primaryStage) {
        VBox registrationLayout = new VBox(10);

        // Labels and TextFields for name and email
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        // Register Button (will collect the entered information)
        Button registerAttendeeButton = new Button("Register");

        registerAttendeeButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Name and Email are required!");
            } else {
                // Create a new attendee and add to the attendees list
                Attendee newAttendee = new Attendee(name, email);
                newAttendee.setAttendeeID("A" + (attendees.size() + 1));  // Generate a unique ID for the attendee
                attendees.add(newAttendee);  // Add the attendee to the attendees list

                // Display the schedule
                showAttendeeSchedule(newAttendee);

                System.out.println("Attendee Registered: " + name + " | " + email);
                ((Stage) registrationLayout.getScene().getWindow()).close(); // Close only the registration form
            }
        });

        // Cancel Button to go back to the main window
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> ((Stage) registrationLayout.getScene().getWindow()).close());

        // Add all elements to the registration layout
        registrationLayout.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, registerAttendeeButton, cancelButton);

        // Create a new scene for the registration form
        Scene registrationScene = new Scene(registrationLayout, 300, 200);

        // Create a new stage for the registration form (this opens in a new window)
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Register Attendee");
        registrationStage.setScene(registrationScene);
        registrationStage.show();
    }

    // Method to show an attendee's schedule after registration
    // Method to show an attendee's schedule after registration
    private void showAttendeeSchedule(Attendee attendee) {
        VBox layout = new VBox(10);
        Label label = new Label(attendee.getName() + "'s Schedule:");

        // ListView to display the attendee's schedule (sessions)
        ListView<Session> scheduleListView = new ListView<>(FXCollections.observableArrayList(attendee.getSchedule().getSessionsList()));
        layout.getChildren().addAll(label, scheduleListView);

        // Button to add a session to the schedule
        Button addSessionButton = new Button("Add Session");
        addSessionButton.setOnAction(e -> addSessionToAttendeeSchedule(attendee)); // Implement add session functionality
        layout.getChildren().add(addSessionButton);

        // Show the attendee schedule in a new window
        Scene scene = new Scene(layout, 400, 300);
        Stage scheduleStage = new Stage();
        scheduleStage.setTitle("Schedule for " + attendee.getName());
        scheduleStage.setScene(scene);
        scheduleStage.show();
    }


    // Method to add a session to an attendee's schedule
    // Method to add a session to an attendee's schedule
    private void addSessionToAttendeeSchedule(Attendee attendee) {
        // Create a session selection form or allow the attendee to pick a session from available sessions
        if (!sessions.isEmpty()) {
            // Add the first session to the attendee's schedule for demonstration
            attendee.addSessionToSchedule(sessions.get(0)); // Just adding the first session for now

            // Update the ListView to reflect the new schedule
            showAttendeeSchedule(attendee);
        }
    }

    // Method to remove a session from an attendee's schedule
    private void removeSessionFromSchedule(Attendee attendee, Session session) {
        attendee.getSchedule().removeSession(session);  // Remove session from attendee's schedule
        showAttendeeSchedule(attendee);  // Refresh the schedule display
    }



    // Method to show all sessions in a list view
    private void showSessionsList(Stage primaryStage) {
        VBox listLayout = new VBox(10);

        // ListView to display sessions
        ListView<Session> sessionListView = new ListView<>(sessions);
        sessionListView.setItems(sessions);

        // Button to close the window
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());

        // Add session selection functionality
        sessionListView.setOnMouseClicked(e -> {
            Session selectedSession = sessionListView.getSelectionModel().getSelectedItem();
            if (selectedSession != null) {
                showAttendanceWindow(selectedSession);
            }
        });

        listLayout.getChildren().addAll(new Label("Sessions List:"), sessionListView, closeButton);

        Scene listScene = new Scene(listLayout, 400, 300);
        Stage listStage = new Stage();
        listStage.setTitle("Sessions List");
        listStage.setScene(listScene);
        listStage.show();
    }

    // Method to show sessions assigned to a speaker
    private void showSpeakerSessions(Stage primaryStage) {
        VBox layout = new VBox(10);
        Label label = new Label("Speaker Sessions:");

        // Create a list view to show the sessions assigned to the speaker
        ListView<Session> speakerSessionsListView = new ListView<>();
        speakerSessionsListView.setItems(FXCollections.observableArrayList(speakers.get(0).getSessions()));

        layout.getChildren().addAll(label, speakerSessionsListView);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 400, 300);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Speaker's Assigned Sessions");
        sessionStage.setScene(scene);
        sessionStage.show();
    }

    // Helper method to display the attendance marking window
    private void showAttendanceWindow(Session session) {
        VBox attendanceLayout = new VBox(10);

        // Label for the selected session
        Label sessionLabel = new Label("Session: " + session.getSessionName());

        // ListView to display all attendees
        ListView<Attendee> attendeeListView = new ListView<>(attendees);

        // Button to mark attendance
        Button markAttendanceButton = new Button("Mark Attendance");
        markAttendanceButton.setOnAction(e -> {
            Attendee selectedAttendee = attendeeListView.getSelectionModel().getSelectedItem();
            if (selectedAttendee != null) {
                session.markAttendance(selectedAttendee); // Mark attendance in the session
                selectedAttendee.markAttendance(session); // Add session to the attendee's attended sessions
                System.out.println("Attendance marked for: " + selectedAttendee.getName());
            } else {
                showAlert("Error", "Please select an attendee!");
            }
        });

        // ListView to display attendees who are marked present
        Label attendanceLabel = new Label("Attendees Marked Present:");
        ListView<Attendee> attendanceListView = new ListView<>(FXCollections.observableArrayList(session.getAttendeesList()));

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> ((Stage) attendanceLayout.getScene().getWindow()).close());

        // Add all elements to the layout
        attendanceLayout.getChildren().addAll(sessionLabel, attendeeListView, markAttendanceButton, attendanceLabel, attendanceListView, closeButton);

        Scene attendanceScene = new Scene(attendanceLayout, 400, 400);
        Stage attendanceStage = new Stage();
        attendanceStage.setTitle("Mark Attendance");
        attendanceStage.setScene(attendanceScene);
        attendanceStage.show();
    }

    // Collect feedback from an attendee for a session
    private void collectFeedback(Attendee attendee, Session session) {
        VBox feedbackLayout = new VBox(10);

        // Label for rating
        Label ratingLabel = new Label("Rate the session (1 to 5):");
        TextField ratingField = new TextField();

        // Label for comment
        Label commentLabel = new Label("Your Comment:");
        TextArea commentArea = new TextArea();

        // Button to submit feedback
        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setOnAction(e -> {
            int rating = Integer.parseInt(ratingField.getText());
            String comment = commentArea.getText();

            // Create feedback
            Feedback feedback = new Feedback("F" + (session.getFeedbackList().size() + 1), attendee.getAttendeeID(), session.getSessionID(), comment, rating);

            // Add feedback to session
            session.addFeedback(feedback);

            // Optionally, show confirmation
            showAlert("Thank you!", "Your feedback has been submitted.");

            // Close the feedback form
            ((Stage) feedbackLayout.getScene().getWindow()).close();
        });

        feedbackLayout.getChildren().addAll(ratingLabel, ratingField, commentLabel, commentArea, submitFeedbackButton);

        Scene feedbackScene = new Scene(feedbackLayout, 300, 200);
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback for " + session.getSessionName());
        feedbackStage.setScene(feedbackScene);
        feedbackStage.show();
    }

    // Method to show the feedback for a session
    private void showSessionFeedback(Session session) {
        VBox layout = new VBox(10);
        Label label = new Label("Feedback for Session: " + session.getSessionName());

        // ListView to display all feedback
        ListView<Feedback> feedbackListView = new ListView<>(FXCollections.observableArrayList(session.getFeedbackList()));
        layout.getChildren().addAll(label, feedbackListView);

        // Show feedback in a new window
        Scene scene = new Scene(layout, 400, 300);
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Session Feedback");
        feedbackStage.setScene(scene);
        feedbackStage.show();
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
