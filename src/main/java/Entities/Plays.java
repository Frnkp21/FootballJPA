package Entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Plays {
    @EmbeddedId
    private PlaysId id;
    private int starts;
    private int substituted;
    private int goals;
    private int yellow;
    private boolean red;

    // Getters y setters

    public PlaysId getId() {
        return id;
    }

    public void setId(PlaysId id) {
        this.id = id;
    }

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public int getSubstituted() {
        return substituted;
    }

    public void setSubstituted(int substituted) {
        this.substituted = substituted;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }
    @Override
    public String toString() {
        return "Plays{" +
                "id=" + id +
                ", starts=" + starts +
                ", substituted=" + substituted +
                ", goals=" + goals +
                ", yellow=" + yellow +
                ", red=" + red +
                '}';
    }

}