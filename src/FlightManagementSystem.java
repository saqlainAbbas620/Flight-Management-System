
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FlightManagementSystem extends Application {

    private LinkedList<Flight> Flights = new LinkedList<>();
    private TreeSet<Passenger> Passengers = new TreeSet<>(Comparator.comparing(Passenger::getCNIC));

    private TabPane Root = new TabPane();
    private Tab FlightTab = new Tab("Flight Managment");
    private Tab PassengerTab = new Tab("Passenger Management");

//    FLIGHT MANAGNEMENT
    private TextField FlightCodeField = new TextField();
    private TextField FromCityField = new TextField();
    private TextField ToCityField = new TextField();
    private TextField DepartureTimeField = new TextField();
    private TextField ArrivalTimeField = new TextField();
    private TextField TotalSeatsField = new TextField();

    private Button AddFlightButton = new Button("Add Flight");
    private Button CancelFlightButton = new Button("Cancel Flight");
    private Button DelayFlightButton = new Button("Delay Flight");

    private TableView<Flight> flightTable = new TableView<>();

//    PASSENGER MANAGEMENT
    private TextField PassengerNameField = new TextField();
    private TextField CINICField = new TextField();

    private ComboBox<String> FlightComboBox = new ComboBox<>();
    private ArrayList<String> FlightComboCode = new ArrayList<>();
    private Button AddPassengerButton = new Button("Add Passenger");
    private Button CancelTicketButton = new Button("Cancel Ticket");

    private TextField SearchField = new TextField();
    private Button SearchButton = new Button("Search");

    private TableView<Passenger> passengerTable = new TableView<>();

//    Passenger Detail Components
    private Stage detailStage = new Stage();

    private VBox detailBox = new VBox(10);

    private Text detailTitle = new Text("Passenger Details");
    private Text nameDetail = new Text();
    private Text cnicDetail = new Text();
    private Text flightDetail = new Text();
    private Text departureDetail = new Text();
    private Text arrivalDetail = new Text();
    private Text timeDetail = new Text();
    private Text statusDetail = new Text();

    public static void main(String[] args) {
        launch(args);
    }



    private void setFlightTab() {
        GridPane flightGrid = new GridPane();
        flightGrid.setPadding(new Insets(10));
        flightGrid.setHgap(10);
        flightGrid.setVgap(10);

        flightGrid.add(new Label("Flight Code:"), 0, 0);
        flightGrid.add(FlightCodeField, 1, 0);
        flightGrid.add(new Label("Departure City:"), 0, 1);
        flightGrid.add(FromCityField, 1, 1);
        flightGrid.add(new Label("Arrival City:"), 0, 2);
        flightGrid.add(ToCityField, 1, 2);
        flightGrid.add(new Label("Departure Time:"), 0, 3);
        flightGrid.add(DepartureTimeField, 1, 3);
        flightGrid.add(new Label("Arrival Time:"), 0, 4);
        flightGrid.add(ArrivalTimeField, 1, 4);
        flightGrid.add(new Label("Total Seats:"), 0, 5);
        flightGrid.add(TotalSeatsField, 1, 5);

        HBox flightButtonBox = new HBox(10, AddFlightButton, CancelFlightButton, DelayFlightButton);
        flightGrid.add(flightButtonBox, 0, 6, 2, 1);

        setFlightTable();

        flightGrid.add(flightTable, 0, 7, 2, 1);

        AddFlightButton.setOnAction(e -> addFlight());
        CancelFlightButton.setOnAction(e -> cancelFlight());
        DelayFlightButton.setOnAction(e -> delayFlight());

        flightGrid.setAlignment(Pos.CENTER);
        FlightTab.setContent(flightGrid);
    }

//    FLIGHT TABLE
    private void setFlightTable() {
        TableColumn<Flight, String> FlightCodeCol = new TableColumn<>("Flight Code");
        FlightCodeCol.setCellValueFactory(c -> c.getValue().FlightCodeProperty());

        TableColumn<Flight, String> FromCol = new TableColumn<>("From");
        FromCol.setCellValueFactory(c -> c.getValue().fromCityProperty());

        TableColumn<Flight, String> ToCol = new TableColumn<>("To");
        ToCol.setCellValueFactory(c -> c.getValue().toCityProperty());

        TableColumn<Flight, String> DepartureTimeCol = new TableColumn<>("Departure Time");
        DepartureTimeCol.setCellValueFactory(c -> c.getValue().departureTimeProperty());

        TableColumn<Flight, String> ArrivalTimeCol = new TableColumn<>("Arrival Time");
        ArrivalTimeCol.setCellValueFactory(c -> c.getValue().arrivalTimeProperty());

        TableColumn<Flight, Integer> SeatsCol = new TableColumn<>("Seats");
        SeatsCol.setCellValueFactory(c -> c.getValue().totalSeatsProperty().asObject());

        TableColumn<Flight, Integer> BookedCol = new TableColumn<>("Booked");
        BookedCol.setCellValueFactory(c -> c.getValue().bookedSeatsProperty().asObject());

        TableColumn<Flight, String> StatusCol = new TableColumn<>("Status");
        StatusCol.setCellValueFactory(c -> c.getValue().statusProperty());

        flightTable.getColumns().add(FlightCodeCol);
        flightTable.getColumns().add(FromCol);
        flightTable.getColumns().add(ToCol);
        flightTable.getColumns().add(DepartureTimeCol);
        flightTable.getColumns().add(ArrivalTimeCol);
        flightTable.getColumns().add(SeatsCol);
        flightTable.getColumns().add(BookedCol);
        flightTable.getColumns().add(StatusCol);

        flightTable.setItems(FXCollections.observableArrayList(Flights));
    }

    private void addFlight() {
        String FlightCode = FlightCodeField.getText();
        String FromCity = FromCityField.getText();
        String ToCity = ToCityField.getText();
        String DepartureTime = DepartureTimeField.getText();
        String ArrivalTime = ArrivalTimeField.getText();
        int TotalSeats = Integer.parseInt(TotalSeatsField.getText());

        for (Flight f : Flights) {
            if (f.getFlightCode().equalsIgnoreCase(FlightCode)) {
                showAlert("Its Flight already Booked");
                clearFlightFields();
                return;
            }
        }
        Flight flight = new Flight(FlightCode, FromCity, ToCity, DepartureTime, ArrivalTime, TotalSeats);

        Flights.add(flight);
        flightTable.setItems(FXCollections.observableArrayList(Flights));

        updateFlightComboBox();

        clearFlightFields();
    }

    private void cancelFlight() {
        Flight selected = flightTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            ArrayList<Passenger> RemainingPassengers = new ArrayList<>();
            for (Passenger p : Passengers) {
                if (!p.getFlightCode().equals(selected.getFlightCode())) {
                    RemainingPassengers.add(p);
                }
            }
            Passengers.clear();
            Passengers.addAll(RemainingPassengers);
            passengerTable.setItems(FXCollections.observableArrayList(Passengers));

            Flights.remove(selected);
            flightTable.setItems(FXCollections.observableArrayList(Flights));

            updateFlightComboBox();

            showAlert("Flight cancelled and all Ticket removed");
        } else {
            showAlert("Please select a flight to cancel");
        }
    }

    private void delayFlight() {
        Flight Selected = flightTable.getSelectionModel().getSelectedItem();

        if (Selected != null) {
            Selected.setDelayed(true);
            flightTable.refresh();
        } 
    }

    private void clearFlightFields() {
        FlightCodeField.clear();
        FromCityField.clear();
        ToCityField.clear();
        DepartureTimeField.clear();
        ArrivalTimeField.clear();
        TotalSeatsField.clear();
    }

//    PASSENGER TAB
    private void setPassengerTab() {
        GridPane PassengerGrid = new GridPane();
        PassengerGrid.setPadding(new Insets(10));
        PassengerGrid.setHgap(10);
        PassengerGrid.setVgap(10);

        PassengerGrid.add(new Label("Passenger Name:"), 0, 0);
        PassengerGrid.add(PassengerNameField, 1, 0);
        PassengerGrid.add(new Label("CNICNo:"), 0, 1);
        PassengerGrid.add(CINICField, 1, 1);
        PassengerGrid.add(new Label("Flight:"), 0, 2);
        PassengerGrid.add(FlightComboBox, 1, 2);

        HBox passengerButtonBox = new HBox(10, AddPassengerButton, CancelTicketButton);
        PassengerGrid.add(passengerButtonBox, 0, 3, 2, 1);

        HBox SearchBox = new HBox(10, new Label("Search Passenger:"), SearchField, SearchButton);
        PassengerGrid.add(SearchBox, 0, 4, 2, 1);

        setPassengerTable();
        PassengerGrid.add(passengerTable, 0, 5, 2, 1);

        AddPassengerButton.setOnAction(e -> addPassenger());
        CancelTicketButton.setOnAction(e -> cancelTicket());
        SearchButton.setOnAction(e -> searchPassenger());

        updateFlightComboBox();

        PassengerGrid.setAlignment(Pos.CENTER);
        PassengerTab.setContent(PassengerGrid);
    }

    private void setPassengerTable() {
        TableColumn<Passenger, String> NameCol = new TableColumn<>("Name");
        NameCol.setCellValueFactory(data -> data.getValue().NameProperty());

        TableColumn<Passenger, String> CNICCol = new TableColumn<>("CNIC");
        CNICCol.setCellValueFactory(data -> data.getValue().CNICProperty());

        TableColumn<Passenger, String> FlightCodeCol = new TableColumn<>("Flight Code");
        FlightCodeCol.setCellValueFactory(data -> data.getValue().FlightCodeProperty());

        passengerTable.getColumns().add(NameCol);
        passengerTable.getColumns().add(CNICCol);
        passengerTable.getColumns().add(FlightCodeCol);

        passengerTable.setItems(FXCollections.observableArrayList(Passengers));
    }

    private void updateFlightComboBox() {
        FlightComboBox.getItems().clear();
        FlightComboCode.clear();
        for (Flight flight : Flights) {
            FlightComboBox.getItems().add(flight.getFromCity() + " -> " + flight.getToCity());
            FlightComboCode.add(flight.getFlightCode());
        }
    }

    private void showPassengerDetails(Passenger passenger) {
        Flight matchedFlight = null;

        for (Flight f : Flights) {
            if (f.getFlightCode().equals(passenger.getFlightCode())) {
                matchedFlight = f;
                break;
            }
        }
        if (matchedFlight != null) {
            nameDetail.setText("Name: " + passenger.getName());
            cnicDetail.setText("CNIC: " + passenger.getCNIC());

            flightDetail.setText("Flight: " + matchedFlight.getFlightCode());
            departureDetail.setText("From: " + matchedFlight.getFromCity());
            arrivalDetail.setText("To: " + matchedFlight.getToCity());
            timeDetail.setText("Time: " + matchedFlight.getDepartureTime() + " - " + matchedFlight.getArrivalTime());

            String Status;
            if (matchedFlight.isDelayed()) {
                Status = "Flight is Delayed";
            } else if (matchedFlight.isFull()) {
                Status = "Flight is Full";
            } else {
                Status = "Flight is ON Time";
            }

            statusDetail.setText("Status: " + Status);

            detailStage.show();
        } else {
            showAlert("Flight information not found for this Passenger");
        }

    }

    private void setDetailView() {
        detailTitle.setFont(Font.font("Arial,20"));

        detailBox.setPadding(new Insets(20));
        detailBox.setAlignment(Pos.TOP_LEFT);
        detailBox.getChildren().addAll(
                detailTitle,
                new Separator(),
                nameDetail,
                cnicDetail,
                flightDetail,
                new Separator(),
                departureDetail,
                arrivalDetail,
                timeDetail,
                new Separator(),
                statusDetail
        );
        Scene detailScene = new Scene(detailBox, 400, 300);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Passenger Details");

        passengerTable.setRowFactory(e -> {
            TableRow<Passenger> Row = new TableRow<>();
            Row.setOnMouseClicked(Event -> {
                if (Event.getClickCount() == 1 && !Row.isEmpty()) {
                    showPassengerDetails(Row.getItem());
                }
            });
            return Row;
        });

    }

    private void addPassenger() {
        String Name = PassengerNameField.getText();
        String CNIC = CINICField.getText();
        String flightRoute = FlightComboBox.getValue();

        if (Name.isEmpty() || CNIC.isEmpty() || flightRoute == null) {
            showAlert("Please Fill All Fields");
            return;
        }

        Flight selectedFlight = null;
        int index = FlightComboBox.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            for (Flight f : Flights) {
                if (f.getFlightCode().equals(FlightComboCode.get(index))) {
                    selectedFlight = f;
                    break;
                }
            }
        }

        if (selectedFlight == null) {
            showAlert("Selected Flight not found!");
            return;
        }
        if (selectedFlight.isFull()) {
            showAlert("This Flight is Already full!");
            return;
        }

        for (Passenger p : Passengers) {
            if (p.getCNIC().equals(CNIC)) {
                showAlert("Passenger with this Name already exists");
                return;
            }

        }
        Passenger newPassenger = new Passenger(Name, CNIC, FlightComboCode.get(index));
        Passengers.add(newPassenger);
        selectedFlight.bookSeats();

        passengerTable.setItems(FXCollections.observableArrayList(Passengers));
        flightTable.refresh();
        clearPassengerFields();
        showAlert("Passenger Added Successfully!");

    }

    private void cancelTicket() {
        Passenger selected = passengerTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            for (Flight f : Flights) {
                if (f.getFlightCode().equals(selected.getFlightCode())) {
                    f.cancelSeat();
                    break;
                }
            }

            Passengers.remove(selected);

            passengerTable.setItems(FXCollections.observableArrayList(Passengers));

            flightTable.refresh();

            showAlert("Ticket cancelled");
        } else {
            showAlert("Please select a passenger to Cancel ticket");
        }
    }

    private void searchPassenger() {
        String searchTerm = SearchField.getText().toLowerCase();

        if (searchTerm.isEmpty()) {
            passengerTable.setItems(FXCollections.observableArrayList(Passengers));
            return;
        }
        ArrayList<Passenger> results = new ArrayList<>();

        for (Passenger p : Passengers) {
            String Name = p.getName().toLowerCase();
            if (Name.contains(searchTerm)) {
                results.add(p);
            }
        }
        passengerTable.setItems(FXCollections.observableArrayList(results));
    }

    private void clearPassengerFields() {
        PassengerNameField.clear();
        CINICField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
        @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Flight Management System");

        setFlightTab();

        setPassengerTab();

        setDetailView();

        Root.getTabs().addAll(FlightTab, PassengerTab);

        Scene scene = new Scene(Root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
