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
    private JButton reset;

    /**
     * Maakt een nieuw object aan voor alle buttons
     *
     * @param simulatorLogic De simulatorlogic die alle schermen beheert
     */
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

        reset = new JButton("Reset");
        reset.addActionListener(this);

        add(run);
        add(minute);
        add(hour);
        add(day);
        add(reset);

        this.setBackground(new Color(59, 69, 89));

        setVisible(true);
    }

    /**
     * Verwerkt gebruiker acties en roept de juiste functies aan gebaseerd op de geklikte knop
     *
     * @param e De actie die door de gebruiker gepleegd is
     */
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

        if (e.getSource() == reset) {
            simulatorLogic.pause();
            run.setText("Run");
            simulatorLogic.reset();
            simulatorLogic.showInitPanel(true);
        }
    }

    /**
     * Bepaald of de pauze knop of de run knop gebruikt moet worden na een click
     */
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