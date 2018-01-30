package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;

public class ReservationCar extends Car {
    private static final Color COLOR=Color.yellow;
    private Location reservedLocation;

    public ReservationCar(Location reservedLocation) {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.reservedLocation = reservedLocation;
    }

    public Color getColor(){
        return COLOR;
    }

    public Location getReservedLocation() {
        return reservedLocation;
    }
}
