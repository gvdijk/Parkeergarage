package Parkeersimulator.Model;

import java.util.LinkedList;
import java.util.Queue;

public class ReservationQueue {
    private Queue<CarReservation> queue = new LinkedList<>();

    public boolean addReservation(CarReservation reservation) {
        return queue.add(reservation);
    }

    public Reservation removeReservation() {
        return queue.poll();
    }

    public int reservationsInQueue(){
        return queue.size();
    }
}
