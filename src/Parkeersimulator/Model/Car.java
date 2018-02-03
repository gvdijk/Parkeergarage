package Parkeersimulator.Model;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int stayMinutes;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private boolean badAtParking = false;

    /**
     * Constructor voor objecten van klasse Car.
     */
    public Car() {}

    /**
     * @return de Location van deze Car.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location de Location waar deze Car zich bevind.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return de resterende tijd in minuten tot deze Car vertrekt.
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * @param minutesLeft de resterende tijd in minuten tot deze Car vertrekt.
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * @param stayMinutes de totale tijd in minuten die deze Car in de parkeergarage blijft.
     */
    public void setStayMinutes (int stayMinutes) { this.stayMinutes = stayMinutes; }

    /**
     * @return de totale tijd in minuten die deze Car in de parkeergarage blijft.
     */
    public int getStayMinutes () { return stayMinutes; }

    /**
     * @return of deze Car aan het betalen is.
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * @param isPaying of deze Car aan het betalen is.
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * @return of deze Car moet betalen.
     */
    public boolean getHasToPay() {return hasToPay;}

    /**
     * @param hasToPay of deze Car moet betalen.
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * @param badAtParking of deze Car slecht is geparkeerd.
     */
    public void setBadAtParking(boolean badAtParking) { this.badAtParking = badAtParking; }

    /**
     * @return of deze Car slecht is geparkeerd.
     */
    public boolean getBadAtParking() { return badAtParking; }

    /**
     * Vorder de tijd voor deze Car.
     */
    public void tick() { minutesLeft--; }

    /**
     * @return de Color van deze Car.
     */
    public abstract Color getColor();
}