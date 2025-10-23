# SOEN-342-Project

## Team Members Information

| Name | Student ID | 
| --- | --- |
| Youssef Yassa (Team Leader) | 40265083 |
| Tristan Girardi | 40272203 |
| Youssef Youssef | 40285384 | 


When you run the program, you’ll see this menu in terminal:

1. View all Connections
2. Search specific connections
3. Sort connections by price
4. Sort connections by duration
5. Reset search
6. Exit

To choose an option, type the number and press Enter.

View all Connections

Shows a list of all the available train travels— including departure city, arrival city, times, and ticket prices. Use this if you just want to browse what’s available.

Search specific connections

Lets you find trips that match what you’re looking for based on your filter.
When you choose this option:
1. You’ll be asked to enter what you want to search by (e.g., departureCity, arrivalCity, departureTime, arrivalTime, or daysOfOperation).
2. Then, you’ll be asked to enter a value. Examples:
   - For city searches: type a city name (e.g., Dublin)
   - For time: use hh:mm format (e.g., 09:30)
   - For days: type a day like MON or FRI

If there are matching results, they’ll be shown on screen. If not, you’ll see a message saying no connections were found.

Sort connections by price

Sorts your current list of results from cheapest to most expensive. Use this if you want to find the most affordable trip. First, you will be prompted to choose First Class Tickets or Second Class Tickets. Then, you will be prompted to choose ascending “asc” or descensing “desc”.

Sort connections by duration

Sorts the current list of trips from shortest to longest travel time if “asc” or longest to shortest if “desc”. 

Reset search

Clears any filters or searches you’ve done and shows all connections again. Use this if you want to reset filters or sort.

Exit

Closes the program and displays a goodbye message: “Thank you for using the Train Ticketing System. Goodbye!”

Example Case

Welcome to the Train Ticketing System!
Please select an option:
1. View all Connections
2. Search specific connections
3. Sort connections by price
4. Sort connections by duration
5. Reset search
6. Exit

> 2
Enter parameter to search by (departureCity/arrivalCity/departureTime/arrivalTime/daysOfOperation):
> departureCity
Enter value to search (time-> hh:mm, days-> MON,TUE,WEN,THU,FRI,SAT,SUN):
> Dublin
[lists all connections departing from Dublin]
> 4

Enter order

>desc

[sorts current list by duration descending]
> 5
Search reset. Displaying all connections.
> 6
Thank you for using the Train Ticketing System. Goodbye!
