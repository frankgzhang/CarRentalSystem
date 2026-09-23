package carrental;

public abstract class Car {
    private final String id;
    private final CarType type;

    public Car(String id, CarType type) {
        // Null and validation checks
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Car ID cannot be null or empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Car type cannot be null");
        }

        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public CarType getType() {
        return type;
    }

    // Each subclass provides its own implementation
    public abstract double getCarRate();
}
