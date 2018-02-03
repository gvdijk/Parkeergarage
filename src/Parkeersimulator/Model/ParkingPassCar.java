package Parkeersimulator.Model;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
    private static final Color COLOR = new Color(43, 186, 252);

    /**
     * Constructor voor objecten van klasse ParkingPassCar.
     * Berekent een willekeurige lengte voor de tijd van deze ParkingPassCar zijn bezoek.
     */
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (60 + random.nextFloat() * 7 * 60);
        this.setStayMinutes(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    /**
     * @return de Color van deze ParkingPassCar.
     */
    public Color getColor(){
    	return COLOR;
    }
}