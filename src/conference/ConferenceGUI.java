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
    // Observable list to store speakers (just a sample, can be extended)
    private ObservableList<Speaker> speakers = FXCollections.observableArrayList();
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList(); // List to store attendees


    @Override
    public void start(Stage primaryStage) {
        // Create some sample speakers
        Speaker speaker1 = new Speaker("S1", "Dr. Abram", "AI and Healthcare Specialist");
        Speaker speaker2 = new Speaker("S2", "Dr. Nancy", "Machine Learning Expert");
        speakers.add(speaker1);
        speakers.add(speaker2);

        // Main layout for the window
        VBox layout = new VBox(10);

        // Label for the title
        Label label = new Label("Welcome to the GAF-AI 2025 Conference Management");

        // Buttons
        Button registerButton = new Button("Register Attendee");
        Button addSessionButton = new Button("Add Session");
        Button showSessionsButton = new Button("Show Sessions");

        // When the "Register Attendee" button is clicked
        registerButton.setOnAction(e -> openRegistrationForm(primaryStage));

        // When the "Add Session" button is clicked
        addSessionButton.setOnAction(e -> openAddSessionForm(primaryStage));

        // When the "Show Sessions" button is clicked
        showSessionsButton.setOnAction(e -> showSessionsList(primaryStage));

        // Add buttons and label to the layout
        layout.getChildren().addAll(label, registerButton, addSessionButton, showSessionsButton);

        // Set the scene and stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Conference Management System");
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the stage
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



    // Method to show an alert (Error messages, etc.)
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

                // Create a new session with sessionCount (5 parameters)
                Session newSession = new Session(sessionName, sessionDate, sessionTime, sessionRoom, sessionCount);
                newSession.setSpeaker(selectedSpeaker);
                sessions.add(newSession); // Add session to the sessions list

                System.out.println("Session Added: " + newSession);
                ((Stage) sessionLayout.getScene().getWindow()).close(); // Close only the add session form
            }
        });

        // Cancel button to close the form
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> ((Stage) sessionLayout.getScene().getWindow()).close());

        sessionLayout.getChildren().addAll(nameLabel, nameField, dateLabel, dateField, timeLabel, timeField, roomLabel, roomField, speakerLabel, speakerComboBox, addSessionButton, cancelButton);

        Scene sessionScene = new Scene(sessionLayout, 300, 350);
        Stage sessionStage = new Stage();
        sessionStage.setTitle("Add Session");
        sessionStage.setScene(sessionScene);
        sessionStage.show();
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



    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
