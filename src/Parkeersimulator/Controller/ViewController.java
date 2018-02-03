package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController extends AbstractController implements ActionListener {

    private JButton carParkView;
    private JButton textView;
    private JButton chartView;
    private JButton view4;

    public ViewController (SimulatorLogic simulatorLogic){
        super(simulatorLogic);

        carParkView = new JButton("CarParkView");
        carParkView.addActionListener(this);

        textView = new JButton("TextView");
        textView.addActionListener(this);

        chartView = new JButton("ChartView");
        chartView.addActionListener(this);

        view4 = new JButton("View 4");
        view4.addActionListener(this);

        add(carParkView);
        add(textView);
        add(chartView);
        add(view4);

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == carParkView){
            simulatorLogic.setVisible("CarParkView");
        }

        if (e.getSource() == textView){
            simulatorLogic.setVisible("TextView");
        }

        if (e.getSource() == chartView){
            simulatorLogic.setVisible("ChartView");
        }

        if (e.getSource() == view4){
            //TODO implement view 4
        }
    }
}
