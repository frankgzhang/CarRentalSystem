package carrental;

public class Van extends Car{
    private static final double CAR_RATE = 50.0;

    public Van(String id) {
        super(id, CarType.VAN, CAR_RATE);
    }
}
