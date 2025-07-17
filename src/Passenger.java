
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Passenger{
    private String Name;
    private String CNIC;
    private String flightCode;

    public Passenger(String name, String CNIC, String flightCode){
        this.Name = name;
        this.CNIC = CNIC;
        this.flightCode = flightCode;
    }

    public String getName(){
        return Name;
    }
    public String getCNIC(){
        return CNIC;
    }
    public String getFlightCode(){
        return flightCode;
    }
    
    public StringProperty NameProperty(){
        return new SimpleStringProperty(Name);
    }
    public StringProperty CNICProperty(){
        return new SimpleStringProperty(CNIC);
    }
    public StringProperty FlightCodeProperty(){
        return new SimpleStringProperty(flightCode);
    }

    public String toString(){
        return Name+" (CNIC NO. " + CNIC+") - Flight : " + flightCode; 
    }
}