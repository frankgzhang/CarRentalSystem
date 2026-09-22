package carrental;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CarRentalSystem {
    private final Map<CarType, List<Car>> cars;
    private final Map<String, List<Reservation>> reservations;

    public CarRentalSystem(int sedans, int suvs, int vans) {
        cars = new HashMap<>();
        reservations = new HashMap<>();

        for (CarType type : CarType.values()) {
            cars.put(type, new ArrayList<>());
        }

        addCars(CarType.SEDAN, sedans);
        addCars(CarType.SUV, suvs);
        addCars(CarType.VAN, vans);
    }

    private void addCars(CarType type, int numberOfCars) {
        for (int i = 1; i <= numberOfCars; i ++) {
            String carId = type.name() + " " + i;
            Car car = new Car(carId, type);

            cars.get(type).add(car);

            reservations.put(carId, new ArrayList<>());
        }
    }

    public Reservation makeReservation(CarType type, LocalDateTime startTime, int numberOfDays) {
        if (numberOfDays <= 0) {
            throw new IllegalArgumentException("Reserved number of days must be greater than 0");
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }

        LocalDateTime endTime = startTime.plusDays(numberOfDays);

        for (Car car : cars.get(type)) {
            if (checkCarAvailability(car, startTime, endTime)) {
                Reservation reservation = new Reservation(car, startTime, endTime);
                reservations.get(car.getId()).add(reservation);
                return reservation;
            }
        }

        throw new IllegalStateException("No " + type + " is available at this time");
    }

    private boolean checkCarAvailability(Car car, LocalDateTime requestedStartTime, LocalDateTime requestedEndTime) {
        List<Reservation> reservationsList = reservations.get(car.getId());

        for (Reservation r : reservationsList) {
            if (alreadyReserved(r, requestedStartTime, requestedEndTime)) {
                return false;
            }
        }

        return true;
    }

    private boolean alreadyReserved(Reservation r, LocalDateTime requestedStartTime, LocalDateTime requestedEndTime) {
        return r.getStartTime().isBefore(requestedEndTime) && requestedStartTime.isBefore(r.getEndTime());
    }

    public int getCarCount(CarType type, LocalDateTime startTime, int numberOfDays) {
        LocalDateTime endTime = startTime.plusDays(numberOfDays);
        int carsLeft = 0;

        for (Car car : cars.get(type)) {
            if (checkCarAvailability(car, startTime, endTime)) {
                carsLeft++;
            }
        }

        return carsLeft;
    }
}
