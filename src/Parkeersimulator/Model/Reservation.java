package Parkeersimulator.Model;

import java.awt.*;

public abstract class Reservation {
    private Location location;

    /**
     * Constructor voor objecten van klasse Reservation.
     */
    public Reservation() {}

    /**
     * @return de huide Location van deze Reservation.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location de Location die aan deze Reservation moet worden toegewezen.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return de Color van deze Reservation.
     */
    public abstract Color getColor();

    /**
     * Vorder de tijd voor deze Reservation.
     */
    public abstract void tick();
}
