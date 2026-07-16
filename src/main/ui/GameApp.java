package ui;

import model.Player;
import model.Event;
import model.EventLibrary;
import java.util.*;

public class GameApp {
    private EventLibrary eventLibrary;
    // private Event event;
    Player p1;
    Scanner sc;

    public GameApp() {
        sc = new Scanner(System.in);
        eventLibrary = new EventLibrary();

        System.out.print("What's your name?: ");
        String name = sc.nextLine();

        System.out.print("What's your birthplace?: ");
        String location = sc.nextLine();

        p1 = new Player(name, location, 0);
    }

    public void runGame() {
        System.out.println("Are you ready to rolling your life?");

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

    public void menuCycle() {
        boolean choosing = true;
        while (choosing) {
            askingMenu();
            choosing = receivingSystem();
        }
    }

    public void askingMenu() {
        System.out.println("\nHere's the menu option");
        System.out.println("1. Next year");
        System.out.println("2. View Player Status");
        System.out.println("3. View all events");
        System.out.println("4. Exit");
        System.out.print("Choose: ");
    }

    public boolean receivingSystem() {
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
                for (String eventCode : eventLibrary.goneThroughEvents()) {
                    System.out.println(eventCode);
                }
                return true;
            case "4":
                System.out.println("Thank you for playing!");
                System.exit(0);
                return false;
            default:
                System.out.println("Invalid input");
                return true;
        }
    }

    public void roundStateDeclaration() {
        Event event = eventLibrary.spingWheelForAge(p1.getPlayerAge());
        eventLibrary.addGoneThroughEvents(event);

        if (event != null) {
            System.out.println("\nAnd you get: " + event.getEventDescription());
            p1.getConditionChanged(event);
            System.out.println("San: " + p1.getPlayerSan() + ", Mood: " + p1.getPlayerMood());
        }
    }
}
