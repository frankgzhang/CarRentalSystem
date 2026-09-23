package carrental;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        CarRentalSystem crs = new CarRentalSystem(2, 1, 1);

        LocalDateTime startTime = LocalDateTime.of(2026, 9, 20, 15, 12);

        // Reserve SEDAN 1 for 3 days
        Reservation firstReservation = crs.makeReservation(CarType.SEDAN, startTime, 3);
        System.out.println("First reservation: ");
        printReservation(firstReservation);
        System.out.println();

        // Make a reservation for the other SEDAN
        Reservation secondReservation = crs.makeReservation(CarType.SEDAN, startTime, 3);
        System.out.println("Second reservation: ");
        printReservation(secondReservation);
        System.out.println();

        // Check sedans left
        int sedansLeft = crs.getCarCount(CarType.SEDAN, startTime, 3);
        System.out.println("Sedans left: " + sedansLeft);
        System.out.println();

        // Make a reservation for a VAN
        Reservation thirdReservation = crs.makeReservation(CarType.VAN, startTime, 15);
        System.out.println("Third reservation (VAN): ");
        printReservation(thirdReservation);
        System.out.println();
    }

    private static void printReservation(Reservation r) {
        System.out.println("Car: " + r.getCar().getId());
        System.out.println("CarType: " + r.getCar().getType());
        System.out.println("Start Time: " + r.getStartTime());
        System.out.println("End Time: " + r.getEndTime());
        System.out.println("Total Price ($): " + r.getTotalPrice());
    }
}
