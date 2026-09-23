# CarRentalSystem
A simulated car rental system that uses object-oriented principles in Java.

# Requirements
1. The system should allow reservation of a car of a given type at a desired date and time for a given number of days.
2. There are 3 types of cars (sedan, SUV, van).
3. The number of cars of each type is limited.
4. Use unit tests to prove the system satisfies the requirements.

# Classes and Enums
1. CarType is an enum that represents the supported car types (sedan, suv, van).
2. Car class represents a rental car, has id, car type, and car rate.
3. Sedan, SUV, and Van are all subclasses. They are all types of cars, and have different car rates.
4. Reservation class represents a reservation made by the user. It has properties for car, start time, end time,
and total price (calculated by car rate * number of days reserved).
5. Car Rental System demonstrates how the system works, has methods for adding cars, checking availability,
and car count.
