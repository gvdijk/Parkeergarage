package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;

public abstract class AbstractView extends JPanel {

    protected SimulatorLogic simulatorLogic;

    public AbstractView (SimulatorLogic simulatorLogic){
        this.simulatorLogic = simulatorLogic;
        simulatorLogic.addView(this);
    }

    abstract public void updateView();

    abstract public String getName();

}
