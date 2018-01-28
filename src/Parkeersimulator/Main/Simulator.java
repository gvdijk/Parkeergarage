package Parkeersimulator.Main;

import Parkeersimulator.Controller.RunController;
import Parkeersimulator.Model.SimulatorLogic;
import Parkeersimulator.View.CarParkView;

import javax.swing.*;
import java.awt.*;

public class Simulator {

    private JFrame screen;
    private SimulatorLogic simulatorLogic;
    private CarParkView carParkView;
    private RunController runController;

    public Simulator (){
        simulatorLogic = new SimulatorLogic();
        carParkView = new CarParkView(simulatorLogic);
        runController = new RunController(simulatorLogic);

        screen=new JFrame("Parkeergarage");
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = screen.getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(runController, BorderLayout.SOUTH);
        screen.pack();
        screen.setVisible(true);
    }
}
