/* Test file that will contain unit testing */
package carrental;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class CarRentalSystemTest {
    private CarRentalSystem carRentalSystem;
    private LocalDateTime startTime;

    @BeforeEach
    void initialSetup() {
        // Create rental system with 2 sedans, 1 suv, and 1 van
        carRentalSystem = new CarRentalSystem(2, 1, 1);
        startTime = LocalDateTime.of(2026, 9, 22, 16, 21);
    }

    // 1. Initially, both of the sedans are available for reservation
    @Test
    void shouldReturn2Sedans() {
        int sedansAvailable = carRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(2, sedansAvailable);
    }

    // 2. Should successfully reserve a sedan
    @Test
    void shouldReserveSedan() {
        Reservation reservation = carRentalSystem.makeReservation(CarType.SEDAN, startTime, 7);

        assertNotNull(reservation);
        assertEquals(CarType.SEDAN, reservation.getCar().getType());
        assertEquals(startTime, reservation.getStartTime());
        assertEquals(startTime.plusDays(7), reservation.getEndTime());
    }

    // 3. After one sedan was reserved, should only have one sedan still available
    @Test
    void shouldReturnOneSedan() {
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        int sedansAvailable = carRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(1, sedansAvailable);
    }

    // 4. Two reservations on the same day will use two different sedans
    @Test
    void shouldReserveDifferentSedans() {
        Reservation firstReservation = carRentalSystem.makeReservation(CarType.SEDAN, startTime, 5);
        Reservation secondReservation = carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);

        assertNotEquals(firstReservation.getCar().getId(), secondReservation.getCar().getId());
    }

    // 5. Trying to reserve a 3rd sedan should throw force the reservation to fail
    @Test
    void shouldFailAfter2Sedans() {
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);

        assertThrows(IllegalStateException.class,
                () -> carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3));
    }

    // 6. Reserving a sedan does not affect availability of other types of cars
    @Test
    void independentCarTypes() {
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        int suvsAvailable = carRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(1, suvsAvailable);
    }

    // 7. Overlapping reservation should fail
    @Test
    void shouldFailOverlap() {
        carRentalSystem.makeReservation(CarType.VAN, startTime, 3);
        LocalDateTime overlapStart = startTime.plusDays(2);

        assertThrows(IllegalStateException.class,
                () -> carRentalSystem.makeReservation(CarType.VAN, overlapStart, 2));
    }

    // 8. Allow a new reservation right after the previous reservation ends
    @Test
    void shouldAllowNewReservationAfterOldOneEnds() {
        Reservation firstCar = carRentalSystem.makeReservation(CarType.SUV, startTime, 3);

        LocalDateTime nextStart = startTime.plusDays(3);
        Reservation secondCar = carRentalSystem.makeReservation(CarType.SUV, nextStart, 4);

        assertNotNull(secondCar);

        // Only one SUV, so same car means same id
        assertEquals(firstCar.getCar().getId(), secondCar.getCar().getId());
    }

    // 9. A reservation before an existing reservation is allowed
    @Test
    void shouldAllowBeforeReesrvation() {
        LocalDateTime existingStart = startTime.plusDays(5);
        carRentalSystem.makeReservation(CarType.VAN, existingStart, 2);

        Reservation firstCar = carRentalSystem.makeReservation(CarType.VAN, startTime, 3);

        assertNotNull(firstCar);
    }

    // 10. A reservation that contains time inside another reservation should fail
    @Test
    void shouldFailReservationInsideAnother() {
        carRentalSystem.makeReservation(CarType.VAN, startTime, 4);

        LocalDateTime secondStart = startTime.plusDays(1);

        assertThrows(IllegalStateException.class,
                () -> carRentalSystem.makeReservation(CarType.VAN, secondStart, 1));
    }

    // 11. Rental system creates a Sedan object and not just a Car object
    @Test
    void sedanReservationUsesSedanSubclass() {
        Reservation reservation = carRentalSystem.makeReservation(CarType.SEDAN, startTime, 9);
        assertInstanceOf(Sedan.class, reservation.getCar());
    }

    // 12. SUV rental rate should be $30/day
    @Test
    void suvRentalRateShouldBe30() {
        Reservation reservation = carRentalSystem.makeReservation(CarType.SUV, startTime, 8);
        assertEquals(30.0, reservation.getCar().getCarRate());
    }

    // 13. Van rental rate over multiple days should be $50 * numberOfDays
    @Test
    void vanRentalTotalPrice() {
        Reservation reservation = carRentalSystem.makeReservation(CarType.VAN, startTime, 10);
        assertEquals(500.0, reservation.getTotalPrice());
    }

    // 14. Should allow another rental when a car's reservation is over
    @Test
    void shouldAllowAnotherRental() {
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 2);
        carRentalSystem.makeReservation(CarType.SEDAN, startTime, 4);

        LocalDateTime thirdStart = startTime.plusDays(3);
        Reservation thirdReservation = carRentalSystem.makeReservation(CarType.SEDAN, thirdStart, 7);

        assertNotNull(thirdReservation);
    }

    // 15. Should reject requested reservation that contains an existing time
    @Test
    void shouldRejectRentalThatContainsExistingTime() {
        LocalDateTime firstStart = startTime.plusDays(3);
        carRentalSystem.makeReservation(CarType.SUV, firstStart, 2);

        assertThrows(IllegalStateException.class,
                () -> carRentalSystem.makeReservation(CarType.SUV, startTime, 7));


    }
}
