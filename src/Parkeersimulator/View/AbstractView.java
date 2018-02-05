package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;

/**
 * De basis voor alle views binnen de applicatie
 */
public abstract class AbstractView extends JPanel {

    protected SimulatorLogic simulatorLogic;

    public AbstractView (SimulatorLogic simulatorLogic){
        this.simulatorLogic = simulatorLogic;
        simulatorLogic.addView(this);
    }

    /**
     * Update alle informatie op het scherm
     */
    abstract public void updateView();
}
