package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Geeft alle belangrijke data uit de simulator weer in textvorm
 */
public class TextView extends AbstractView {

    private JLabel currentTime;
    private JLabel dayEarnings;
    private JLabel earnings;
    private JLabel currentCars;
    private JLabel currentCarPercentages;
    private JLabel currentQueues;
    private DecimalFormat f;

    /**
     * Maakt nieuwe instanties van alle tekstvelden die gebruikt worden
     *
     * @param simulatorLogic    De simulatorlogic die alle schermen beheert
     */
    public TextView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);

        this.setBackground(new Color(51, 51, 51));

        setLayout(new FlowLayout(FlowLayout.LEFT, 60, 5));

        f = new DecimalFormat("#.##");

        currentTime = new JLabel();
        currentTime.setForeground(Color.lightGray);
        dayEarnings = new JLabel();
        dayEarnings.setForeground(Color.lightGray);
        setDayEarnings();
        earnings = new JLabel();
        earnings.setForeground(Color.lightGray);
        currentCars = new JLabel();
        currentCars.setForeground(Color.lightGray);
        currentCarPercentages = new JLabel();
        currentCarPercentages.setForeground(Color.lightGray);
        currentQueues = new JLabel();
        currentQueues.setForeground(Color.lightGray);

        add(Box.createRigidArea(new Dimension(0, 450)));
        add(currentTime);
        add(dayEarnings);
        add(earnings);
        add(currentCars);
        add(currentCarPercentages);
        add(currentQueues);
    }

    /**
     * Update alle textvelden op het scherm elke tick
     */
    @Override
    public void updateView() {
        currentTime.setText("<html>Current day: " + convertDay(simulatorLogic.getDay()) +
                "<br/>Current hour: " + simulatorLogic.getHour() +
                "<br/>Current minute: " + simulatorLogic.getMinute());
        setDayEarnings();
        earnings.setText("<html>Money due from parked cars: €" + f.format(simulatorLogic.getMoneyDue()) + "-<br/>" +
                "<br/>Total Earnings: €" + f.format(simulatorLogic.getTotalEarnings()) + "-");
        currentCars.setText("<html><b>Current cars in the garage:</b><br/>" +
                "<br/>Normal cars: " + simulatorLogic.getNormalCars() +
                "<br/>Cars with a parking pass: " + simulatorLogic.getPassCars() +
                "<br/>Cars that reserved a spot: " + simulatorLogic.getReservationCars());
        currentCarPercentages.setText("<html>Current distribution of cars (%):<br/>" +
                "<br/>Normal cars: " + simulatorLogic.getCarPercentages()[0] + "%" +
                "<br/>Parking pass cars: " + simulatorLogic.getCarPercentages()[1] + "%" +
                "<br/>Reserved spot cars: " + simulatorLogic.getCarPercentages()[2] + "%");
        currentQueues.setText("<html>Current cars in queue: <br/>" +
                "<br/>Entrance queue: " + simulatorLogic.getEntranceCarQueue().carsInQueue() +
                "<br/>Parking pass queue: " + simulatorLogic.getEntranceCarQueue().carsInQueue() +
                "<br/>Exit queue: " + simulatorLogic.getExitCarQueue().carsInQueue() +
                "<br/><br/>Cars that left the queue: " + simulatorLogic.getCarsThatLeft());
    }

    /**
     * Convert de huidige dag van een integer value naar een dagnaam in String formaat
     *
     * @param dayInt    De integer representatie van de huidige dag
     * @return  De String representatie van de huidige dag
     */
    private String convertDay (int dayInt){
        String dayString = "";
        switch (dayInt){
            case 0:  dayString = "Monday";
                break;
            case 1:  dayString = "Tuesday";
                break;
            case 2:  dayString = "Wednesday";
                break;
            case 3:  dayString = "Thursday";
                break;
            case 4:  dayString = "Friday";
                break;
            case 5:  dayString = "Saturday";
                break;
            case 6:  dayString = "Sunday";
                break;
        }
        return dayString;
    }

    /**
     * Update alle text voor het dayearnings textveld
     */
    private void setDayEarnings(){
        dayEarnings.setText ("<html>Earnings per day:<br/>" +
                "<br/>Monday: €" + f.format(simulatorLogic.getDayEarnings()[0]) + "-" +
                "<br/>Tuesday: €" + f.format(simulatorLogic.getDayEarnings()[1]) + "-" +
                "<br/>Wednesday: €" + f.format(simulatorLogic.getDayEarnings()[2]) + "-" +
                "<br/>Thursday: €" + f.format(simulatorLogic.getDayEarnings()[3]) + "-" +
                "<br/>Friday: €" + f.format(simulatorLogic.getDayEarnings()[4]) + "-" +
                "<br/>Saturday: €" + f.format(simulatorLogic.getDayEarnings()[5]) + "-" +
                "<br/>Sunday: €" + f.format(simulatorLogic.getDayEarnings()[6]) + "-</html>");
    }
}
