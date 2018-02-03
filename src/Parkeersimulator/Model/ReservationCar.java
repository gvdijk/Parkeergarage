package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ReservationCar extends Car {
    private Color color;
    private int minutesToGo;
    private CarReservation reservation;

    /**
     * Constructor voor objecten van klasse PassReservation.
     * Berekent een willekeurige lengte voor de tijd van deze ReservationCar zijn bezoek.
     * Zet de tijd tot van deze ReservationCar gelijk aan de dat van de CarReservation.
     * Bereken een willekeurige verandering in aankomsttijd.
     * Zet de Color voor te vroeg of te laat zijn, en op tijd komen.
     * @param reservation de Reservation voor deze ReservationCar.
     */
    public ReservationCar(CarReservation reservation) {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.reservation = reservation;
        int inconsistency = ThreadLocalRandom.current().nextInt(-60, 30);
        if (inconsistency < -30) {
            this.color = new Color(255, 212, 41);
        } else if (inconsistency > 15) {
            this.color = new Color(244, 159, 27);
        } else {
            this.color = new Color(70, 207, 96);
        }
        this.minutesToGo = reservation.getMinutesToGo() + inconsistency;
    }

    /**
     * @return de Color van deze ReservationCar.
     */
    public Color getColor(){ return color; }

    /**
     * @return de Loccation waar deze ReservationCar in de toekomst moet gaan parkeren.
     */
    public Location getReservedLocation() { return reservation.getLocation(); }

    /**
     * @return de CarReservation die bij deze ReservationCar hoort.
     */
    public CarReservation getReservation() { return reservation; }

    /**
     * @return de minuten totdat deze ReservationCar daadwerkelijk aankomt.
     */
    public int getMinutesToGo() { return minutesToGo; }

    /**
     * Overridden methode van Car.
     * Vorder de tijd voor deze ReservationCar.
     * Gebruik de Car methode wanneer minutesToGo gelijk is aan 0.
     */
    @Override
    public void tick() {
        if(minutesToGo <= 0) {
            super.tick();
        } else {
            minutesToGo--;
        }
    }
}