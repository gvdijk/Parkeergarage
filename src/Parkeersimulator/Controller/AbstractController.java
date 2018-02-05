package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;

/**
 * Basis voor alle controller objecten
 */
public abstract class AbstractController extends JPanel{
    protected SimulatorLogic simulatorLogic;

    public AbstractController(SimulatorLogic simulatorLogic){
        this.simulatorLogic = simulatorLogic;
    }
}
