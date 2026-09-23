package carrental;

public class Car {
    private final String id;
    private final CarType type;

    public Car(String id, CarType type) {
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
}
