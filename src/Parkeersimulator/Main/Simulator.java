package Parkeersimulator.Main;

import Parkeersimulator.Controller.RunController;
import Parkeersimulator.Controller.ViewController;
import Parkeersimulator.Model.SimulatorLogic;
import Parkeersimulator.View.CarParkView;
import Parkeersimulator.View.TextView;

import javax.swing.*;
import java.awt.*;

public class Simulator {

    private JFrame screen;
    private SimulatorLogic simulatorLogic;
    private CarParkView carParkView;
    private TextView textView;
    private RunController runController;
    private ViewController viewController;

    public Simulator (){
        simulatorLogic = new SimulatorLogic();
        carParkView = new CarParkView(simulatorLogic);
        textView = new TextView(simulatorLogic);
        textView.setLayout(new BoxLayout(textView, BoxLayout.PAGE_AXIS));
        runController = new RunController(simulatorLogic);
        viewController = new ViewController(simulatorLogic);

        screen=new JFrame("Parkeergarage");
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = screen.getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(runController, BorderLayout.SOUTH);
        contentPane.add(viewController, BorderLayout.NORTH);
        screen.pack();
        contentPane.add(textView, BorderLayout.CENTER);
        screen.setVisible(true);
    }
}
