package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;

import javax.swing.*;
import java.awt.event.*;

public class RunController extends AbstractController implements ActionListener{
    private JButton start;
    private JButton pause;
    private JButton tick;
    private JButton tick100;

    public RunController(SimulatorLogic simulatorLogic){
        super(simulatorLogic);

        start = new JButton("Start");
        start.addActionListener(this);

        pause = new JButton("Pause");
        pause.addActionListener(this);

        tick = new JButton("1 step");
        tick.addActionListener(this);

        tick100 = new JButton("100 steps");
        tick100.addActionListener(this);

        add(start);
        add(pause);
        add(tick);
        add(tick100);

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

        if (e.getSource() == tick){
            simulatorLogic.tick(1);
        }

        if (e.getSource() == tick100){
            simulatorLogic.tick(100);
        }
    }
}
