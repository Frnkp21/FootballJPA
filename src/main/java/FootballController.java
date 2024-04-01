import Entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class FootballController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public FootballController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("football");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void listTeams() {
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Entities.Team t", Team.class);
        List<Team> teams = query.getResultList();
        if (teams.isEmpty()) {
            System.out.println("No teams.txt found.");
        } else {
            System.out.println("--- Teams ---");
            for (Team team : teams) {
                System.out.println(team);
            }
        }
    }

    public void listMatches() {
        TypedQuery<MatchInfo> query = entityManager.createQuery("SELECT m FROM Entities.MatchInfo m", MatchInfo.class);
        List<MatchInfo> matches = query.getResultList();
        if (matches.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            System.out.println("--- Matches ---");
            for (MatchInfo match : matches) {
                System.out.println(match);
            }
        }
    }


    public void deleteMatch(int match_id) {
        entityManager.getTransaction().begin();
        MatchInfo match = entityManager.find(MatchInfo.class, match_id);
        if (match == null) {
            System.out.println("Match not found.");
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.remove(match);
        entityManager.getTransaction().commit();
    }




    public boolean isTeamValid(int teamCode) {
        Team team = entityManager.find(Team.class, teamCode);
        return team != null;
    }

    public void addMatch(int match_id, Date played_date, int homeTeamCode, int awayTeamCode) {
        entityManager.getTransaction().begin();
        Team homeTeam = entityManager.find(Team.class, homeTeamCode);
        Team awayTeam = entityManager.find(Team.class, awayTeamCode);

        if (homeTeam == null || awayTeam == null) {
            System.out.println("Invalid team code.");
            entityManager.getTransaction().rollback();
            return;
        }

        MatchInfo match = new MatchInfo();
        match.setMatch_id(match_id);
        match.setPlayed_date(played_date);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        entityManager.persist(match);
        entityManager.getTransaction().commit();
        System.out.println("Match agregado correctamente.");

    }
    public void addTeam(int code, String name, String stadium, String city) {
        entityManager.getTransaction().begin();
        Team team = new Team();
        team.setCode(code);
        team.setName(name);
        team.setStadium(stadium);
        team.setCity(city);
        entityManager.persist(team);
        entityManager.getTransaction().commit();
        System.out.println("Team agregado correctamente.");
    }

    public void deleteTeam(int teamCode) {
        entityManager.getTransaction().begin();
        Team team = entityManager.find(Team.class, teamCode);
        if (team == null) {
            System.out.println("Team not found.");
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.remove(team);
        entityManager.getTransaction().commit();
        System.out.println("Team borrado correctamente.");
    }
    public void addPlayer(int player_id, String surname, String forename) {
        entityManager.getTransaction().begin();
        Player player = new Player();
        player.setPlayer_id(player_id);
        player.setSurname(surname);
        player.setForename(forename);
        entityManager.persist(player);
        entityManager.getTransaction().commit();
        System.out.println("Player agregado correctamente.");

    }

    public void addPlays(int matchId, int playerId, int starts, int substituted, int goals, int yellow, boolean red) {
        entityManager.getTransaction().begin();

        PlaysId playsId = new PlaysId();
        playsId.setMatchId(matchId);
        playsId.setPlayerId(playerId);

        Plays plays = new Plays();
        plays.setId(playsId);
        plays.setStarts(starts);
        plays.setSubstituted(substituted);
        plays.setGoals(goals);
        plays.setYellow(yellow);
        plays.setRed(red);

        entityManager.persist(plays);

        entityManager.getTransaction().commit();
    }


    public void deletePlayer(int playerId) {
        entityManager.getTransaction().begin();
        Player player = entityManager.find(Player.class, playerId);
        if (player == null) {
            System.out.println("Player not found.");
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.remove(player);
        entityManager.getTransaction().commit();
    }
    public void listPlayers() {
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
        List<Player> players = query.getResultList();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            System.out.println("--- Players ---");
            for (Player player : players) {
                System.out.println(player);
            }
        }
    }
    public void listPlays() {
        TypedQuery<Plays> query = entityManager.createQuery("SELECT p FROM Plays p", Plays.class);
        List<Plays> playsList = query.getResultList();
        if (playsList.isEmpty()) {
            System.out.println("No plays found.");
        } else {
            System.out.println("--- Plays ---");
            for (Plays plays : playsList) {
                System.out.println(plays);
            }
        }
    }

    public void deletePlays(int matchId, int playerId) {
        entityManager.getTransaction().begin();
        PlaysId playsId = new PlaysId();
        playsId.setMatchId(matchId);
        playsId.setPlayerId(playerId);
        Plays playsToDelete = entityManager.find(Plays.class, playsId);
        if (playsToDelete == null) {
            System.out.println("Plays not found.");
        } else {
            entityManager.remove(playsToDelete);
            System.out.println("Plays borrado correctamente.");
        }
        entityManager.getTransaction().commit();
    }

    public void loadMatchesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int match_id = Integer.parseInt(tokenizer.nextToken());
                String dateString = tokenizer.nextToken();
                Date played_date;
                try {
                    played_date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                } catch (ParseException e) {
                    System.err.println("Error parsing date for match with ID " + match_id + ": " + e.getMessage());
                    continue; // Skip this match and continue with the next one
                }
                int homeTeamCode = Integer.parseInt(tokenizer.nextToken());
                int awayTeamCode = Integer.parseInt(tokenizer.nextToken());

                // Check if both home and away teams exist
                Team homeTeam = entityManager.find(Team.class, homeTeamCode);
                Team awayTeam = entityManager.find(Team.class, awayTeamCode);
                if (homeTeam == null || awayTeam == null) {
                    System.out.println("Invalid team code for match with ID " + match_id + ". Skipping...");
                    continue; // Skip this match and continue with the next one
                }

                // Check if the match already exists
                MatchInfo existingMatch = entityManager.find(MatchInfo.class, match_id);
                if (existingMatch != null) {
                    System.out.println("Match with ID " + match_id + " already exists. Skipping...");
                    continue; // Skip this match and continue with the next one
                }

                // Create and persist the match
                addMatch(match_id, played_date, homeTeamCode, awayTeamCode);
            }
            System.out.println("Matches loaded successfully from file.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void loadPlaysFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int matchId = Integer.parseInt(tokenizer.nextToken());
                int playerId = Integer.parseInt(tokenizer.nextToken());
                int starts = Integer.parseInt(tokenizer.nextToken());
                int substituted = Integer.parseInt(tokenizer.nextToken());
                int goals = Integer.parseInt(tokenizer.nextToken());
                int yellow = Integer.parseInt(tokenizer.nextToken());
                boolean red = Boolean.parseBoolean(tokenizer.nextToken());

                // Verificar si la fila ya existe en la base de datos
                PlaysId playsId = new PlaysId();
                playsId.setMatchId(matchId);
                playsId.setPlayerId(playerId);
                Plays existingPlays = entityManager.find(Plays.class, playsId);
                if (existingPlays != null) {
                    // Si la fila ya existe, puedes optar por actualizarla si es necesario
                    existingPlays.setStarts(starts);
                    existingPlays.setSubstituted(substituted);
                    existingPlays.setGoals(goals);
                    existingPlays.setYellow(yellow);
                    existingPlays.setRed(red);
                } else {
                    // Si la fila no existe, la insertamos
                    Plays newPlays = new Plays();
                    newPlays.setId(playsId);
                    newPlays.setStarts(starts);
                    newPlays.setSubstituted(substituted);
                    newPlays.setGoals(goals);
                    newPlays.setYellow(yellow);
                    newPlays.setRed(red);
                    entityManager.getTransaction().begin();
                    entityManager.persist(newPlays);
                    entityManager.getTransaction().commit();
                }
            }
            System.out.println("Plays loaded successfully from file.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void loadPlayersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int player_id = Integer.parseInt(tokenizer.nextToken());
                String surname = tokenizer.nextToken();
                String forename = tokenizer.nextToken();

                Player existingPlayer = entityManager.find(Player.class, player_id);
                if (existingPlayer == null) {
                    addPlayer(player_id,surname,forename);
                } else {
                    System.out.println("Player con el code " + player_id + " ya existe.");
                }
            }
            System.out.println("Players cargados correctamente.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public void loadTeamsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int code = Integer.parseInt(tokenizer.nextToken());
                String name = tokenizer.nextToken();
                String stadium = tokenizer.nextToken();
                String city = tokenizer.nextToken();

                // Verificar si el equipo ya existe en la base de datos
                Team existingTeam = entityManager.find(Team.class, code);
                if (existingTeam == null) {
                    addTeam(code, name, stadium, city);
                } else {
                    System.out.println("Team con el code " + code + " ya existe.");
                }
            }
            System.out.println("Teams cargados correctamente.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}
