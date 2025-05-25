package airline;

import java.util.ArrayList;

public class flights {

    private String id;
    private String destination;
    private String takeOff;
    private double price;
    private int duration;
    private ArrayList<Integer> reservedSeats;
 

    public flights(String id, String d, String t, double p, int dur) {
        this.id = id;
        destination = d;
        takeOff = t;
        price = p;
        duration = dur;
        this.reservedSeats = new ArrayList<>();
        

    }

    public String getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public int getAvailableSeats() {
        return 300 - reservedSeats.size();
    }

    public boolean reserveSeat(int seatNumber) {
     
        if (reservedSeats.contains(seatNumber)) {
    
            return false;
        }
        reservedSeats.add(seatNumber);
        return true;
    }

    @Override
    public String toString() {
        return id + " : " + takeOff + " to " + destination + "\t\t\t\t\t\t Price : " + price + "$" + "\t\t\t\t\t\t\tDuration : "
                + duration + " hours";
    }
}
