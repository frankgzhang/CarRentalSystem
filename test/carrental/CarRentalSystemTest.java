/* Test file that will contain unit testing */
package carrental;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class CarRentalSystemTest {
    private CarRentalSystem cRentalSystem;
    private LocalDateTime startTime;

    @BeforeEach
    void initialSetup() {
        // Create rental system with 2 sedans, 1 suv, and 1 van
        cRentalSystem = new CarRentalSystem(2, 1, 1);
        startTime = LocalDateTime.of(2026, 9, 22, 16, 21);
    }

    // Initially, both of the sedans are available for reservation
    @Test
    void shouldReturn2Sedans() {
        int sedansAvailable = cRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(2, sedansAvailable);
    }

    // Should successfully reserve a sedan
    @Test
    void shouldReserveSedan() {
        Reservation reservation = cRentalSystem.makeReservation(CarType.SEDAN, startTime, 7);

        assertNotNull(reservation);
        assertEquals(CarType.SEDAN, reservation.getCar().getType());
        assertEquals(startTime, reservation.getStartTime());
        assertEquals(startTime.plusDays(7), reservation.getEndTime());
    }

    // After one sedan was reserved, should only have one sedan still available
    @Test
    void shouldReturnOneSedan() {
        cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        int sedansAvailable = cRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(1, sedansAvailable);
    }

    // Two reservations on the same day will use two different sedans
    @Test
    void shouldReserveDifferentSedans() {
        Reservation firstReservation = cRentalSystem.makeReservation(CarType.SEDAN, startTime, 5);
        Reservation secondReservation = cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);

        assertNotEquals(firstReservation.getCar().getId(), secondReservation.getCar().getId());
    }

    // Trying to reserve a 3rd sedan should throw force the reservation to fail
    @Test
    void shouldFailAfter2Sedans() {
        cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);

        assertThrows(IllegalStateException.class,
                () -> cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3));
    }

    // Reserving a sedan does not affect availability of other types of cars
    @Test
    void independentCarTypes() {
        cRentalSystem.makeReservation(CarType.SEDAN, startTime, 3);
        int suvsAvailable = cRentalSystem.getCarCount(CarType.SEDAN, startTime, 3);

        assertEquals(1, suvsAvailable);
    }

    // Overlapping reservation should fail
    @Test
    void shouldFailOverlap() {
        cRentalSystem.makeReservation(CarType.VAN, startTime, 3);
        LocalDateTime overlapStart = startTime.plusDays(2);

        assertThrows(IllegalStateException.class,
                () -> cRentalSystem.makeReservation(CarType.VAN, overlapStart, 2));
    }

    // Allow a new reservation right after the previous reservation ends
    @Test
    void shouldAllowNewReservationAfterOldOneEnds() {
        Reservation firstCar = cRentalSystem.makeReservation(CarType.SUV, startTime, 3);

        LocalDateTime nextStart = startTime.plusDays(3);
        Reservation secondCar = cRentalSystem.makeReservation(CarType.SUV, nextStart, 4);

        assertNotNull(secondCar);

        // Only one SUV, so same car means same id
        assertEquals(firstCar.getCar().getId(), secondCar.getCar().getId());
    }

    // A reservation before an existing reservation is allowed
    @Test
    void shouldAllowBeforeReesrvation() {
        LocalDateTime existingStart = startTime.plusDays(5);
        cRentalSystem.makeReservation(CarType.VAN, existingStart, 2);

        Reservation firstCar = cRentalSystem.makeReservation(CarType.VAN, startTime, 3);

        assertNotNull(firstCar);
    }

    // A reservation that contains time inside another reservation should fail
    @Test
    void shouldFailReservationInsideAnother() {
        cRentalSystem.makeReservation(CarType.VAN, startTime, 4);

        LocalDateTime secondStart = startTime.plusDays(1);

        assertThrows(IllegalStateException.class,
                () -> cRentalSystem.makeReservation(CarType.VAN, secondStart, 1));
    }
}
