package Parkeersimulator.Model;

import java.awt.*;

public abstract class Reservation {
    private Location location;

    public Reservation() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public abstract Color getColor();

    public abstract void tick();
}
