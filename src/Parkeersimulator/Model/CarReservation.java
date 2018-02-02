package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CarReservation extends Reservation {
    private static final Color COLOR = new Color(150, 230, 150);
    private int minutesToGo;
    private SimulatorLogic logic;

    public CarReservation(SimulatorLogic logic) {
        minutesToGo = (int) (60 + ThreadLocalRandom.current().nextFloat() * 4 * 60);
        this.logic = logic;
        ReservationCar car = new ReservationCar(this);
        logic.addReservationCarToList(car);
    }

    public int getMinutesToGo() {
        return minutesToGo;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void tick() {
        minutesToGo--;
        if (minutesToGo == -30) {
            logic.getScreenLogic().removeReservationAt(getLocation());
            this.setLocation(null);
        }
    }
}
