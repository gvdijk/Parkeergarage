package Parkeersimulator.Main;

import Parkeersimulator.Controller.InitController;
import Parkeersimulator.Controller.RunController;
import Parkeersimulator.Controller.ViewController;
import Parkeersimulator.Model.SimulatorLogic;
import Parkeersimulator.View.CarParkView;
import Parkeersimulator.View.TextView;

import javax.swing.*;
import java.awt.*;

public class Simulator {

    private JPanel initPanel;
    private JPanel simulatorPanel;
    private JFrame screen;
    private SimulatorLogic simulatorLogic;
    private CarParkView carParkView;
    private TextView textView;
    private RunController runController;
    private ViewController viewController;
    private InitController initController;

    public Simulator (){
        screen=new JFrame("Parkeergarage");
        simulatorPanel = new JPanel();
        initPanel = new JPanel();
        simulatorLogic = new SimulatorLogic(initPanel, simulatorPanel);
        carParkView = new CarParkView(simulatorLogic);
        textView = new TextView(simulatorLogic);
        runController = new RunController(simulatorLogic);
        viewController = new ViewController(simulatorLogic);
        initController = new InitController(simulatorLogic);

        createFrame();
    }

    private void createFrame(){
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setResizable(false);
        Container contentPane = screen.getContentPane();
        simulatorPanel.setLayout(new BorderLayout());

        initPanel.add(initController);

        simulatorPanel.add(carParkView, BorderLayout.CENTER);
        simulatorPanel.add(runController, BorderLayout.SOUTH);
        simulatorPanel.add(viewController, BorderLayout.NORTH);

        contentPane.add(simulatorPanel);

        screen.pack();

        contentPane.add(initPanel);

        simulatorPanel.add(textView, BorderLayout.CENTER);

        simulatorLogic.showInitPanel(true);

        screen.setVisible(true);
    }
}
