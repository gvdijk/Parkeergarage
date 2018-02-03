package Parkeersimulator.Model;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CarReservation extends Reservation {
    private static final Color COLOR = new Color(28, 108, 43);
    private int minutesToGo;
    private SimulatorLogic logic;

    /**
     * Constructor voor objecten van klasse CarReservation.
     * Bepaald een willekeurige tijd voor de afgesproken tijd.
     * Maakt een ReservationCar aan die correspondeert met deze CarReservation.
     * Voegt deze ReservationCar toe aan de lijst in SimulatorLogic.
     */
    public CarReservation(SimulatorLogic logic) {
        minutesToGo = (int) (60 + ThreadLocalRandom.current().nextFloat() * 4 * 60);
        this.logic = logic;
        ReservationCar car = new ReservationCar(this);
        logic.addReservationCarToList(car);
    }

    /**
     * @return de resterende tijd in minuten tot de afgesproken tijd.
     */
    public int getMinutesToGo() { return minutesToGo; }

    /**
     * @return de Color van deze CarReservation.
     */
    public Color getColor() { return COLOR; }

    /**
     * Vorder de tijd van deze CarReservation.
     * Verwijder deze CarReservation als er geen Car is komen opdagen na 30 minuten.
     */
    public void tick() {
        minutesToGo--;
        if (minutesToGo == -30) {
            logic.getScreenLogic().removeReservationAt(getLocation());
            this.setLocation(null);
        }
    }
}
