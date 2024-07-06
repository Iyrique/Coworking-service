package com.ylab.repository;

import com.ylab.connector.ConnectorDB;
import com.ylab.model.Booking;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BookingRepository.
 */
@Testcontainers
public class BookingRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static BookingRepository bookingRepository;

    @BeforeAll
    public static void setUp() throws SQLException {

        ConnectorDB.setConnectionDetails(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        )) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE SCHEMA coworking");
                stmt.execute("CREATE TABLE coworking.bookings (" +
                        "id SERIAL PRIMARY KEY, " +
                        "username VARCHAR(50) NOT NULL, " +
                        "resource_id INTEGER NOT NULL, " +
                        "start_time TIMESTAMP NOT NULL, " +
                        "end_time TIMESTAMP NOT NULL, " +
                        "is_workspace BOOLEAN NOT NULL)");
            }
        }

        bookingRepository = new BookingRepository();
    }

    @AfterEach
    public void clearDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        )) {
            connection.createStatement().execute("TRUNCATE TABLE coworking.bookings RESTART IDENTITY CASCADE");
        }
    }
//
//    @Test
//    @DisplayName("Test saving and retrieving all bookings")
//    public void testSaveAndFindAllBookings() {
//        Booking booking = new Booking(null, "user1", 1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true);
//        bookingRepository.save(booking);
//
//        List<Booking> bookings = bookingRepository.findAll();
//        assertNotNull(bookings);
//        assertFalse(bookings.isEmpty());
//        assertEquals("user1", bookings.get(0).getUsername());
//    }

    @Test
    @DisplayName("Test cancelling a booking")
    public void testCancelBooking() {
        Booking booking = new Booking(null, "user2", 2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), false);
        bookingRepository.save(booking);

        List<Booking> bookings = bookingRepository.findAll();
        Long id = bookings.get(0).getId();
        bookingRepository.cancel(id.intValue());

        bookings = bookingRepository.findAll();
        assertTrue(bookings.isEmpty(), "Bookings should be empty after cancellation");
    }

    @Test
    @DisplayName("Test filtering bookings by date, username, and resource ID")
    public void testFilterBookings() {
        LocalDateTime now = LocalDateTime.now();
        Booking booking1 = new Booking(null, "user3", 3, now, now.plusHours(1), true);
        Booking booking2 = new Booking(null, "user4", 4, now.plusDays(1), now.plusDays(1).plusHours(1), false);
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.filter(now.toLocalDate().atStartOfDay(), "user3", 3);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        assertEquals("user3", bookings.get(0).getUsername());
    }
}
