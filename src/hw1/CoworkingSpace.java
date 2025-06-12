package hw1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


public class CoworkingSpace {
    private final int id;
    private final String type;
    private final BigDecimal hourlyPrice;
    private final List<Reservation> reservations = new ArrayList<>();

    public CoworkingSpace(int id, String type, BigDecimal hourlyPrice) {
        this.id = id;
        this.type = type;
        this.hourlyPrice = hourlyPrice;
    }

    public int getId() {
        return this.id;
    }

    public BigDecimal getHourlyPrice() {
        return this.hourlyPrice;
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CoworkingSpace{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=$" + hourlyPrice +
                '}';
    }
}

class Reservation {
    private final int id;
    private final String username;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final BigDecimal price;

    public Reservation(int id, String username, LocalDate date, LocalTime startTime, LocalTime endTime, BigDecimal price) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", Time=" + startTime + "-" + endTime +
                ", reservationPrice=" + price +
                '}';
    }
}