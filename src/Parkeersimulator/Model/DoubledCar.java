package Parkeersimulator.Model;

import java.awt.*;

public class DoubledCar extends Car {
    private Color color;
    private Car coupledCar;

    /**
     * Constructor voor objecten van klasse AdHocCar.
     * Neemt alle relevante waardes van gekoppelde Car over.
     * @param location de Location waar deze DoubledCar zich moet gaan bevinden.
     * @param coupledCar de Car waar deze DoubledCar een aanduidende plekvuller is.
     */
    public DoubledCar(Location location, Car coupledCar) {
        this.setStayMinutes(coupledCar.getStayMinutes());
        this.setMinutesLeft(coupledCar.getStayMinutes());
        this.setHasToPay(false);
        this.color = coupledCar.getColor();
        this.setLocation(location);
        this.coupledCar = coupledCar;
    }

    /**
     * @return de Color van deze DoubledCar.
     */
    public Color getColor(){ return color; }

    /**
     * @return de Car waar deze DoubledCar een aanduidende plekvuller is.
     */
    public Car getCoupledCar() { return coupledCar; }
}
