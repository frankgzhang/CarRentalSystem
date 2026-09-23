package carrental;

public class Sedan extends Car{
    private double carRate;

    public Sedan(String id, double rate) {
        super(id, CarType.SEDAN);

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
