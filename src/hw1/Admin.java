package hw1;

import java.util.List;

class Admin {
    private final List<CoworkingSpace> spaces;

    public Admin(List<CoworkingSpace> spaces) {
        this.spaces = spaces;
    }

    public void addCoworkingSpace(int id, String type, double price) {
        if (spaces.stream().anyMatch(s -> s.getId() == id)) {
            System.out.println("Space with this id already exists.");
            return;
        }
        spaces.add(new CoworkingSpace(id, type, price));
        System.out.println("Space added.");
    }

    public void removeCoworkingSpace(int id) {
        spaces.removeIf(s -> s.getId() == id);
        System.out.println("Space removed (if it existed).");
    }

    public void viewAllReservations() {
        spaces.forEach(space -> {
            System.out.println(space);
            space.getReservations().forEach(System.out::println);
        });
    }
}