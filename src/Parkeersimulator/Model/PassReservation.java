package Parkeersimulator.Model;

import java.awt.*;

public class PassReservation extends Reservation {
    private static final Color COLOR = new Color(14, 97, 137);

    /**
     * Constructor voor objecten van klasse PassReservation.
     * @param location de Location voor deze PassReservation.
     */
    public PassReservation(Location location) {
        this.setLocation(location);
    }

    /**
     * @return de Color van deze PassReservation.
     */
    public Color getColor() {
        return COLOR;
    }
}
