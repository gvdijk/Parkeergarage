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

    public abstract void updateViews();
}
