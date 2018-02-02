package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitController extends AbstractController implements ActionListener {
    private JSpinner tickPause;
    private JButton start;

    public InitController(SimulatorLogic simulatorLogic) {
        super(simulatorLogic);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        tickPause = new JSpinner(new SpinnerNumberModel(100, 1, 200, 1));
        JLabel tickPauseLabel = new JLabel("Pause per tick (ms): ");

        start = new JButton("Start");
        start.addActionListener(this);

        add(Box.createRigidArea(new Dimension(0, 40)));
        add(tickPauseLabel);
        tickPauseLabel.setLabelFor(tickPause);
        add(tickPause);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(start);

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == start){
            Integer tick = (Integer)tickPause.getValue();
            simulatorLogic.initialize(tick);
            simulatorLogic.showInitPanel(false);
        }
    }
}