package Parkeersimulator.Model;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int stayMinutes;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private boolean badAtParking;

    /**
     * Constructor for objects of class Car
     */
    public Car() {
        badAtParking = false;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public void setStayMinutes (int stayMinutes) { this.stayMinutes = stayMinutes; }

    public int getStayMinutes () { return stayMinutes; }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void setBadAtParking(boolean badAtParking) { this.badAtParking = badAtParking; }

    public boolean getBadAtParking() { return badAtParking; }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
}