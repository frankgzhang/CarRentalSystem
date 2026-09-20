package carrental;

public class Car {
    private final String id;
    private final CarType type;
    private boolean available;

    public Car(String id, CarType type) {
        this.id = id;
        this.type = type;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public CarType getType() {
        return type;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
