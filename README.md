# SOEN-342-Project

## Team Members Information

| Name | Student ID | 
| --- | --- |
| Youssef Yassa (Team Leader) | 40265083 |
| Tristan Girardi | 40272203 |
| Youssef Youssef | 40285384 | 

## 🎥 Project Demo

[![Watch the Demo](https://img.shields.io/badge/YouTube-Demo-red?logo=youtube&logoColor=white&style=for-the-badge)](https://www.youtube.com/watch?v=98RXCTIDypw)

## How to run the program

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Setup

#### 1. Database Setup

Create the database:
```sql
CREATE DATABASE "connection-management";
```

#### 2. Configure Database

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/connection-management
spring.datasource.username=postgres
spring.datasource.password=PASSWORD
```

#### 3. Install Dependencies
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

## Usage Guide

When you run the program, you’ll see this menu in terminal:

1. View all Connections
2. Search specific connections
3. Sort connections by price
4. Sort connections by duration
5. Reset search
6. Book a Trip
7. View My Trips
8. Exit

To choose an option, type the number and press Enter.

### View all Connections

Shows a list of all the available train travels— including departure city, arrival city, times, and ticket prices. Use this if you just want to browse what’s available.

### Search specific connections

Lets you find trips that match what you’re looking for based on your filter.
When you choose this option:
1. You’ll be asked to enter what you want to search by (e.g., departureCity, arrivalCity, departureTime, arrivalTime, or daysOfOperation).
2. Then, you’ll be asked to enter a value. Examples:
   - For city searches: type a city name (e.g., Berlin)
   - For time: use hh:mm format (e.g., 09:30)
   - For days: type a day like MON or FRI

If there are matching results, they’ll be shown on screen. If not, you’ll see a message saying no connections were found.

### Sort connections by price

Sorts your current list of results from cheapest to most expensive. Use this if you want to find the most affordable trip. First, you will be prompted to choose First Class Tickets or Second Class Tickets. Then, you will be prompted to choose ascending “asc” or descensing “desc”.

### Sort connections by duration

Sorts the current list of trips from shortest to longest travel time if “asc” or longest to shortest if “desc”. 

### Reset search

Clears any filters or searches you’ve done and shows all connections again. Use this if you want to reset filters or sort.

### Book a Trip
1. Choose a connection by its index number
2. Enter number of travelers
3. For each traveler, provide:
   - First Name
   - Last Name
   - Age
   - ID (Passport/State ID)
   - Class preference (yes/no for First Class)

### View My Trips
   Enter your Last Name and ID
   View current/upcoming trips and past bookings

### Exit
   Closes the program and displays a goodbye message: “Thank you for using the Train    Ticketing System. Goodbye!”

## Example Case

### Example: Search for Berlin connections, book a trip, and view booking

**Step 1: Search for trains from Berlin**
```
Please select an option: 2
Enter parameter to search by:
Parameter: departureCity
Enter value: Berlin
```

**Step 2: Sort by price to find cheapest option**
```
Please select an option: 3
Would you like to sort by First Class or Second Class ticket price? (Enter '1' or '2'): 2
Would you like to sort in ascending or descending order? (Enter 'asc' or 'desc'): asc
```

Results show:
```
| Index | Departure | Arrival  | Timetable    | Train      | 1st Class | 2nd Class |
|-------|-----------|----------|--------------|------------|-----------|-----------|
| 0     | Berlin    | Dresden  | 06:45-08:03  | Eurostar   | €58       | €35       |
| 1     | Berlin    | Leipzig  | 18:15-20:20  | TGV        | €79       | €55       |
```

**Step 3: Book the Berlin to Dresden trip**
```
Please select an option: 6
Enter the index of the connection you want to book: 0
How many people are traveling? 2

--- Traveler 1 ---
First Name: Youssef
Last Name: Yassa
Age: 23
ID (Passport/State ID): DE123456
First Class? (yes/no): no

--- Traveler 2 ---
First Name: Youssef
Last Name: Youssef
Age: 22
ID (Passport/State ID): DE789012
First Class? (yes/no): yes
```

**Confirmation:**
```
✓ Trip booked successfully!

=== Trip ID: 1 ===
Booking Date: 2025-11-26
Connection: Berlin -> Dresden
Departure: 06:45 | Arrival: 08:03
Number of Travelers: 2
Reservations:
  - Youssef Yassa (Age: 23) | Ticket #1 | Second Class | €35.00
  - Youssef Youssef (Age: 22) | Ticket #2 | First Class | €58.00
Total Price: €93.00
```

**Step 4: View your booking**
```
Please select an option: 7
Enter your Last Name: Yassa
Enter your ID: DE123456

========== CURRENT/UPCOMING TRIPS ==========

=== Trip ID: 1 ===
Booking Date: 2025-11-26
Connection: Berlin -> Dresden
Departure: 06:45 | Arrival: 08:03
Number of Travelers: 2
Reservations:
  - Youssef Yassa (Age: 23) | Ticket #1 | Second Class | €35.00
  - Youssef Youssef (Age: 22) | Ticket #2 | First Class | €58.00
Total Price: €93.00
```

