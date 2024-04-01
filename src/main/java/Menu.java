import Entities.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Menu {
    private BufferedReader reader;
    private FootballController controller;
    private Scanner scanner;

    public Menu() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        controller = new FootballController();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n--- Football---");
            System.out.println("-Listar-");
            System.out.println("1. Listar Teams");
            System.out.println("2. Listar Matches");
            System.out.println("3. Listar Players");
            System.out.println("4. Listar Plays");
            System.out.println("-Agregar-");
            System.out.println("5. Agregar Match");
            System.out.println("6. Agregar Team");
            System.out.println("7. Agregar Players");
            System.out.println("8. Agregar Plays");
            System.out.println("-Borrar-");
            System.out.println("9. Borrar Match");
            System.out.println("10. Borrar Team");
            System.out.println("11. Borrar Player");
            System.out.println("12. Borrar Plays");
            System.out.println("-Cargar-");
            System.out.println("13. Cargar Teams");
            System.out.println("14. Cargar Players");
            System.out.println("15. Cargar Matches");
            System.out.println("16. Cargar Plays");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        controller.listTeams();
                        break;
                    case 2:
                        controller.listMatches();
                        break;
                    case 3:
                        controller.listPlayers();
                        break;
                    case 4:
                        controller.listPlays();
                        break;
                    case 5:
                        addMatch();
                        break;
                    case 6:
                        addTeam();
                        break;
                    case 7:
                        addPlayer();
                        break;
                    case 8:
                        addPlays();
                        break;
                    case 9:
                        deleteMatch();
                        break;
                    case 10:
                        deleteTeam();
                        break;
                    case 11:
                        deletePlayer();
                        break;
                    case 12:
                        deletePlays();
                        break;
                    case 13:
                        loadTeamsFromFile();
                        break;
                    case 14:
                        loadPlayersFromFile();
                        break;
                    case 15:
                        loadMatchesFromFile();
                        break;
                    case 16:
                        loadPlaysFromFile();
                        break;
                    case 0:
                        System.out.println("Saliendo");
                        break;
                    default:
                        System.out.println("Error");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
    }

    private void addMatch() {
        System.out.println("\n--- Agregar Match ---");
        System.out.print("Match ID: ");
        int match_id = scanner.nextInt();
        System.out.print("Pon la fecha (YYYY-MM-DD): ");
        String dateString = scanner.next();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            System.out.print("Pon el id del Team home: ");
            int homeTeamCode = scanner.nextInt();
            System.out.print("Pon el id del team away: ");
            int awayTeamCode = scanner.nextInt();

            if (controller.isTeamValid(homeTeamCode) && controller.isTeamValid(awayTeamCode)) {
                controller.addMatch(match_id, date, homeTeamCode, awayTeamCode);
            } else {
                System.out.println("uno de los dos id estan mal.");
            }
        } catch (ParseException e) {
            System.out.println("Error");
        }
    }


    private void deleteMatch() throws IOException {
        System.out.println("\n--- Borrar Match ---");
        System.out.print("Pon el id del match que quieres borrar: ");
        int matchId;
        try {
            matchId = Integer.parseInt(reader.readLine());
            controller.deleteMatch(matchId);
            System.out.println("Match borrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error.");
        }
    }

    private void loadTeamsFromFile() throws IOException {
        String filePath = "teams.txt";
        controller.loadTeamsFromFile(filePath);
    }

    private void loadPlayersFromFile() throws IOException {
        String filePath = "players.txt";
        controller.loadPlayersFromFile(filePath);
    }
    private void loadMatchesFromFile() throws IOException {
        String filePath = "matches.txt";
        controller.loadMatchesFromFile(filePath);
    }
    private void loadPlaysFromFile() throws IOException {
        String filePath = "plays.txt";
        controller.loadPlaysFromFile(filePath);
    }

    private void addTeam() throws IOException {
        System.out.println("\n--- Agregar Team ---");
        System.out.print("Pon el team code: ");
        int code = Integer.parseInt(reader.readLine());
        System.out.print("Pon el team name: ");
        String name = reader.readLine();
        System.out.print("Pon el stadium: ");
        String stadium = reader.readLine();
        System.out.print("Pon la city: ");
        String city = reader.readLine();
        controller.addTeam(code, name, stadium, city);
    }

    private void deleteTeam() throws IOException {
        System.out.println("\n--- Borrar Team ---");
        System.out.print("Pon el codigo del Team que quieres borrar: ");
        int teamCode = Integer.parseInt(reader.readLine());
        controller.deleteTeam(teamCode);
    }

    private void addPlayer() throws IOException {
        System.out.println("\n--- Agregar Player ---");
        System.out.print("Pon el player ID: ");
        int player_id = Integer.parseInt(reader.readLine());
        System.out.print("Pon el player surname: ");
        String surname = reader.readLine();
        System.out.print("Pon el player forename: ");
        String forename = reader.readLine();
        controller.addPlayer(player_id, surname, forename);
    }

    private void deletePlayer() throws IOException {
        System.out.println("\n--- Borrar Player ---");
        System.out.print("Pon el id del player que quieres borrar: ");
        int playerId = Integer.parseInt(reader.readLine());
        controller.deletePlayer(playerId);
    }

    private void addPlays() {
        System.out.println("\n--- Agregar Plays ---");
        System.out.print("Pon el match ID: ");
        int matchId = scanner.nextInt();
        System.out.print("Pon el player ID: ");
        int playerId = scanner.nextInt();
        System.out.print("Pon el numero de starts: ");
        int starts = scanner.nextInt();
        System.out.print("Pon el numero de substituciones: ");
        int substituted = scanner.nextInt();
        System.out.print("Pon el numero de goles: ");
        int goals = scanner.nextInt();
        System.out.print("Pon el numero de las cartas amarillas: ");
        int yellow = scanner.nextInt();
        System.out.print("le pusieron Roja?? (true/false): ");
        boolean red = scanner.nextBoolean();

        controller.addPlays(matchId, playerId, starts, substituted, goals, yellow, red);
    }

    private void deletePlays() {
        System.out.println("\n--- Borrar Plays ---");
        System.out.print("Pon el match ID: ");
        int matchId = scanner.nextInt();
        System.out.print("Pon el player ID: ");
        int playerId = scanner.nextInt();
        controller.deletePlays(matchId, playerId);
    }

}
