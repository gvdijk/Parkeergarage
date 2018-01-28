package Parkeersimulator.Model;

import java.awt.*;

public abstract class Reservation {
    private Location location;

    public Reservation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract Color getColor();
}
