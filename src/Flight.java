
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Flight {

    private String flightCode;
    private String fromCity;
    private String toCity;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private int bookedSeats;
    private boolean isDelayed;

    public Flight(String flightCode, String fromCity, String toCity,
            String departureTime, String arrivalTime, int totalSeats) {
        this.flightCode = flightCode;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.bookedSeats = 0;
        this.isDelayed = false;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getBookedSeat() {
        return bookedSeats;
    }

    public boolean isDelayed() {
        return isDelayed;
    }

    public StringProperty FlightCodeProperty() {
        return new SimpleStringProperty(flightCode);
    }

    public StringProperty fromCityProperty() {
        return new SimpleStringProperty(fromCity);
    }

    public StringProperty toCityProperty() {
        return new SimpleStringProperty(toCity);
    }

    public StringProperty departureTimeProperty() {
        return new SimpleStringProperty(departureTime);
    }

    public StringProperty arrivalTimeProperty() {
        return new SimpleStringProperty(arrivalTime);
    }

    public IntegerProperty totalSeatsProperty() {
        return new SimpleIntegerProperty(totalSeats);
    }

    public IntegerProperty bookedSeatsProperty() {
        return new SimpleIntegerProperty(bookedSeats);
    }

    public StringProperty statusProperty() {
        return new SimpleStringProperty(
                isDelayed ? "Delayed" : (isFull() ? "Full" : "Available"));
    }

    public boolean bookSeats() {
        if (bookedSeats < totalSeats) {
            bookedSeats++;
            return true;
        }
        return false;
    }

    public void cancelSeat() {
        if (bookedSeats > 0) {
            bookedSeats--;
        }
    }

    public boolean isFull() {
        return bookedSeats >= totalSeats;
    }

    public void setDelayed(boolean delayed) {
        isDelayed = delayed;
    }

    public String toString() {
        return flightCode + " - " + fromCity + " to " + toCity;
    }
}
