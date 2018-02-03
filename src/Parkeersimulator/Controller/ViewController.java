package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;
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

        this.setBackground(new Color(59, 69, 89));

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == carParkView){
            simulatorLogic.setVisibleView("CarParkView");
        }

        if (e.getSource() == textView){
            simulatorLogic.setVisibleView("TextView");
        }

        if (e.getSource() == view3){
            //TODO implement view 3
        }

        if (e.getSource() == view4){
            //TODO implement view 4
        }
    }
}
