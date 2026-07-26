package ui;

import model.Player;
import persistence.JsonWriter;
import persistence.JsonReader;
import model.Event;
import model.EventLibrary;

import java.io.IOException;
import java.util.*;

// @ExcludeFromJacocoGeneratedReport
public class GameApp {
    String filePath = "src/main/data/dataStorage.json";
    EventLibrary eventLibrary = new EventLibrary();
    Player p1;
    Scanner sc;
    JsonReader jsonReader = new JsonReader(filePath);
    JsonWriter jsonWriter = new JsonWriter();

    public GameApp() {
        sc = new Scanner(System.in);
    }

    public void welcomingPlayer() {
        System.out.println("Welcome to Life Rolling game!\n" + "Please choose the following option");
        System.out.println(
                "1.Start new game"
                        + "\n2.Loading game"
                        + "\n3.Quit");
    }

    public void start() throws IOException {
        welcomingPlayer();
        boolean cycle = true;
        while (cycle) {
            System.out.print("\nChoosing: ");
            String beginningOption = sc.nextLine();
            switch (beginningOption) {
                case "1":
                    playerInitilization();
                    runGame();
                    cycle = false;
                    break;
                case "2":
                    p1 = jsonReader.loadGame(filePath, eventLibrary);
                    runGame();
                    cycle = false;
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again");
                    break;
            }
        }
    }

    public void playerInitilization() {
        sc = new Scanner(System.in);
        eventLibrary = new EventLibrary();

        System.out.print("What's your name?: ");
        String name = sc.nextLine();

        System.out.print("What's your birthplace?: ");
        String location = sc.nextLine();

        p1 = new Player(name, location, 0);
    }

    public void runGame() throws IOException {
        System.out.println("\nAre you ready to rolling your life?");

        boolean playing = true;
        while (playing) {
            System.out.println("\n" + p1.toString());

            System.out.println("Here's the options you might get:");
            ArrayList<Event> possibleEvents = eventLibrary.allPossibleEvents(p1.getPlayerAge());

            if (possibleEvents.isEmpty()) {
                System.out.println("No events at this age");
            } else {
                for (Event e : possibleEvents) {
                    System.out.println("  - " + e.getEventDescription());
                }
            }
            roundStateDeclaration();
            eventLibrary.prerequistieEventAlternation();

            eventLibrary.roundCheck(p1);
            menuCycle();
        }
        sc.close();
    }

    public void menuCycle() throws IOException {
        boolean choosing = true;
        while (choosing) {
            askingMenu();
            choosing = receivingSystem();
        }
    }

    public void askingMenu() {
        System.out.println(
                "\nHere's the menu option"
                        + "\n1. Next year"
                        + "\n2. View Player Status"
                        + "\n3. View all events"
                        + "\n4. Exit");
        System.out.print("Choose: ");
    }

    public boolean receivingSystem() throws IOException {
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                p1.addAge();
                return false;
            case "2":
                System.out.println(p1.toString());
                System.out.println("Achievement: " + p1.achievementMade());
                return true;
            case "3":
                for (String eventCode : eventLibrary.goneThroughEventsInString()) {
                    System.out.println(eventCode);
                }
                return true;
            case "4":
                savingGameMethodOption();
                return false;
            default:
                System.out.println("Invalid input");
                return true;
        }
    }

    public void savingGameMethodOption() throws IOException {
        System.out.println(
                "\nA. Back to game"
                        + "\nB. Save game"
                        + "\nC. Exit");
        boolean cycle = true;
        while (cycle) {
            String option = sc.nextLine();
            switch (option) {
                case "A":
                    runGame();
                    cycle = false;
                    break;
                case "B":
                    jsonWriter.saveGame(p1, eventLibrary, filePath);
                    cycle = false;
                case "C":
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again");
                    break;
            }
        }
    }

    
    public void roundStateDeclaration() {
        Event event = eventLibrary.spingWheelForAge(p1.getPlayerAge());
        eventLibrary.addGoneThroughEventInEvent(event);

        if (event != null) {
            System.out.println("\nAnd you get: " + event.getEventDescription());
            p1.getConditionChanged(event);
            System.out.println("San: " + p1.getPlayerSan() + ", Mood: " + p1.getPlayerMood());
        }
    }
}
