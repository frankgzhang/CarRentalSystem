package carrental;

import java.time.LocalDateTime;

public class Reservation {
    private final Car car;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Reservation(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
