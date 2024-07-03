# Coworking-Service

Console application for managing a coworking space

## Functional features

- User registration and authorization
- View available workplaces and conference rooms
- Booking a workplace or conference room for a specific time and date
- Cancellation of the reservation
- Manage bookings and resources
- View all bookings and filter them

## How to Build and Run

1. Clone the repository;
2. Navigate to the project directory: `cd <project-directory>`;
3. Start the database using Docker: `docker-compose up -d`;
4. Build the project using Maven: `mvn clean install`;
5. Run the application: `mvn exec: java -Dexec.mainClass = "com.ylab.Main`;

## Usage

### Login Menu

1. Login
2. Register
3. Exit

### Main Menu

1. View available workspaces
2. View available conference rooms
3. Book a workspace
4. Book a conference room
5. Cancel a booking
6. View all bookings
7. Add new workspace
8. Add new conference room
9. Update workspace
10. Update conference room
11. Delete workspace
12. Delete conference room
13. Filter bookings
14. Logout