package carrental;

public class Car {
    private final String id;
    private final CarType type;

    public Car(String id, CarType type) {
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
