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

        this.setBackground(new Color(51, 51, 51));

        setLayout(new FlowLayout(FlowLayout.CENTER, 10,50));

        tickPause = new JSpinner(new SpinnerNumberModel(100, 1, 200, 1));
        tickPause.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        tickPause.getEditor().getComponent(0).setForeground(Color.lightGray);
        JLabel tickPauseLabel = new JLabel("Pause per tick (ms): ");
        tickPauseLabel.setForeground(Color.lightGray);

        garageFloors = new JSpinner(new SpinnerNumberModel(3, 1, 4, 1));
        garageFloors.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageFloors.getEditor().getComponent(0).setForeground(Color.lightGray);
        garageRows = new JSpinner(new SpinnerNumberModel(6, 1, 8, 1));
        garageRows.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageRows.getEditor().getComponent(0).setForeground(Color.lightGray);
        garagePlaces = new JSpinner(new SpinnerNumberModel(30, 1, 40, 1));
        garagePlaces.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garagePlaces.getEditor().getComponent(0).setForeground(Color.lightGray);

        JPanel garageSpinnerPanel = new JPanel();
        garageSpinnerPanel.setLayout(new BoxLayout(garageSpinnerPanel, BoxLayout.Y_AXIS));
        garageSpinnerPanel.add(garageFloors);
        garageSpinnerPanel.add(garageRows);
        garageSpinnerPanel.add(garagePlaces);

        JLabel floorLabel = new JLabel("Garage floors: ");
        floorLabel.setForeground(Color.lightGray);
        JLabel rowLabel = new JLabel("Garage rows: ");
        rowLabel.setForeground(Color.lightGray);
        JLabel placesLabel = new JLabel("Garage places: ");
        placesLabel.setForeground(Color.lightGray);

        JPanel garageLabelPanel = new JPanel();
        garageLabelPanel.setBackground(new Color(51, 51, 51));
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