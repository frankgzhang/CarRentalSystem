package carrental;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CarRentalSystem {
    private final Map<CarType, List<Car>> carInventory;
    private final Map<String, List<Reservation>> reservations;

    public CarRentalSystem(int sedans, int suvs, int vans) {
        carInventory = new HashMap<>();
        reservations = new HashMap<>();

        for (CarType type : CarType.values()) {
            carInventory.put(type, new ArrayList<>());
        }

        addCars(CarType.SEDAN, sedans);
        addCars(CarType.SUV, suvs);
        addCars(CarType.VAN, vans);
    }

    private void addCars(CarType type, int numberOfCars) {
        for (int i = 1; i <= numberOfCars; i ++) {
            String carId = type.name() + " " + i;
            Car car;

            switch (type) {
                case SEDAN:
                    car = new Sedan(carId);
                    break;
                case SUV:
                    car = new SUV(carId);
                    break;
                case VAN:
                    car = new Van(carId);
                    break;
                default:
                    throw new IllegalArgumentException("Car type not supported");
            }

            carInventory.get(type).add(car);

            reservations.put(carId, new ArrayList<>());
        }
    }

    public Reservation makeReservation(CarType type, LocalDateTime startTime, int numberOfDays) {
        if (numberOfDays <= 0) {
            throw new IllegalArgumentException("Reserved number of days must be greater than 0");
        }

        // Assumption: cannot reserve a car for more than a month
        if (numberOfDays > 30) {
            throw new IllegalArgumentException("Reserved number of days cannot exceed one month");
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }

        LocalDateTime endTime = startTime.plusDays(numberOfDays);

        for (Car car : carInventory.get(type)) {
            if (checkCarAvailability(car, startTime, endTime)) {
                double totalPrice = numberOfDays * car.getCarRate();

                Reservation reservation = new Reservation(car, startTime, endTime, totalPrice);
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

        for (Car car : carInventory.get(type)) {
            if (checkCarAvailability(car, startTime, endTime)) {
                carsLeft++;
            }
        }

        return carsLeft;
    }
}
