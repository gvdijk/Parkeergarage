package Parkeersimulator.View;

//import java.awt.*;
import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;

public class TextView extends AbstractView {

    //private Dimension size;
    private JLabel currentTick;
    private JLabel currentMinute;
    private JLabel currentHour;
    private JLabel currentDay;

    public TextView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
        //size = new Dimension(0, 0);

        currentTick = new JLabel("Current tick: " + simulatorLogic.getCurrentTick());
        currentMinute = new JLabel("Current minute: " + simulatorLogic.getMinute());
        currentHour = new JLabel("Current hour: " + simulatorLogic.getHour());
        currentDay = new JLabel("Current day: " + simulatorLogic.getDay());

        add(currentTick);
        add(currentMinute);
        add(currentHour);
        add(currentDay);

        setVisible(false);
    }

    @Override
    public void updateView() {
        currentTick.setText("Current tick: " + simulatorLogic.getCurrentTick());
        currentMinute.setText("Current minute: " + simulatorLogic.getMinute());
        currentHour.setText("Current hour: " + simulatorLogic.getHour());
        currentDay.setText("Current day: " + simulatorLogic.getDay());
    }

    @Override
    public String getName() {
        return "TextView";
    }
}
