package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;

public class DoubledCar extends Car {
    private Color color;
    private Car coupledCar;

    public DoubledCar(int stayMinutes, Location location, Color color, Car coupledCar) {
        this.setStayMinutes(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
        this.color = color;
        this.setLocation(location);
        this.coupledCar = coupledCar;
    }

    public Color getColor(){
        return color;
    }

    public Car getCoupledCar() {
        return coupledCar;
    }

    public void setCoupledCar(Car coupledCar) {
        this.coupledCar = coupledCar;
    }
}
