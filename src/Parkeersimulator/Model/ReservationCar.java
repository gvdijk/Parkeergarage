package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ReservationCar extends Car {
    private Color color;
    private int minutesToGo;
    private CarReservation reservation;

    public ReservationCar(CarReservation reservation) {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.reservation = reservation;
        int inconsistency = ThreadLocalRandom.current().nextInt(-60, 30);
        if (inconsistency < -30) {
            this.color = new Color(250, 220, 10);
        } else if (inconsistency > 15) {
            this.color = new Color(180, 250, 10);
        } else {
            this.color = new Color(10, 250, 10);
        }
        this.minutesToGo = reservation.getMinutesToGo() + inconsistency;
    }

    public Color getColor(){
        return color;
    }

    public Location getReservedLocation() {
        return reservation.getLocation();
    }

    public CarReservation getReservation() { return reservation; }

    public int getMinutesToGo() { return minutesToGo; }

    @Override
    public void tick() {
        if(minutesToGo <= 0) {
            super.tick();
        } else {
            minutesToGo--;
        }
    }
}