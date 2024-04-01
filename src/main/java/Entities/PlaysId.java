package Entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlaysId implements Serializable {
    private int matchId;
    private int playerId;

    // Getters y setters

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}