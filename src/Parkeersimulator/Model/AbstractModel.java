package Parkeersimulator.Model;

import Parkeersimulator.Main.Simulator;
import Parkeersimulator.View.AbstractView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel {
    private List<AbstractView> views;
    private JPanel initPanel;
    private JPanel simulatorPanel;
    protected Simulator simulator;

    public AbstractModel (JPanel init, JPanel sim){
        views = new ArrayList<>();
        initPanel = init;
        simulatorPanel = sim;
    }

    public void addView (AbstractView view){
        views.add(view);
    }

    public void updateViews(){
        // Update all views.
        for (AbstractView v: views){
            v.updateView();
        }
    }

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