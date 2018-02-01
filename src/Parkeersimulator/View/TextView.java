package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;

public class TextView extends AbstractView {

    private JLabel currentTick;
    private JLabel currentDay;
    private JLabel currentHour;
    private JLabel currentMinute;
    private JLabel totalEarnings;
    private JLabel dayEarnings;

    public TextView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        currentTick = new JLabel("Current tick: " + simulatorLogic.getCurrentTick());
        currentDay = new JLabel("Current day: " + convertDay(simulatorLogic.getDay()));
        currentHour = new JLabel("Current hour: " + simulatorLogic.getHour());
        currentMinute = new JLabel("Current minute: " + simulatorLogic.getMinute());
        dayEarnings = new JLabel("");
        setDayEarnings();
        totalEarnings = new JLabel("Total Earnings: €" + simulatorLogic.getTotalEarnings() +",-");

        /*
        currentTick.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentHour.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentMinute.setAlignmentX(Component.CENTER_ALIGNMENT);
        dayEarnings.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalEarnings.setAlignmentX(Component.CENTER_ALIGNMENT);
        */

        add(Box.createRigidArea(new Dimension(5, 50)));
        add(currentTick);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(currentDay);
        add(currentHour);
        add(currentMinute);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(dayEarnings);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(totalEarnings);

        setVisible(false);
    }

    @Override
    public void updateView() {
        currentTick.setText("Current tick: " + simulatorLogic.getCurrentTick());
        currentDay.setText("Current day: " + convertDay(simulatorLogic.getDay()));
        currentMinute.setText("Current minute: " + simulatorLogic.getMinute());
        currentHour.setText("Current hour: " + simulatorLogic.getHour());
        setDayEarnings();
        totalEarnings.setText("Total Earnings: €" + simulatorLogic.getTotalEarnings()+",-");
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
