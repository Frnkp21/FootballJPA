package Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Team {
    @Id
    private int code;
    private String name;
    private String stadium;
    private String city;

    // Getters y setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public String toString() {
        return "Team{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", stadium='" + stadium + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}