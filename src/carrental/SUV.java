package carrental;

public class SUV extends Car{
    private static final double CAR_RATE = 30.0;

    public SUV(String id) {
        super(id, CarType.SUV, CAR_RATE);
    }
}
