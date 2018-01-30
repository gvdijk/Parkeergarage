package Parkeersimulator.Model;

import java.awt.*;

public abstract class Reservation {
    private Location location;
    private String relevance;

    public Reservation(Location location, String relevance) {
        this.location = location;
        this.relevance = relevance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public abstract Color getColor();

    public abstract void tick();
}
