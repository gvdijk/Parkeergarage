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
    private JSpinner tickPause;
    private JSpinner garageFloors;
    private JSpinner garageRows;
    private JSpinner garagePlaces;
    private JLabel errorMessage;
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
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Courier", Font.BOLD, 20));

        //Invoerveld voor het instellen van de simulatiepauze
        tickPause = new JSpinner(new SpinnerNumberModel());
        tickPause.setValue(100);
        tickPause.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        tickPause.getEditor().getComponent(0).setForeground(Color.lightGray);
        JLabel tickPauseLabel = new JLabel("Pauze per simulatie minuut (milliseconde): ");
        tickPauseLabel.setForeground(Color.lightGray);

        //JPanel die de tekst en het invoerveld voor de simulatiepauze rangschikt
        JPanel tickPausePanel = new JPanel();
        tickPausePanel.setBackground(new Color(51, 51, 51));
        tickPausePanel.add(tickPauseLabel);
        tickPausePanel.add(tickPause);
        tickPausePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Invoervelden voor de grootte van de garage
        garageFloors = new JSpinner(new SpinnerNumberModel());
        garageFloors.setValue(3);
        garageFloors.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageFloors.getEditor().getComponent(0).setForeground(Color.lightGray);
        garageRows = new JSpinner(new SpinnerNumberModel());
        garageRows.setValue(6);
        garageRows.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garageRows.getEditor().getComponent(0).setForeground(Color.lightGray);
        garagePlaces = new JSpinner(new SpinnerNumberModel());
        garagePlaces.setValue(30);
        garagePlaces.getEditor().getComponent(0).setBackground(new Color(51, 51, 51));
        garagePlaces.getEditor().getComponent(0).setForeground(Color.lightGray);

        //JPanel die de invoervelden van de garage rangschikt
        JPanel garageSpinnerPanel = new JPanel();
        garageSpinnerPanel.setLayout(new BoxLayout(garageSpinnerPanel, BoxLayout.Y_AXIS));
        garageSpinnerPanel.add(garageFloors);
        garageSpinnerPanel.add(garageRows);
        garageSpinnerPanel.add(garagePlaces);

        //Text voor de garageinstellingen
        JLabel floorLabel = new JLabel("Aantal verdiepingen: ");
        floorLabel.setForeground(Color.lightGray);
        JLabel rowLabel = new JLabel("Aantal rijen: ");
        rowLabel.setForeground(Color.lightGray);
        JLabel placesLabel = new JLabel("Plekken per rij: ");
        placesLabel.setForeground(Color.lightGray);

        //JPanel die alle text voor de instellingen rangschikt
        JPanel garageLabelPanel = new JPanel();
        garageLabelPanel.setBackground(new Color(51, 51, 51));
        garageLabelPanel.setLayout(new BoxLayout(garageLabelPanel, BoxLayout.Y_AXIS));
        garageLabelPanel.add(floorLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(rowLabel);
        garageLabelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        garageLabelPanel.add(placesLabel);

        //JPanel die de labels en spinners voor de garageinstellingen rangschikt
        JPanel garageSettings = new JPanel();
        garageSettings.add(garageLabelPanel);
        garageSettings.add(garageSpinnerPanel);
        garageSettings.setBackground(new Color(51, 51, 51));
        garageSettings.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Textvak dat een errorbericht laat zien als de ingevoerde waardes niet kloppen
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.red);
        errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Knop om de simulatie aan te maken met de ingevoerde waardes
        start = new JButton("Start");
        start.addActionListener(this);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Alle componenten aan de classe toevoegen en zichtbaar maken
        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(tickPausePanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(garageSettings);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(errorMessage);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(start);
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

            if (!checkInput(tick, garage)){
                simulatorLogic.initialize(tick, garage);
                simulatorLogic.showInitPanel(false);
            }
        }
    }

    private boolean checkInput(int tick, int[] garage){
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

        return error;
    }
}