package hw1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CustomerService {

    private final CoworkingRepository repository;
    private int reservationCounter = 1;

    CustomerService(CoworkingRepository repo) {
        this.repository = repo;
    }


    void viewAvailableSpaces() {
        repository.findAll().forEach(System.out::println);
    }

    void viewAvailableTimeSlots(int spaceId, LocalDate date) {
        repository.findById(spaceId).ifPresentOrElse(space -> {
            List<TimeSlot> availableTimeSlots = getAvailableTimeSlots(space, date);
            if (availableTimeSlots.isEmpty()) {
                System.out.println("No available slots.");
            } else {
                availableTimeSlots.forEach(System.out::println);
            }
        }, () -> System.out.println("Space not found."));
    }

    void viewMyReservations(String user) {
        repository.findAll().forEach(space ->
                space.getReservations().stream()
                        .filter(r -> r.getUsername().equals(user))
                        .forEach(System.out::println));
    }


    void makeReservation(int spaceId, String user,
                         LocalDate date, LocalTime start, LocalTime end) {

        CoworkingSpace space = repository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("Space not found"));

        if (!isAvailable(space, date, start, end)) {
            System.out.println("Time slot not available.");
            return;
        }

        long minutes     = Duration.between(start, end).toMinutes();
        BigDecimal hours = BigDecimal.valueOf(minutes)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        BigDecimal total = space.getHourlyPrice().multiply(hours);

        Reservation r = new Reservation(reservationCounter++, user, date, start, end, total);
        space.getReservations().add(r);
        repository.save(space);                      // фиксируем изменение хранилища

        System.out.println("Reservation successful:");
        System.out.println(r);
    }

    void cancelReservation(int resId, String user) {
        repository.findAll().forEach(space -> space.getReservations().stream()
                .filter(r -> r.getId() == resId && r.getUsername().equals(user))
                .findFirst()
                .ifPresent(r -> {
                    space.getReservations().remove(r);
                    repository.save(space);
                    System.out.println("Reservation canceled.");
                }));
    }

    private boolean isAvailable(CoworkingSpace s,
                                LocalDate d, LocalTime st, LocalTime en) {
        return s.getReservations().stream()
                .filter(r -> r.getDate().equals(d))
                .noneMatch(r -> st.isBefore(r.getEndTime()) && en.isAfter(r.getStartTime()));
    }

    private List<TimeSlot> getAvailableTimeSlots(CoworkingSpace s, LocalDate d) {
        List<Reservation> busy = s.getReservations().stream()
                .filter(r -> r.getDate().equals(d))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .toList();

        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalTime startDay   = LocalTime.of(7, 0);
        LocalTime endDay   = LocalTime.of(21, 0);

        for (Reservation reservation : busy) {
            if (startDay.isBefore(reservation.getStartTime())) {
                timeSlots.add(new TimeSlot(startDay, reservation.getStartTime()));
            }
            startDay = reservation.getEndTime().isAfter(startDay) ? reservation.getEndTime() : startDay;
        }
        if (startDay.isBefore(endDay)) {
            timeSlots.add(new TimeSlot(startDay, endDay));
        }
        return timeSlots;
    }
}

class TimeSlot {
    private final LocalTime start;
    private final LocalTime end;
    TimeSlot(LocalTime start, LocalTime end) { this.start = start; this.end = end; }
    @Override public String toString() { return start + " - " + end; }
}