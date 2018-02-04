package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RunController extends AbstractController implements ActionListener{
    private JButton run;
    private JButton minute;
    private JButton hour;
    private JButton day;
    private JButton week;
    private JButton reset;

    public RunController(SimulatorLogic simulatorLogic){
        super(simulatorLogic);

        run = new JButton("Run");
        run.addActionListener(this);

        minute = new JButton("+1 minute");
        minute.addActionListener(this);

        hour = new JButton("+1 hour");
        hour.addActionListener(this);

        day = new JButton("+1 day");
        day.addActionListener(this);

        week = new JButton("Skip to end");
        week.addActionListener(this);

        reset = new JButton("Reset");
        reset.addActionListener(this);

        add(run);
        add(minute);
        add(hour);
        add(day);
        add(week);
        add(reset);

        this.setBackground(new Color(59, 69, 89));

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == run){
            pressedPause();
        }

        if (e.getSource() == minute){
            if (run.getText().equals("Pause")) {pressedPause();}
            simulatorLogic.tick(false, 1);
        }

        if (e.getSource() == hour){
            if (run.getText().equals("Pause")) {pressedPause();}
            simulatorLogic.tick(false, 60);
        }

        if (e.getSource() == day){
            if (run.getText().equals("Pause")) {pressedPause();}
            simulatorLogic.tick(false, 1440);
        }

        if (e.getSource() == week){
            if (run.getText().equals("Pause")) {pressedPause();}
            simulatorLogic.tick(false, 10079);
        }

        if (e.getSource() == reset) {
            simulatorLogic.pause();
            run.setText("Run");
            simulatorLogic.reset();
            simulatorLogic.showInitPanel(true);
        }
    }

    private void pressedPause(){
        if (run.getText().equals("Pause")) {
            run.setText("Run");
            simulatorLogic.pause();
        }else{
            run.setText("Pause");
            simulatorLogic.start();
        }
    }
}