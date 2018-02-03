package Parkeersimulator.View;

import Parkeersimulator.Model.*;

import java.awt.*;
import java.util.ArrayList;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
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
        graphics.clearRect(0,0, getPreferredSize().width, getPreferredSize().height);
        ArrayList<DoubledCar> doubledCars = new ArrayList<>();
        for(int floor = 0; floor < simulatorLogic.getGarageLogic().getNumberOfFloors(); floor++) {
            for(int row = 0; row < simulatorLogic.getGarageLogic().getNumberOfRows(); row++) {
                for(int place = 0; place < simulatorLogic.getGarageLogic().getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Reservation reservation = simulatorLogic.getGarageLogic().getReservationAt(location);
                    Car car = simulatorLogic.getGarageLogic().getCarAt(location);
                    Color color = new Color(112, 112, 112);
                    if (car != null) {
                        color = car.getColor();
                        if (car instanceof DoubledCar) {
                            doubledCars.add((DoubledCar) car);
                        }
                    } else if (reservation != null) {
                        color = reservation.getColor();
                    }
                    drawPlace(graphics, location, color);
                }
            }
        }
        drawDoubledPark(graphics,doubledCars);
        repaint();
    }

    /**
     * Paint a line over two places to indicate a double parked car.
     */
    private void drawDoubledPark(Graphics graphics, ArrayList<DoubledCar> doubledCars) {
        for (DoubledCar car : doubledCars) {
            Location locationSecondary = car.getLocation();
            Location locationPrimary = car.getCoupledCar().getLocation();
            Location location = locationPrimary.getPlace() > locationSecondary.getPlace() ? locationSecondary : locationPrimary;
            graphics.setColor(new Color(229, 229, 229));
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20 + 8,
                    60 + location.getPlace() * 10 + 3,
                    4,
                    14);
        }
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