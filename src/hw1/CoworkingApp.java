package hw1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class CoworkingApp {

    public static void main(String[] args) {
        CoworkingRepository repo = new InMemoryCoworkingRepository();

        AdminService    admin    = new AdminService(repo);
        CustomerService customer = new CustomerService(repo);

        admin.addSpace(1, "Open Space",   new BigDecimal("5.00"));
        admin.addSpace(2, "Private Room", new BigDecimal("15.00"));

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1 – Admin");
            System.out.println("2 – Customer");
            System.out.println("0 – Exit");
            System.out.print("Choice: ");
            int choice = in.nextInt(); in.nextLine();

            switch (choice) {
                case 1 -> adminMenu(in, admin);
                case 2 -> customerMenu(in, customer);
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void adminMenu(Scanner in, AdminService admin) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1 – Add space");
            System.out.println("2 – Remove space");
            System.out.println("3 – List all spaces");
            System.out.println("4 – List all reservations");
            System.out.println("0 – Back");
            System.out.print("Choice: ");
            int c = in.nextInt(); in.nextLine();

            switch (c) {
                case 1 -> {
                    System.out.print("Enter id: ");
                    int id = in.nextInt(); in.nextLine();
                    System.out.print("Enter type: ");
                    String type = in.nextLine();
                    System.out.print("Enter hourly rate: ");
                    BigDecimal rate = new BigDecimal(in.nextLine().trim());
                    admin.addSpace(id, type, rate);
                }
                case 2 -> {
                    System.out.print("Enter id to remove: ");
                    int id = in.nextInt(); in.nextLine();
                    admin.removeSpace(id);
                }
                case 3 -> admin.listSpaces();
                case 4 -> admin.listReservations();
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }


    private static void customerMenu(Scanner in, CustomerService customer) {
        System.out.print("Enter your name: ");
        String user = in.nextLine();

        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1 – Browse spaces");
            System.out.println("2 – View free slots");
            System.out.println("3 – Make reservation");
            System.out.println("4 – My reservations");
            System.out.println("5 – Cancel reservation");
            System.out.println("0 – Back");
            System.out.print("Choice: ");
            int c = in.nextInt(); in.nextLine();

            switch (c) {
                case 1 -> customer.viewAvailableSpaces();
                case 2 -> {
                    System.out.print("Enter space id: ");
                    int id = in.nextInt(); in.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    LocalDate d = LocalDate.parse(in.nextLine());
                    customer.viewAvailableTimeSlots(id, d);
                }
                case 3 -> {
                    System.out.print("Enter space id: ");
                    int id = in.nextInt(); in.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(in.nextLine());
                    System.out.print("Enter start time (HH:mm): ");
                    LocalTime stratTime = LocalTime.parse(in.nextLine());
                    System.out.print("Enter end time (HH:mm): ");
                    LocalTime endTime = LocalTime.parse(in.nextLine());
                    customer.makeReservation(id, user, date, stratTime, endTime);
                }
                case 4 -> customer.viewMyReservations(user);
                case 5 -> {
                    customer.viewMyReservations(user);
                    System.out.print("Enter reservation id to cancel: ");
                    int rid = in.nextInt(); in.nextLine();
                    customer.cancelReservation(rid, user);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}