package Parkeersimulator.Model;

import Parkeersimulator.View.AbstractView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel {
    public List<AbstractView> views;

    public AbstractModel (){
        views = new ArrayList<>();
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

    public void setVisible (String view){
        for (AbstractView v : views) {
            v.setVisible(false);
            if (v.getName().equals(view)){
                v.setVisible(true);
            }
        }
    }
}