 package airline;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;

import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Airline extends Application {

    private ArrayList<flights> flight;
    private ListView<flights> flightListView;
    private Label seatAvailabilityLabel;
    private Button reserveButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Text t = new Text("WELCOME TO AIRLINE RESRVATION");
        t.setFont(Font.font("Times new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40));
        t.setFill(Color.RED);
        flight = createSampleFlights();
        Label title = new Label("Available Flights");

        flightListView = new ListView<>(FXCollections.observableArrayList(flight));

        flightListView.setPrefWidth(300);
        flightListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSeatAvailability(newValue);
        });

        Label seatAvailabilityTitleLabel = new Label("Seat Availability:");
        seatAvailabilityLabel = new Label();
        Label reservationLabel = new Label("Reservation:");
        TextField seatNumberTextField = new TextField();
        seatNumberTextField.setPromptText("Enter seat number");
        reserveButton = new Button("Reserve");

        reserveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flights selectedFlight = flightListView.getSelectionModel().getSelectedItem();
                if (selectedFlight != null) {
                    String seatNumber = seatNumberTextField.getText();
                    int x = Integer.parseInt(seatNumber);
                    boolean success = selectedFlight.reserveSeat(x);
                    boolean seatExists = (x <= 300);
                    if (success) {
                        if (seatExists == true) {
                            updateSeatAvailability(selectedFlight);
                            seatNumberTextField.clear();
                            seatNumberTextField.requestFocus();
                            showAlert(Alert.AlertType.INFORMATION, "Reservation Successful",
                                    "Seat " + seatNumber + " reserved successfully.");
                        } else if (!seatExists) {
                            showAlert(Alert.AlertType.WARNING, "Invalid Seat", "Seat " + seatNumber
                                    + " does not exist on the plane. Please choose a valid seat.");
                        }
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Reservation Failed",
                                "Seat " + seatNumber + " is already reserved. Please select another seat.");
                    }
                }
            }
        });
        GridPane gpane = new GridPane();
        gpane.add(t, 0, 0);
        gpane.setPadding(new Insets(10));
        gpane.setHgap(10);
        gpane.setVgap(10);

        gpane.add(title, 0, 1);
        gpane.add(flightListView, 0, 2);
        gpane.add(seatAvailabilityTitleLabel, 0, 3);
        gpane.add(seatAvailabilityLabel, 0, 4);
        gpane.add(reservationLabel, 0, 5);
        gpane.add(seatNumberTextField, 0, 6);
        gpane.add(reserveButton, 0, 7);
        gpane.setAlignment(Pos.TOP_CENTER);
        Image logo = new Image(getClass().getResourceAsStream("plane-9922.png"));
        Scene scene = new Scene(gpane, 1000, 700);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("You can't escape unless you click A");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("A"));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.setTitle("Airline");
        stage.show();
    }

    private ArrayList<flights> createSampleFlights() {
        ArrayList<flights> fli = new ArrayList<>();
        fli.add(new flights("F001", "New York", "London", 800, 8));
        fli.add(new flights("F002", "Lisbon", "Paris", 300, 2));
        fli.add(new flights("F003", "Tokyo", "Munich", 700, 7));
        fli.add(new flights("F004", "Ankara", "Cincinnati", 900, 9));
        fli.add(new flights("F005", "Berlin", "Cairo", 500, 5));
        fli.add(new flights("F006", "Alexandria", "Sydney", 1000, 12));
        fli.add(new flights("F007", "Rio", "Istanbul", 950, 10));
        fli.add(new flights("F008", "Helsinki", "Nairobi", 500, 5));
        fli.add(new flights("F009", "Palermo", "Denver", 950, 9));
        fli.add(new flights("F010", "Moscow", "Stockholm", 400, 3));
        fli.add(new flights("F011", "Oslo", "Marseille", 300, 2));
        return fli;
    }

    private void updateSeatAvailability(flights flight) {
        if (flight != null) {
            String availabilityText = "Available Seats: " + flight.getAvailableSeats();
            seatAvailabilityLabel.setText(availabilityText);
            reserveButton.setDisable(false);
        } else {
            seatAvailabilityLabel.setText("");
            reserveButton.setDisable(true);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
