package hw1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CoworkingSpace {
    private final int id;
    private final String type;
    private final double price;
    private final List<Reservation> reservations = new ArrayList<>();

    public CoworkingSpace(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;

    }

    public int getId() { return id; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public List<Reservation> getReservations() { return reservations; }

    @Override
    public String toString() {
        return "CoworkingSpace" +
                "\nid=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", reservations=" + reservations;
    }
}

class Reservation {
    private final int id;
    private final String username;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Reservation(int id, String username, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    @Override
    public String toString() {
        return "Reservation: " +
                "id=" + id +
                ", date=" + date +
                ", time=" + startTime + "-" + endTime;
    }
}