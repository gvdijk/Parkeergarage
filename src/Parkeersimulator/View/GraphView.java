package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;

public class GraphView extends AbstractView {

    private JLabel test;

    /**
     * Constructor voor objecten van klasse GraphView.
     */
    public GraphView (SimulatorLogic simulatorLogic) {
        super(simulatorLogic);

        this.setBackground(new Color(51, 51, 51));

        test = new JLabel("Test");
        test.setForeground(Color.lightGray);

        add(test);

        setVisible(false);
    }

    @Override
    public void updateView() {

    }

    @Override
    public String getName() {
        return "GraphView";
    }
}
