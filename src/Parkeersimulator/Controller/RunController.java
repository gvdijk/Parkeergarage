package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RunController extends AbstractController implements ActionListener{
    private JButton start;
    private JButton pause;
    private JButton minute;
    private JButton hour;
    private JButton day;

    public RunController(SimulatorLogic simulatorLogic){
        super(simulatorLogic);

        start = new JButton("Start");
        start.addActionListener(this);

        pause = new JButton("Pause");
        pause.addActionListener(this);

        minute = new JButton("+1 minute");
        minute.addActionListener(this);

        hour = new JButton("+1 hour");
        hour.addActionListener(this);

        day = new JButton("+1 day");
        day.addActionListener(this);

        add(start);
        add(pause);
        add(minute);
        add(hour);
        add(day);

        this.setBackground(new Color(59, 69, 89));

        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == start){
            simulatorLogic.start();
        }

        if (e.getSource() == pause){
            simulatorLogic.pause();
        }

        if (e.getSource() == minute){
            simulatorLogic.tick(1);
        }

        if (e.getSource() == hour){
            simulatorLogic.tick(60);
        }

        if (e.getSource() == day){
            simulatorLogic.tick(1440);
        }
    }
}
