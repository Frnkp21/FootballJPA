package Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    private int player_id;
    private String surname;
    private String forename;

    // Getters y setters

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    @Override
    public String toString() {
        return "Player ID: " + player_id +
                ", Surname: " + surname +
                ", Forename: " + forename;
    }
}