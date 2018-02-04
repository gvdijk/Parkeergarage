package Parkeersimulator.Main;

import Parkeersimulator.Controller.InitController;
import Parkeersimulator.Controller.RunController;
import Parkeersimulator.Model.SimulatorLogic;
import Parkeersimulator.View.CarParkView;
import Parkeersimulator.View.TextView;

import javax.swing.*;
import java.awt.*;

public class Simulator {

    private JPanel initPanel;
    private JPanel simulatorPanel;
    private JTabbedPane tabbedPane;

    private JFrame screen;
    private SimulatorLogic simulatorLogic;
    private CarParkView carParkView;
    private TextView textView;
    private RunController runController;
    private InitController initController;

    public Simulator (){
        screen=new JFrame("Parkeergarage");
        screen.setBackground(new Color(51, 51, 51));
        simulatorPanel = new JPanel();
        initPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        simulatorLogic = new SimulatorLogic(initPanel, simulatorPanel);
        carParkView = new CarParkView(simulatorLogic);
        textView = new TextView(simulatorLogic);
        runController = new RunController(simulatorLogic);
        initController = new InitController(simulatorLogic);

        createFrame();
    }

    private void createFrame(){
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setResizable(false);
        Container contentPane = screen.getContentPane();
        simulatorPanel.setLayout(new BorderLayout());

        initPanel.add(initController);
        initPanel.setBackground(new Color(51, 51, 51));

        tabbedPane.addTab("Cark Park View", carParkView);
        tabbedPane.addTab("View 2", null);
        tabbedPane.addTab("Text View", textView);
        tabbedPane.addTab("Graph View", null);

        simulatorPanel.add(tabbedPane, BorderLayout.CENTER);
        simulatorPanel.add(runController, BorderLayout.SOUTH);

        contentPane.add(simulatorPanel);

        screen.pack();

        contentPane.add(initPanel);

        simulatorLogic.showInitPanel(true);

        screen.setVisible(true);
    }
}