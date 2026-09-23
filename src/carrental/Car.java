package carrental;

public class Car {
    private final String id;
    private final CarType type;
    private final double carRate;

    public Car(String id, CarType type, double carRate) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Car ID cannot be null or empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Car type cannot be null");
        }

        if (carRate < 0) {
            throw new IllegalArgumentException("Car rate must be > $0");
        }

        this.id = id;
        this.type = type;
        this.carRate = carRate;
    }

    public String getId() {
        return id;
    }

    public CarType getType() {
        return type;
    }

    public double getCarRate() {
        return carRate;
    }
}
