package Parkeersimulator.View;

import Parkeersimulator.Model.Car;
import Parkeersimulator.Model.CarQueue;
import Parkeersimulator.Model.Location;
import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;
    private JLabel entranceQueueSize;
    private JLabel entrancePassQueue;
    private JLabel paymentCarQueue;
    private JLabel exitCarQueue;


    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorLogic simulatorLogic) {
        super (simulatorLogic);

        entranceQueueSize = new JLabel("Entrance queue: " + simulatorLogic.getEntranceCarQueue().carsInQueue());
        add(entranceQueueSize);

        entrancePassQueue = new JLabel("Pass entrance queue: " + simulatorLogic.getEntrancePassQueue().carsInQueue());
        add(entrancePassQueue);

        paymentCarQueue = new JLabel("Payment queue: " + simulatorLogic.getPaymentCarQueue().carsInQueue());
        add(paymentCarQueue);

        exitCarQueue = new JLabel("Exit queue: " + simulatorLogic.getExitCarQueue().carsInQueue());
        add(exitCarQueue);

        size = new Dimension(0, 0);
    }

    @Override
    public String getName() {
        return "CarParkView";
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

     public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < simulatorLogic.getScreenLogic().getNumberOfFloors(); floor++) {
            for(int row = 0; row < simulatorLogic.getScreenLogic().getNumberOfRows(); row++) {
                for(int place = 0; place < simulatorLogic.getScreenLogic().getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = simulatorLogic.getScreenLogic().getCarAt(location);
                    Color color = car == null ? Color.darkGray : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        entranceQueueSize.setText("entrance queue: " + simulatorLogic.getEntranceCarQueue().carsInQueue());
        entrancePassQueue.setText("pass entrance queue: " + simulatorLogic.getEntrancePassQueue().carsInQueue());
        paymentCarQueue.setText("payment queue: " + simulatorLogic.getPaymentCarQueue().carsInQueue());
        exitCarQueue.setText("exit queue: " + simulatorLogic.getExitCarQueue().carsInQueue());

        repaint();

    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }



}
