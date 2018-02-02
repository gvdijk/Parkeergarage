package Parkeersimulator.Model;

import java.awt.*;

public class PassReservation extends Reservation {
    private static final Color COLOR = new Color(120, 180, 250);

    public PassReservation(Location location) {
        this.setLocation(location);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void tick() {
        // Nothing here yet
    }
}
