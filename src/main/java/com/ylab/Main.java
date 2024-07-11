package com.ylab;

import com.ylab.model.Booking;
import com.ylab.model.ConferenceRoom;
import com.ylab.model.User;
import com.ylab.model.Workspace;
import com.ylab.repository.impl.BookingRepositoryImpl;
import com.ylab.repository.impl.ResourceRepositoryImpl;
import com.ylab.repository.impl.UserRepositoryImpl;
import com.ylab.service.impl.BookingServiceImpl;
import com.ylab.service.impl.ResourceServiceImpl;
import com.ylab.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Main class for the coworking service application.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static UserServiceImpl userServiceImpl = new UserServiceImpl(new UserRepositoryImpl());
    private static ResourceServiceImpl resourceServiceImpl = new ResourceServiceImpl(new ResourceRepositoryImpl());
    private static BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(new BookingRepositoryImpl());
    private static User currentUser;

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        LiquibaseStarter.main(args);
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void showMainMenu() {
        System.out.println("1. View available workspaces");
        System.out.println("2. View available conference rooms");
        System.out.println("3. Book a workspace");
        System.out.println("4. Book a conference room");
        System.out.println("5. Cancel a booking");
        System.out.println("6. View all bookings");
        System.out.println("7. Add new workspace");
        System.out.println("8. Add new conference room");
        System.out.println("9. Update workspace");
        System.out.println("10. Update conference room");
        System.out.println("11. Delete workspace");
        System.out.println("12. Delete conference room");
        System.out.println("13. Filter bookings");
        System.out.println("14. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAvailableWorkspaces();
                break;
            case 2:
                viewAvailableConferenceRooms();
                break;
            case 3:
                bookWorkspace();
                break;
            case 4:
                bookConferenceRoom();
                break;
            case 5:
                cancelBooking();
                break;
            case 6:
                viewAllBookings();
                break;
            case 7:
                addNewWorkspace();
                break;
            case 8:
                addNewConferenceRoom();
                break;
            case 9:
                updateWorkspace();
                break;
            case 10:
                updateConferenceRoom();
                break;
            case 11:
                deleteWorkspace();
                break;
            case 12:
                deleteConferenceRoom();
                break;
            case 13:
                filterBookings();
                break;
            case 14:
                currentUser = null;
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (userServiceImpl.authenticate(username, password)) {
            currentUser = userServiceImpl.getUser(username);
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        try {
            User user = new User(null, username, password, name);
            userServiceImpl.registerUser(user);
            System.out.println("Registration successful. You can now log in.");
        } catch (IllegalArgumentException e) {
            System.out.println("Username already exists.");
        }
    }

    private static void viewAvailableWorkspaces() {
        for (Workspace workspace : resourceServiceImpl.getAllWorkspaces().values()) {
            System.out.println("ID: " + workspace.getId() + ", Name: " + workspace.getName());
        }
    }

    private static void viewAvailableConferenceRooms() {
        for (ConferenceRoom room : resourceServiceImpl.getAllConferenceRooms().values()) {
            System.out.println("ID: " + room.getId() + ", Name: " + room.getName());
        }
    }

    private static void bookWorkspace() {
        System.out.print("Workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Start time (yyyy-MM-dd HH:mm): ");
        String startTimeStr = scanner.nextLine();
        System.out.print("End time (yyyy-MM-dd HH:mm): ");
        String endTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        try {
            Booking booking = new Booking(null, currentUser.getUsername(), id, startTime, endTime, true);
            bookingServiceImpl.bookResource(booking);
            System.out.println("Workspace booked successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Time slot is already booked.");
        }
    }

    private static void bookConferenceRoom() {
        System.out.print("Conference Room ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Start time (yyyy-MM-dd HH:mm): ");
        String startTimeStr = scanner.nextLine();
        System.out.print("End time (yyyy-MM-dd HH:mm): ");
        String endTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        try {
            Booking booking = new Booking(null, currentUser.getUsername(), id, startTime, endTime, false);
            bookingServiceImpl.bookResource(booking);
            System.out.println("Conference room booked successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Time slot is already booked.");
        }
    }

    private static void cancelBooking() {
        System.out.print("Booking ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        bookingServiceImpl.cancelBooking(id);
        System.out.println("Booking cancelled successfully.");
    }

    private static void viewAllBookings() {
        for (Booking booking : bookingServiceImpl.getAllBookings()) {
            System.out.println("ID: " + booking.getId() + ", User: " + booking.getUsername() + ", Resource ID: "
                    + booking.getResourceId() + ", Start Time: " + booking.getStartTime() + ", End Time: "
                    + booking.getEndTime() + ", Workspace: " + booking.isWorkspace());
        }
    }

    private static void addNewWorkspace() {
        System.out.print("Workspace name: ");
        String name = scanner.nextLine();
        Workspace workspace = new Workspace(null, name, false);
        resourceServiceImpl.addWorkspace(workspace);
        System.out.println("Workspace added successfully.");
    }

    private static void addNewConferenceRoom() {
        System.out.print("Conference room name: ");
        String name = scanner.nextLine();
        ConferenceRoom conferenceRoom = new ConferenceRoom(null, name, false);
        resourceServiceImpl.addConferenceRoom(conferenceRoom);
        System.out.println("Conference room added successfully.");
    }

    private static void updateWorkspace() {
        System.out.print("Workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New name: ");
        String newName = scanner.nextLine();
        System.out.print("Is available: ");
        boolean status = scanner.nextBoolean();
        resourceServiceImpl.updateWorkspace(id, newName, status);
        System.out.println("Workspace updated successfully.");
    }

    private static void updateConferenceRoom() {
        System.out.print("Conference Room ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New name: ");
        String newName = scanner.nextLine();
        System.out.print("Is available: ");
        boolean status = scanner.nextBoolean();
        resourceServiceImpl.updateConferenceRoom(id, newName, status);
        System.out.println("Conference room updated successfully.");
    }

    private static void deleteWorkspace() {
        System.out.print("Workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        resourceServiceImpl.deleteWorkspace(id);
        System.out.println("Workspace deleted successfully.");
    }

    private static void deleteConferenceRoom() {
        System.out.print("Conference Room ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        resourceServiceImpl.deleteConferenceRoom(id);
        System.out.println("Conference room deleted successfully.");
    }

    private static void filterBookings() {
        System.out.print("Date (yyyy-MM-dd) or leave blank: ");
        String dateStr = scanner.nextLine();
        LocalDateTime date = null;
        if (!dateStr.isBlank()) {
            date = LocalDateTime.parse(dateStr + "T00:00:00");
        }
        System.out.print("Username or leave blank: ");
        String username = scanner.nextLine();
        System.out.print("Resource ID or leave blank: ");
        String resourceIdStr = scanner.nextLine();
        Integer resourceId = resourceIdStr.isBlank() ? null : Integer.parseInt(resourceIdStr);

        for (Booking booking : bookingServiceImpl.filterBookings(date, username, resourceId)) {
            System.out.println("ID: " + booking.getId() + ", User: " + booking.getUsername() + ", Resource ID: "
                    + booking.getResourceId() + ", Start Time: " + booking.getStartTime() + ", End Time: "
                    + booking.getEndTime() + ", Workspace: " + booking.isWorkspace());
        }
    }
}
