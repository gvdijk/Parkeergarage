package Parkeersimulator.Model;

import Parkeersimulator.View.AbstractView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel {
    private List<AbstractView> views;
    private JPanel initPanel;
    private JPanel simulatorPanel;

    public AbstractModel (JPanel init, JPanel simulator){
        views = new ArrayList<>();
        initPanel = init;
        simulatorPanel = simulator;
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

    public void setVisibleView(String view){
        for (AbstractView v : views) {
            v.setVisible(false);
            if (v.getName().equals(view)){
                v.setVisible(true);
            }
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