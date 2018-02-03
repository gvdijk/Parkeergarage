package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;

public class TextView extends AbstractView {

    private JLabel currentTick;
    private JLabel currentTime;
    private JLabel totalEarnings;
    private JLabel dayEarnings;
    private JLabel dueEarnings;
    private JLabel currentCars;

    public TextView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        currentTick = new JLabel("Current tick: " + simulatorLogic.getCurrentTick());
        currentTime = new JLabel("<html>Current day: " + convertDay(simulatorLogic.getDay()) +
                "<br/>Current hour: " + simulatorLogic.getHour() +
                "<br/>Current minute: " + simulatorLogic.getMinute());
        dayEarnings = new JLabel("");
        setDayEarnings();
        dueEarnings = new JLabel("Money due from parked cars: €" + simulatorLogic.getMoneyDue() + ",-");
        totalEarnings = new JLabel("Total Earnings: €" + simulatorLogic.getTotalEarnings() +",-");
        currentCars = new JLabel("<html>Current cars in the garage:<br/>" +
                "<br/>Normal cars: " + simulatorLogic.getNormalCars() +
                "<br/>Cars with a parking pass: " + simulatorLogic.getPassCars() +
                "<br/>Cars that reserved a spot: " + simulatorLogic.getReservationCars());

        add(Box.createRigidArea(new Dimension(5, 50)));
        add(currentTick);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(currentTime);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(dayEarnings);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(totalEarnings);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(dueEarnings);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(currentCars);

        setVisible(false);
    }

    @Override
    public void updateView() {
        currentTick.setText("Current tick: " + simulatorLogic.getCurrentTick());
        currentTime.setText("<html>Current day: " + convertDay(simulatorLogic.getDay()) +
                "<br/>Current hour: " + simulatorLogic.getHour() +
                "<br/>Current minute: " + simulatorLogic.getMinute());
        setDayEarnings();
        dueEarnings.setText("Money due from parked cars: €" + simulatorLogic.getMoneyDue() + ",-");
        totalEarnings.setText("Total Earnings: €" + simulatorLogic.getTotalEarnings()+",-");
        currentCars.setText("<html><b>Current cars in the garage:</b><br/>" +
                "<br/>Normal cars: " + simulatorLogic.getNormalCars() +
                "<br/>Cars with a parking pass: " + simulatorLogic.getPassCars() +
                "<br/>Cars that reserved a spot: " + simulatorLogic.getReservationCars());
    }

    @Override
    public String getName() {
        return "TextView";
    }

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

    private void setDayEarnings(){
        dayEarnings.setText ("<html>Earnings per day:<br/>" +
                "<br/>Monday: €" + simulatorLogic.getDayEarnings().get(0) + ",-" +
                "<br/>Tuesday: €" + simulatorLogic.getDayEarnings().get(1) + ",-" +
                "<br/>Wednesday: €" + simulatorLogic.getDayEarnings().get(2) + ",-" +
                "<br/>Thursday: €" + simulatorLogic.getDayEarnings().get(3) + ",-" +
                "<br/>Friday: €" + simulatorLogic.getDayEarnings().get(4) + ",-" +
                "<br/>Saturday: €" + simulatorLogic.getDayEarnings().get(5) + ",-" +
                "<br/>Sunday: €" + simulatorLogic.getDayEarnings().get(6) + ",-</html>");
    }
}
