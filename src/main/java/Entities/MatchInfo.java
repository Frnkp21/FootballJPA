package Entities;

import Entities.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MatchInfo {
    @Id
    private int match_id;
    private Date played_date;
    @ManyToOne
    @JoinColumn(name = "code_home")
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name = "code_away")
    private Team awayTeam;

    // Getters y setters

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public Date getPlayed_date() {
        return played_date;
    }

    public void setPlayed_date(Date played_date) {
        this.played_date = played_date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "match_id=" + match_id +
                ", played_date=" + played_date +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                '}';
    }
}
