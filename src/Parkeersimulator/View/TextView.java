package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;

public class TextView extends AbstractView {

    private JLabel currentTime;
    private JLabel dayEarnings;
    private JLabel earnings;
    private JLabel currentCars;
    private JLabel currentCarPercentages;

    public TextView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);

        this.setBackground(new Color(51, 51, 51));

        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        currentTime = new JLabel("<html>Current day: " + convertDay(simulatorLogic.getDay()) +
                "<br/>Current hour: " + simulatorLogic.getHour() +
                "<br/>Current minute: " + simulatorLogic.getMinute());
        currentTime.setForeground(Color.lightGray);
        dayEarnings = new JLabel("");
        dayEarnings.setForeground(Color.lightGray);
        setDayEarnings();
        earnings = new JLabel("<html>Money due from parked cars: €" + simulatorLogic.getMoneyDue() + ",-<br/>" +
                "<br/>Total Earnings: €" + simulatorLogic.getTotalEarnings() + ",-");
        earnings.setForeground(Color.lightGray);
        currentCars = new JLabel("<html>Current cars in the garage:<br/>" +
                "<br/>Normal cars: " + simulatorLogic.getNormalCars() +
                "<br/>Cars with a parking pass: " + simulatorLogic.getPassCars() +
                "<br/>Cars that reserved a spot: " + simulatorLogic.getReservationCars());
        currentCars.setForeground(Color.lightGray);
        currentCarPercentages = new JLabel();
        currentCarPercentages.setForeground(Color.lightGray);

        add(currentTime);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(dayEarnings);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(earnings);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(currentCars);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(currentCarPercentages);
        setVisible(false);
    }

    @Override
    public void updateView() {
        currentTime.setText("<html>Current day: " + convertDay(simulatorLogic.getDay()) +
                "<br/>Current hour: " + simulatorLogic.getHour() +
                "<br/>Current minute: " + simulatorLogic.getMinute());
        setDayEarnings();
        earnings.setText("<html>Money due from parked cars: €" + simulatorLogic.getMoneyDue() + ",-<br/>" +
                "<br/>Total Earnings: €" + simulatorLogic.getTotalEarnings() + ",-");
        currentCars.setText("<html><b>Current cars in the garage:</b><br/>" +
                "<br/>Normal cars: " + simulatorLogic.getNormalCars() +
                "<br/>Cars with a parking pass: " + simulatorLogic.getPassCars() +
                "<br/>Cars that reserved a spot: " + simulatorLogic.getReservationCars());
        currentCarPercentages.setText("<html>Current distribution of cars (%):<br/>" +
                "<br/>Normal cars: " + simulatorLogic.getCarPercentages()[0] + "%" +
                "<br/>Parking pass cars: " + simulatorLogic.getCarPercentages()[1] + "%" +
                "<br/>Reserved spot cars: " + simulatorLogic.getCarPercentages()[2] + "%");
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
