package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;

public class CarReservation extends Reservation {
    private static final Color COLOR = new Color(250, 230, 150);
    private int minutesToGo;
    private SimulatorLogic logic;

    public CarReservation(Location location, SimulatorLogic logic) {
        super(location, "Car");
        Random random = new Random();
        minutesToGo = (int) (30 + random.nextFloat() * 4.5 * 60);
        this.logic = logic;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void tick() {
        minutesToGo--;
        if (minutesToGo == 0) {
            Car car = new ReservationCar(getLocation());
            logic.addReservationCar(car);
        }
    }
}
