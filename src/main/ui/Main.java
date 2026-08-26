package ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// import javax.swing.*;
// import java.util.*;

// import model.Player;
/**
 * Main.
 */
// @ExcludeFromJacocoGeneratedReport
public class Main {
    /**
     * Main running program, initialize player sttributes and events given to
     * players.
     */
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Life Wheel");
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // The custom game UI remains usable with Swing's fallback look and feel.
            }
            new GamePanel("Life Wheel");
        });
    }
}
