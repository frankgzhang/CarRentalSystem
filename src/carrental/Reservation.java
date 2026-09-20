package carrental;

import java.time.LocalTime;

public class Reservation {
    private final String id;
    private final Car car;

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalTime cleanUpTime;

    public Reservation(String id, Car car, LocalTime startTime, LocalTime endTime, LocalTime cleanUpTime) {
        this.id = id;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cleanUpTime = cleanUpTime;
    }

    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getCleanUpTime() {
        return cleanUpTime;
    }
}
