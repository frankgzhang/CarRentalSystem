package carrental;

import java.time.LocalDateTime;

public class Reservation {
    private final Car car;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final double totalPrice;

    public Reservation(Car car, LocalDateTime startTime, LocalDateTime endTime, double totalPrice) {
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
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

    public double getTotalPrice() {
        return totalPrice;
    }
}
