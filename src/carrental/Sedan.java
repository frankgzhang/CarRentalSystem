package carrental;

public class Sedan extends Car{
    private static final double CAR_RATE = 20.0;

    public Sedan(String id) {
        super(id, CarType.SEDAN, CAR_RATE);
    }
}
