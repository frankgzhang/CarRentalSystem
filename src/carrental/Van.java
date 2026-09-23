package carrental;

public class Van extends Car{
    private double carRate;

    public Van(String id, double rate) {
        super(id, CarType.VAN);

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
