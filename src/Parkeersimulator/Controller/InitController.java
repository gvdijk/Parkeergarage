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
    private JSpinner reservationTime;

    private JPanel tickPausePanel;
    private JPanel garageSpinnerPanel;
    private JPanel garageLabelPanel;
    private JPanel garageSettings;
    private JPanel parkingFeePanel;
    private JPanel reservationPanel;

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

        //Invoervelden voor de grootte van de garage
        garageFloors = new JSpinner(new SpinnerNumberModel());
        garageRows = new JSpinner(new SpinnerNumberModel());
        garagePlaces = new JSpinner(new SpinnerNumberModel());

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

        //JPanel die de labels en spinners voor de garageinstellingen rangschikt
        garageSettings = new JPanel();

        //Invoerveld voor de prijs van het parkeren
        parkingFee = new JSpinner();

        //Text voor de prijs van het parkeren
        JLabel parkingFeeLabel = new JLabel("Prijs per 20 minuten parkeren: ");
        parkingFeeLabel.setForeground(Color.lightGray);

        //JPanel die de labels en spinner voor de parkeer prijs rangschikt
        parkingFeePanel = new JPanel();
        parkingFeePanel.add(parkingFeeLabel);
        parkingFeePanel.add(parkingFee);
        parkingFeePanel.setBackground(new Color(51, 51, 51));

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
        reservationPanel.add(reservationTime);
        reservationPanel.setBackground(new Color(51, 51, 51));

        //Knop om de simulatie aan te maken met de ingevoerde waardes
        start = new JButton("Start");
        start.addActionListener(this);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        createControllerLayout();

        //Alle componenten aan de classe toevoegen en zichtbaar maken
        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(tickPausePanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(garageSettings);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(parkingFeePanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(reservationPanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(errorMessage);
        add(Box.createRigidArea(new Dimension(0, 40)));
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
        tickPausePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        garageFloors.setValue(3);
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
        garageSettings.setAlignmentX(Component.CENTER_ALIGNMENT);

        SpinnerNumberModel s = new SpinnerNumberModel();
        s.setValue(1.00);
        s.setStepSize(0.10);
        parkingFee.setModel(s);
        parkingFee.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        parkingFee.getEditor().getComponent(0).setForeground(Color.lightGray);
        Dimension d = parkingFee.getPreferredSize();
        d.width = 50;
        parkingFee.setPreferredSize(d);

        reservationTime.setValue(15);
        reservationTime.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        reservationTime.getEditor().getComponent(0).setForeground(Color.lightGray);
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
            double fee = (double) parkingFee.getValue();
            int time = (int) reservationTime.getValue();

            if (!checkInput(tick, garage, fee, time)){
                simulatorLogic.initialize(tick, garage, fee, time);
                simulatorLogic.showInitPanel(false);
            }
        }
    }

    /**
     * Checkt of de ingevulde waardes wel voldoen aan de minimum en maximum inputs
     *
     * @param tick  De pauze tussen ticks
     * @param garage    De grootte van de garage
     * @param fee   De prijs per kaartje
     * @param time De tijd waarop een plek van tevoren gereserveerd wordt
     * @return
     */
    private boolean checkInput(int tick, int[] garage, double fee, int time){
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
        if (fee < 0){
            errorMessage.setText("Kaartjes kunnen geen negatieve prijs hebben");
            error = true;
        }
        if (time < 0 || time > 45){
            errorMessage.setText("Tijd van tevoren moet tussen 0 en 45 liggen");
            error = true;
        }

        return error;
    }
}