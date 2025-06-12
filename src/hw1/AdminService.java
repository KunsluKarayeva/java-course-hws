package hw1;

import java.math.BigDecimal;

public class AdminService {

    private final CoworkingRepository repository;

    public AdminService(CoworkingRepository repository) {
        this.repository = repository;
    }


    public void addSpace(int id, String type, BigDecimal rate) {
        if (repository.findById(id).isPresent()) {
            System.out.println("Space with this ID already exists.");
        } else {
            repository.save(new CoworkingSpace(id, type, rate));
            System.out.println("Space added.");
        }
    }


    public void removeSpace(int id) {
        repository.deleteById(id);
        System.out.println("Space removed (if it existed).");
    }


    public void listSpaces() {
        repository.findAll().forEach(System.out::println);
    }


    public void listReservations() {
        repository.findAll().forEach(space -> {
            System.out.println(space);
            space.getReservations().forEach(System.out::println);
        });
    }
}