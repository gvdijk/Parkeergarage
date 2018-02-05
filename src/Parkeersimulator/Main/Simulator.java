package Parkeersimulator.Main;

import Parkeersimulator.Controller.InitController;
import Parkeersimulator.Controller.RunController;
import Parkeersimulator.Model.SimulatorLogic;
import Parkeersimulator.View.*;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

/**
 * Maakt alle schermcomponenten aan
 */
public class Simulator {

    private JPanel initPanel;   //Het paneel wat de initiele instellingen beheert
    private JPanel simulatorPanel;  //Het paneel die de simulator en simulator tabs beheert
    private JTabbedPane tabbedPane; //Paneel wat de tabjes mogelijk maakt
    private JFrame screen;  //Applicatie frame
    private SimulatorLogic simulatorLogic;  //Alle logica voor het model van de simulator
    private CarParkView carParkView;    //De visuele weergave van de parkeergarage
    private TextView textView;      //Alle simulator data in textweergave
    private GraphView graphView;    //Grafiekweergave van de textdata
    private QueueView queueView;    //Informatie over de queues
    private ReservationView reservationView;    //Informatie over de reserveringen
    private RunController runController;        //Controller die gebruikt kan worden tijdens de simulatie
    private InitController initController;      //Controller die de initiele instellingen beheert

    /**
     * Maakt alle nieuwe objecten aan die gebruikt worden in de simulator
     */
    public Simulator (){
        screen=new JFrame("Parkeergarage");
        screen.setBackground(new Color(51, 51, 51));
        simulatorPanel = new JPanel();
        initPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        simulatorLogic = new SimulatorLogic(initPanel, simulatorPanel);
        carParkView = new CarParkView(simulatorLogic);
        textView = new TextView(simulatorLogic);
        graphView = new GraphView(simulatorLogic);
        queueView = new QueueView(simulatorLogic);
        reservationView = new ReservationView(simulatorLogic);
        runController = new RunController(simulatorLogic);
        initController = new InitController(simulatorLogic);

        createFrame();
    }

    /**
     * Rangschikt alle schermcomponenten van de simulator
     */
    private void createFrame(){
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.setResizable(false);
        Container contentPane = screen.getContentPane();
        simulatorPanel.setLayout(new BorderLayout());

        initPanel.add(initController);
        initPanel.setBackground(new Color(51, 51, 51));

        tabbedPane.addTab("Cark Park View", carParkView);
        tabbedPane.addTab("Text View", textView);
        tabbedPane.addTab("Queue View", queueView);
        tabbedPane.addTab("Graph View", graphView);
        tabbedPane.addTab("Reservation Information", reservationView);

        simulatorPanel.add(tabbedPane, BorderLayout.CENTER);
        simulatorPanel.add(runController, BorderLayout.SOUTH);

        contentPane.add(simulatorPanel);

        screen.pack();

        contentPane.add(initPanel);

        simulatorLogic.showInitPanel(true);

        screen.setVisible(true);
    }
}