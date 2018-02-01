package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController extends AbstractController implements ActionListener {

    private JButton carParkView;
    private JButton textView;
    private JButton view3;
    private JButton view4;

    public ViewController (SimulatorLogic simulatorLogic){
        super(simulatorLogic);

        carParkView = new JButton("CarParkView");
        carParkView.addActionListener(this);

        textView = new JButton("TextView");
        textView.addActionListener(this);

        view3 = new JButton("View 3");
        view3.addActionListener(this);

        view4 = new JButton("View 4");
        view4.addActionListener(this);

        add(carParkView);
        add(textView);
        add(view3);
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

        if (e.getSource() == view3){
            //TODO implement view 3
        }

        if (e.getSource() == view4){
            //TODO implement view 4
        }
    }
}
