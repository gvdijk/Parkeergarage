package Parkeersimulator.Controller;

import Parkeersimulator.Model.SimulatorLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class die de instellingen van de simulator initialiseerd voordat de simulator gestart wordt
 */
public class InitController extends AbstractController implements ActionListener {
    private JLabel title;
    private JLabel errorMessage;

    private JSpinner tickPause;
    private JSpinner garageFloors;
    private JSpinner garageRows;
    private JSpinner garagePlaces;
    private JSpinner parkingFee;
    private JSpinner reservationFee;
    private JSpinner reservationTime;
    private JSpinner enterSpeed;
    private JSpinner paymentSpeed;
    private JSpinner exitSpeed;

    private JPanel tickPausePanel;
    private JPanel garageSpinnerPanel;
    private JPanel garageLabelPanel;
    private JPanel garageSettings;
    private JPanel parkingFeePanel;
    private JPanel reservationFeePanel;
    private JPanel reservationPanel;
    private JPanel entraceSpinnerPanel;
    private JPanel entranceLabelPanel;
    private JPanel entranceSettings;

    private JButton start;

    /**
     * Parameter die alle visuele componenten van de controller initialiseerd en aan het scherm toevoegd
     *
     * @param simulatorLogic De instantie van simulatorlogic die het simulatormodel aanstuurt
     */
    public InitController(SimulatorLogic simulatorLogic) {
        super(simulatorLogic);

        this.setBackground(new Color(51, 51, 51));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Titel object voor boven aan het scherm
        title = new JLabel("Parkeergarage simulatie software v0.9 ofzo");

        //Invoerveld voor het instellen van de simulatiepauze
        tickPause = new JSpinner(new SpinnerNumberModel());

        //JPanel die de tekst en het invoerveld voor de simulatiepauze rangschikt
        tickPausePanel = new JPanel();
        tickPausePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Invoervelden voor de snelheden van het binnenkomen en weggaat
        enterSpeed = new JSpinner();
        paymentSpeed = new JSpinner();
        exitSpeed = new JSpinner();

        //JPanel die de invoervelden voor de in en uitgangen rangschikt
        entraceSpinnerPanel = new JPanel();

        //Text voor de in en uitgang instellingen
        JLabel entranceLabel = new JLabel("Ingang breedte (auto's per minuut): ");
        entranceLabel.setForeground(Color.lightGray);
        JLabel paymentLabel = new JLabel("Betaalplek breedte (auto's per minuut): ");
        paymentLabel.setForeground(Color.lightGray);
        JLabel exitLabel = new JLabel("Uitgang breedte (auto's per minuut): ");
        exitLabel.setForeground(Color.lightGray);

        //JPanel die alle tekst voor de in en uitgangen rangschikt
        entranceLabelPanel = new JPanel();
        entranceLabelPanel.setBackground(new Color(51, 51, 51));
        entranceLabelPanel.setLayout(new BoxLayout(entranceLabelPanel, BoxLayout.Y_AXIS));
        entranceLabelPanel.add(entranceLabel);
        entranceLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        entranceLabelPanel.add(paymentLabel);
        entranceLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        entranceLabelPanel.add(exitLabel);

        //JPanel die alle labels en invoervelden voor de in en uitgangen rangschikt
        entranceSettings = new JPanel();
        entranceSettings.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Invoervelden voor de grootte van de garage
        garageFloors = new JSpinner();
        garageRows = new JSpinner();
        garagePlaces = new JSpinner();

        //JPanel die de invoervelden van de garage rangschikt
        garageSpinnerPanel = new JPanel();

        //Text voor de garageinstellingen
        JLabel floorLabel = new JLabel("Aantal verdiepingen: ");
        floorLabel.setForeground(Color.lightGray);
        JLabel rowLabel = new JLabel("Aantal rijen: ");
        rowLabel.setForeground(Color.lightGray);
        JLabel placesLabel = new JLabel("Plekken per rij: ");
        placesLabel.setForeground(Color.lightGray);

        //JPanel die alle text voor de instellingen rangschikt
        garageLabelPanel = new JPanel();
        garageLabelPanel.setBackground(new Color(51, 51, 51));
        garageLabelPanel.setLayout(new BoxLayout(garageLabelPanel, BoxLayout.Y_AXIS));
        garageLabelPanel.add(floorLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(rowLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(placesLabel);

        //JPanel die de labels en invoervelden voor de garageinstellingen rangschikt
        garageSettings = new JPanel();
        garageSettings.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Invoerveld voor de prijs van het parkeren
        parkingFee = new JSpinner();

        //Text voor de prijs van het parkeren
        JLabel parkingFeeLabel = new JLabel("Prijs per 20 minuten parkeren: ");
        parkingFeeLabel.setForeground(Color.lightGray);

        //JPanel die de labels en spinner voor de parkeerprijs rangschikt
        parkingFeePanel = new JPanel();
        parkingFeePanel.add(parkingFeeLabel);
        parkingFeePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Tekst voor de kosten van reserveren
        JLabel reservationFeeText = new JLabel("Eenmalige kosten per reservering: ");
        reservationFeeText.setForeground(Color.lightGray);

        //Invoerveld voor de kosten van reserveren
        reservationFee = new JSpinner();

        //JPanel die de labels en spinner voor de reserveerkosten rangschikt
        reservationFeePanel = new JPanel();
        reservationFeePanel.add(reservationFeeText);
        reservationFeePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Textvak dat een errorbericht laat zien als de ingevoerde waardes niet kloppen
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.red);
        errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Tekst voor de tijd waarop men van tevoren mag reserveren
        JLabel reservationLabel = new JLabel("Tijd voor aankomst dat plek word gereserveerd: ");
        reservationLabel.setForeground(Color.lightGray);

        //Invoerveld voor de tijd waarop men van tevoren mag reserveren
        reservationTime = new JSpinner();

        //JPanel die de labels en spinner voor de reserveertijd rangschikt
        reservationPanel = new JPanel();
        reservationPanel.add(reservationLabel);
        reservationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Knop om de simulatie aan te maken met de ingevoerde waardes
        start = new JButton("Start");
        start.addActionListener(this);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        createControllerLayout();

        //Alle componenten aan de class toevoegen en zichtbaar maken
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(tickPausePanel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(garageSettings);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(entranceSettings);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(parkingFeePanel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(reservationFeePanel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(reservationPanel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(errorMessage);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(start);
    }

    /**
     * Stelt alle kleuren, groottes en waarden in per variable en rangschikt ze op het scherm
     */
    private void createControllerLayout(){
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Courier", Font.BOLD, 20));

        tickPause.setValue(100);
        tickPause.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        tickPause.getEditor().getComponent(0).setForeground(Color.lightGray);
        JLabel tickPauseLabel = new JLabel("Pauze per simulatie minuut (milliseconde): ");
        tickPauseLabel.setForeground(Color.lightGray);

        tickPausePanel.setBackground(new Color(51, 51, 51));
        tickPausePanel.add(tickPauseLabel);
        tickPausePanel.add(tickPause);

        enterSpeed.setValue(3);
        Dimension enterDim = enterSpeed.getPreferredSize();
        enterDim.width = 50;
        enterSpeed.setPreferredSize(enterDim);
        enterSpeed.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        enterSpeed.getEditor().getComponent(0).setForeground(Color.lightGray);
        paymentSpeed.setValue(7);
        paymentSpeed.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        paymentSpeed.getEditor().getComponent(0).setForeground(Color.lightGray);
        exitSpeed.setValue(5);
        exitSpeed.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        exitSpeed.getEditor().getComponent(0).setForeground(Color.lightGray);

        entraceSpinnerPanel.setLayout(new BoxLayout(entraceSpinnerPanel, BoxLayout.Y_AXIS));
        entraceSpinnerPanel.add(enterSpeed);
        entraceSpinnerPanel.add(paymentSpeed);
        entraceSpinnerPanel.add(exitSpeed);

        entranceSettings.add(entranceLabelPanel);
        entranceSettings.add(entraceSpinnerPanel);
        entranceSettings.setBackground(new Color(51, 51, 51));

        garageFloors.setValue(3);
        Dimension garageDim = garageFloors.getPreferredSize();
        garageDim.width = 50;
        garageFloors.setPreferredSize(garageDim);
        garageFloors.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageFloors.getEditor().getComponent(0).setForeground(Color.lightGray);
        garageRows.setValue(6);
        garageRows.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageRows.getEditor().getComponent(0).setForeground(Color.lightGray);
        garagePlaces.setValue(30);
        garagePlaces.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garagePlaces.getEditor().getComponent(0).setForeground(Color.lightGray);

        garageSpinnerPanel.setLayout(new BoxLayout(garageSpinnerPanel, BoxLayout.Y_AXIS));
        garageSpinnerPanel.add(garageFloors);
        garageSpinnerPanel.add(garageRows);
        garageSpinnerPanel.add(garagePlaces);

        garageSettings.add(garageLabelPanel);
        garageSettings.add(garageSpinnerPanel);
        garageSettings.setBackground(new Color(51, 51, 51));

        SpinnerNumberModel s = new SpinnerNumberModel();
        s.setValue(1.00);
        s.setStepSize(0.10);
        parkingFee.setModel(s);
        parkingFee.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        parkingFee.getEditor().getComponent(0).setForeground(Color.lightGray);
        Dimension parkingDim = parkingFee.getPreferredSize();
        parkingDim.width = 50;
        parkingFee.setPreferredSize(parkingDim);

        SpinnerNumberModel r = new SpinnerNumberModel();
        r.setValue(3.00);
        r.setStepSize(0.10);
        reservationFee.setModel(r);
        reservationFee.setPreferredSize(parkingDim);
        reservationFee.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        reservationFee.getEditor().getComponent(0).setForeground(Color.lightGray);

        reservationTime.setValue(15);
        reservationTime.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        reservationTime.getEditor().getComponent(0).setForeground(Color.lightGray);

        reservationPanel.add(reservationTime);
        reservationPanel.setBackground(new Color(51, 51, 51));
        parkingFeePanel.add(parkingFee);
        parkingFeePanel.setBackground(new Color(51, 51, 51));
        reservationFeePanel.setBackground(new Color(51, 51, 51));
        reservationFeePanel.add(reservationFee);
    }

    /**
     * Overridden van Actionlistener. Functie om de start knop mee aan te sturen.
     *
     * @param e Actionevent waarmee gezien kan worden welke actie de gebruiker heeft uitgevoerd
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == start){
            int tick = (int)tickPause.getValue();
            int[] garage = new int[]{(int)garageFloors.getValue(),
                                     (int)garageRows.getValue(),
                                     (int)garagePlaces.getValue()
            };
            int[] speeds = new int[]{(int)enterSpeed.getValue(),
                                     (int)paymentSpeed.getValue(),
                                     (int)exitSpeed.getValue()
            };
            double parkFee = (double) parkingFee.getValue();
            double reserveFee = (double) reservationFee.getValue();
            int time = (int) reservationTime.getValue();

            if (!checkInput(tick, garage, speeds, parkFee, reserveFee, time)){
                simulatorLogic.initialize(tick, garage, speeds, parkFee, reserveFee, time);
                simulatorLogic.showInitPanel(false);
            }
        }
    }

    /**
     * Checkt of de ingevulde waardes wel voldoen aan de minimum en maximum inputs
     *
     * @param tick  De pauze tussen ticks
     * @param garage    De grootte van de garage
     * @param speeds    De grootte van alle ingangen, uitgangen en betaalrijen
     * @param parkFee   De prijs per kaartje
     * @param time De tijd waarop een plek van tevoren gereserveerd wordt
     * @return
     */
    private boolean checkInput(int tick, int[] garage, int[] speeds, double parkFee, double reservationFee, int time){
        errorMessage.setText("");
        boolean error = false;

        if (tick < 1 || tick > 200){
            errorMessage.setText("Pauze tussen ticks moet tussen 1 en 200 liggen");
            error = true;
        }
        if (garage[0] < 1 || garage[0] > 4){
            errorMessage.setText("Garage verdiepingen moet tussen 1 en 4 liggen");
            error = true;
        }
        if (garage[1] < 1 || garage[1] > 8){
            errorMessage.setText("Garage rijen moet tussen 1 en 8 liggen");
            error = true;
        }
        if (garage[2] < 1 || garage[2] > 40){
            errorMessage.setText("Garage plekken moet tussen 1 en 40 liggen");
            error = true;
        }
        if (speeds[0] < 1){
            errorMessage.setText("Ingang breedte kan geen 0 of kleiner zijn ");
            error = true;
        }
        if (speeds[1] < 1){
            errorMessage.setText("Betaling breedte kan geen 0 of kleiner zijn ");
            error = true;
        }
        if (speeds[2] < 1){
            errorMessage.setText("Uitgang breedte kan geen 0 of kleiner zijn ");
            error = true;
        }
        if (parkFee < 0){
            errorMessage.setText("Kaartjes kunnen geen negatieve prijs hebben");
            error = true;
        }
        if (reservationFee < 0){
            errorMessage.setText("Reserveringen kunnen geen negatieve prijs hebben");
            error = true;
        }
        if (time < 0 || time > 45){
            errorMessage.setText("Tijd van tevoren moet tussen 0 en 45 liggen");
            error = true;
        }

        return error;
    }
}