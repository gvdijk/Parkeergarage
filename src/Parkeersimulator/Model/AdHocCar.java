package Parkeersimulator.Model;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
	private static final Color COLOR = new Color(255, 42, 42);

    /**
     * Constructor voor objecten van klasse AdHocCar.
     * Berekent een willekeurige lengte voor de tijd van deze AdHocCar zijn bezoek.
     */
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    	this.setStayMinutes(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    /**
     * @return de Color van deze AdHocCar.
     */
    public Color getColor(){
    	return COLOR;
    }
}
