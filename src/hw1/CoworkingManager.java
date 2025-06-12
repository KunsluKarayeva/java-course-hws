package hw1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CoworkingManager {
    public static void main(String[] args) {
        List<CoworkingSpace> spaces = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        Admin admin = new Admin(spaces);
        Customer customer = new Customer(spaces);

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1 - Admin Login");
            System.out.println("2 - User Login");
            System.out.println("0 - Exit");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1 -> adminMenu(in, admin);
                case 2 -> customerMenu(in, customer);
                case 0 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void adminMenu(Scanner in, Admin admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1 - Add coworking space");
            System.out.println("2 - Remove coworking space");
            System.out.println("3 - View all reservations");
            System.out.println("0 - Back");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter id: ");
                    int id = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter type: ");
                    String type = in.nextLine();
                    System.out.print("Enter price: ");
                    double price = in.nextDouble();
                    in.nextLine();
                    admin.addCoworkingSpace(id, type, price);
                }
                case 2 -> {
                    System.out.print("Enter id to remove: ");
                    int id = in.nextInt();
                    in.nextLine();
                    admin.removeCoworkingSpace(id);
                }
                case 3 -> admin.viewAllReservations();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void customerMenu(Scanner in, Customer customer) {
        System.out.print("Enter your name: ");
        String username = in.nextLine();

        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1 - Browse available spaces");
            System.out.println("2 - View available time slots");
            System.out.println("3 - Make reservation");
            System.out.println("4 - View my reservations");
            System.out.println("5 - Cancel reservation");
            System.out.println("0 - Back");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1 -> customer.viewAvailableSpaces();
                case 2 -> {
                    System.out.print("Enter space id: ");
                    int spaceId = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(in.nextLine());
                    customer.viewAvailableTimeSlots(spaceId, date);
                }
                case 3 -> {
                    System.out.print("Enter space id: ");
                    int spaceId = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(in.nextLine());
                    System.out.print("Enter start time (HH:mm): ");
                    LocalTime start = LocalTime.parse(in.nextLine());
                    System.out.print("Enter end time (HH:mm): ");
                    LocalTime end = LocalTime.parse(in.nextLine());
                    customer.makeReservation(spaceId, username, date, start, end);
                }
                case 4 -> customer.viewMyReservations(username);
                case 5 -> {
                    customer.viewMyReservations(username);
                    System.out.print("Enter reservation id to cancel: ");
                    int reservationId = in.nextInt();
                    in.nextLine();
                    customer.cancelReservation(reservationId, username);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
