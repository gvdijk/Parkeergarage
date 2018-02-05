package Parkeersimulator.Model;

import Parkeersimulator.Main.Simulator;
import Parkeersimulator.View.AbstractView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * De basis voor alle modellen binnen de applicatie
 */
public abstract class AbstractModel {
    private List<AbstractView> views;
    private JPanel initPanel;
    private JPanel simulatorPanel;

    public AbstractModel (JPanel init, JPanel sim){
        views = new ArrayList<>();
        initPanel = init;
        simulatorPanel = sim;
    }

    /**
     * Voeg een view toe om geupdate te worden elke tick
     *
     * @param view De view die toegevoegd moet worden
     */
    public void addView (AbstractView view){
        views.add(view);
    }

    /**
     * Update alle views
     */
    public void updateViews(){
        // Update all views.
        for (AbstractView v: views){
            v.updateView();
        }
    }

    /**
     * Laat de initele instellingen wel of niet zien
     *
     * @param show  Boolean om te bepalen of de instellingen wel of niet tevoorschijn moeten komen
     */
    public void showInitPanel(boolean show){
        if (show) {
            initPanel.setVisible(true);
            simulatorPanel.setVisible(false);
        }
        else {
            initPanel.setVisible(false);
            simulatorPanel.setVisible(true);
        }
    }
}