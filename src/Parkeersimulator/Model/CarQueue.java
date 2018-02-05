package Parkeersimulator.Model;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     * Voeg een nieuwe Car toe aan deze Queue
     * @param car de toe te voegen Car
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Check wat de eerst volgende Car in de Queue is
     * @return de Car voorin de Queue
     */
    public Car checkCar() {
        return queue.peek();
    }

    /**
     * Remove de eerst volgende Car in de Queue en return deze
     * @return de Car voorin de Queue
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Geef de grootte van deze Queue teurg
     * @return de grootte van deze Queue
     */
    public int carsInQueue(){
    	return queue.size();
    }

    /**
     * Maak deze Queue leeg
     */
    public void clearQueue() { queue.clear(); }
}
