package carrental;

public class SUV extends Car{
    private double carRate;

    public SUV(String id, double rate) {
        super(id, CarType.SUV);

        if (rate <= 0) {
            throw new IllegalArgumentException("Car rate must be > $0");
        }
        this.carRate = rate;
    }

    @Override
    public double getCarRate() {
        return carRate;
    }
}
