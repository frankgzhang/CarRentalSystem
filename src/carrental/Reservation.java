package carrental;

import java.time.LocalDateTime;

public class Reservation {
    private final String id;
    private final Car car;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDateTime cleanUpEndTime;

    public Reservation(String id, Car car, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime cleanUpEndTime) {
        this.id = id;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cleanUpEndTime = cleanUpEndTime;
    }

    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getCleanUpEndTime() {
        return cleanUpEndTime;
    }

    /* Checks if car is being used during requested reservation time.
    */
    public boolean carReserved(LocalDateTime requestedStartTime, LocalDateTime requestedCleanUpEndTime) {
        return startTime.isBefore(requestedCleanUpEndTime)
                && requestedStartTime.isBefore(cleanUpEndTime);
    }
}
