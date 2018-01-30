package Parkeersimulator.Model;

import Parkeersimulator.Model.Car;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
    private static final Color COLOR=Color.blue;
	
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (60 + random.nextFloat() * 7 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
