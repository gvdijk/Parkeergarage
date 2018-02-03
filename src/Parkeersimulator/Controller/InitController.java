package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitController extends AbstractController implements ActionListener {
    private JSpinner tickPause;
    private JSpinner garageFloors;
    private JSpinner garageRows;
    private JSpinner garagePlaces;
    private JButton start;

    public InitController(SimulatorLogic simulatorLogic) {
        super(simulatorLogic);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10,50));

        tickPause = new JSpinner(new SpinnerNumberModel(100, 1, 200, 1));
        JLabel tickPauseLabel = new JLabel("Pause per tick (ms): ");

        garageFloors = new JSpinner(new SpinnerNumberModel(3, 1, 4, 1));
        garageRows = new JSpinner(new SpinnerNumberModel(6, 1, 8, 1));
        garagePlaces = new JSpinner(new SpinnerNumberModel(30, 1, 40, 1));

        JPanel garageSpinnerPanel = new JPanel();
        garageSpinnerPanel.setLayout(new BoxLayout(garageSpinnerPanel, BoxLayout.Y_AXIS));
        garageSpinnerPanel.add(garageFloors);
        garageSpinnerPanel.add(garageRows);
        garageSpinnerPanel.add(garagePlaces);

        JLabel floorLabel = new JLabel("Garage floors: ");
        JLabel rowLabel = new JLabel("Garage rows: ");
        JLabel placesLabel = new JLabel("Garage places: ");

        JPanel garageLabelPanel = new JPanel();
        garageLabelPanel.setLayout(new BoxLayout(garageLabelPanel, BoxLayout.Y_AXIS));
        garageLabelPanel.add(floorLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(rowLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(placesLabel);

        start = new JButton("Start");
        start.addActionListener(this);

        add(Box.createRigidArea(new Dimension(0, 40)));
        add(tickPauseLabel);
        add(tickPause);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(garageLabelPanel);
        add(garageSpinnerPanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(start);

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == start){
            int tick = (int)tickPause.getValue();
            int[] garage = new int[]{(int)garageFloors.getValue(),
                                     (int)garageRows.getValue(),
                                     (int)garagePlaces.getValue()
            };

            simulatorLogic.initialize(tick, garage);
            simulatorLogic.showInitPanel(false);
        }
    }
}