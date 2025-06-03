package hw1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class Customer {
    private final List<CoworkingSpace> spaces;
    private int reservationCounter = 1;

    public Customer(List<CoworkingSpace> spaces) {
        this.spaces = spaces;
    }

    public void viewAvailableSpaces() {
        spaces.forEach(System.out::println);
    }

    public void viewAvailableTimeSlots(int spaceId, LocalDate date) {
        spaces.stream()
                .filter(s -> s.getId() == spaceId)
                .findFirst()
                .ifPresentOrElse(
                        space -> {
                            List<TimeSlot> slots = getAvailableTimeSlots(space, date);
                            if (slots.isEmpty()) {
                                System.out.println("No available slots.");
                            } else {
                                slots.forEach(System.out::println);
                            }
                        },
                        () -> System.out.println("Space not found.")
                );
    }

    public void makeReservation(int spaceId, String username, LocalDate date, LocalTime start, LocalTime end) {
        Optional<CoworkingSpace> reservationSpace = spaces.stream()
                .filter(s -> s.getId() == spaceId)
                .findFirst();

        if (reservationSpace.isEmpty()) {
            System.out.println("Space not found.");
            return;
        }

        CoworkingSpace space = reservationSpace.get();

        if (!isAvailable(space, date, start, end)) {
            System.out.println("Time slot not available.");
            return;
        }

        Reservation reservation = new Reservation(reservationCounter++, username, date, start, end);
        space.getReservations().add(reservation);
        System.out.println("Reservation successful. ID: " + reservation.getId());
    }

    public void viewMyReservations(String username) {
        spaces.forEach(space ->
                space.getReservations().stream()
                        .filter(r -> r.getUsername().equals(username))
                        .forEach(System.out::println));
    }

    public void cancelReservation(int reservationId, String username) {
        for (CoworkingSpace space : spaces) {
            Optional<Reservation> toRemove = space.getReservations().stream()
                    .filter(r -> r.getId() == reservationId && r.getUsername().equals(username))
                    .findFirst();

            toRemove.ifPresent(r -> {
                space.getReservations().remove(r);
                System.out.println("Reservation canceled.");
            });
        }
    }

    private boolean isAvailable(CoworkingSpace space, LocalDate date, LocalTime start, LocalTime end) {
        return space.getReservations().stream()
                .filter(r -> r.getDate().equals(date))
                .noneMatch(r -> start.isBefore(r.getEndTime()) && end.isAfter(r.getStartTime()));
    }

    private List<TimeSlot> getAvailableTimeSlots(CoworkingSpace space, LocalDate date) {
        List<Reservation> reservations = space.getReservations().stream()
                .filter(r -> r.getDate().equals(date))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .toList();

        List<TimeSlot> available = new ArrayList<>();
        LocalTime startOfDay = LocalTime.of(7, 0);
        LocalTime endOfDay = LocalTime.of(21, 0);
        LocalTime current = startOfDay;

        for (Reservation r : reservations) {
            if (current.isBefore(r.getStartTime())) {
                available.add(new TimeSlot(current, r.getStartTime()));
            }
            current = r.getEndTime().isAfter(current) ? r.getEndTime() : current;
        }

        if (current.isBefore(endOfDay)) {
            available.add(new TimeSlot(current, endOfDay));
        }

        return available;
    }
}


class TimeSlot {
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }
}