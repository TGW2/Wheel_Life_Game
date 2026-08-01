package ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import model.GameLife;
import model.Player;

public class GamePanel extends JPanel {
    private int currentImage = 1;
    private final int MAX_IMAGE = 5;
    // private Player player;
    private GameLife gamelife;
    private JFrame frame;

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JLabel playerImageLabel;

    private JButton spinButton;
    private JButton viewExperiencedEventsButton;
    private JButton viewCurrentStatusButton;
    private JButton costumeButton;
    private JButton saveButton;
    private JButton loadButton;

    private JTextArea eventOutputArea;
    private JScrollPane eventScrollPane;

    public GamePanel(String name) {
        gamelife = new GameLife();
        // player = new Player(name, name, ABORT);
        initializePanel();

        initializeButtonViewCurrentStatus();
        initializeButtonCostume();
        initializePlayerImage();
        initializeButtonViewExperiencedEvents();

        initializeEventOutput();
        initializeButtonSpinWheel();
        initializeButtonSave();
        initializeButtonLoad();

        initializeFrame(name);
    }

    private void initializePanel() {
        setBackground(new Color(254, 244, 226));
        setLayout(new GridLayout(1, 2));

        leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(null);

        rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(null);

        add(leftPanel);
        add(rightPanel);
    }

    private void initializeButtonViewCurrentStatus() {
        viewCurrentStatusButton = new JButton("View Current Status");
        viewCurrentStatusButton.setFont(new Font("Arial", Font.PLAIN, 16));
        viewCurrentStatusButton.setBounds(70, 20, 220, 40);

        viewCurrentStatusButton.addActionListener(e -> {
            eventOutputArea.append("\n" + gamelife.getCurrentPlayerStatusForGui());
        });

        leftPanel.add(viewCurrentStatusButton);
    }

    private void initializeButtonCostume() {
        costumeButton = new JButton("Costume");
        costumeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        costumeButton.setBounds(70, 75, 220, 40);
        costumeButton.addActionListener(e -> {
            currentImage++;
            if (currentImage > MAX_IMAGE) {
                currentImage = 1;
            }
            updatePlayerImage();
        });
        leftPanel.add(costumeButton);
    }

    private void initializePlayerImage() {
        playerImageLabel = new JLabel();
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playerImageLabel.setBounds(40, 130, 280, 350);
        updatePlayerImage();
        leftPanel.add(playerImageLabel);
    }

    private void updatePlayerImage() {
        String path = "src/main/assest/" + currentImage + ".png";
        ImageIcon originalIcon = new ImageIcon(path);
        if (originalIcon.getIconWidth() == -1) {
            playerImageLabel.setIcon(null);
            playerImageLabel.setText("Image not found");
            return;
        }
        Image scaledImage = originalIcon.getImage()
                .getScaledInstance(276, 346, Image.SCALE_SMOOTH);
        playerImageLabel.setIcon(new ImageIcon(scaledImage));
        playerImageLabel.setText("");
    }

    private void initializeButtonViewExperiencedEvents() {
        viewExperiencedEventsButton = new JButton("View Experienced Events");
        viewExperiencedEventsButton.setFont(new Font("Arial", Font.PLAIN, 15));
        viewExperiencedEventsButton.setBounds(55, 500, 250, 40);

        viewExperiencedEventsButton.addActionListener(e -> {
            eventOutputArea.append("\n" + gamelife.getExperiencedEventsText());
        });

        leftPanel.add(viewExperiencedEventsButton);

    }

    private void initializeEventOutput() {
        eventOutputArea = new JTextArea();
        eventOutputArea.setEditable(false);
        eventOutputArea.setLineWrap(true);
        eventOutputArea.setWrapStyleWord(true);

        eventOutputArea.setFont(new Font("Arial", Font.PLAIN, 15));
        eventOutputArea.setBackground(Color.WHITE);
        eventScrollPane = new JScrollPane(eventOutputArea);
        eventScrollPane.setBounds(40, 20, 300, 250);

        eventOutputArea.append("Welcome to Life Rolling game!");

        rightPanel.add(eventScrollPane);
    }

    private void initializeButtonSpinWheel() {
        spinButton = new JButton("Spin Wheel");

        spinButton.setFont(new Font("Arial", Font.BOLD, 18));
        spinButton.setBounds(100, 295, 180, 60);
        spinButton.addActionListener(e -> {
            eventOutputArea.append("\n" + gamelife.spinOnce());
        });

        rightPanel.add(spinButton);
    }

    private void initializeButtonSave() {
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.setBounds(100, 380, 180, 45);
        rightPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                gamelife.saveGame("src/main/data/dataStorage.json");
                eventOutputArea.setText("Game saved successfully.");
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(this, "Unable to save the game.", "Save Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initializeButtonLoad() {
        loadButton = new JButton("Load");
        loadButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loadButton.setBounds(100, 445, 180, 45);
        rightPanel.add(loadButton);

        loadButton.addActionListener(e -> {
            try {
                gamelife.loadGame();
                eventOutputArea.setText("Game loaded successfully.\n\n" + gamelife.getPlayer().toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Unable to load game.", "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initializeFrame(String name) {
        frame = new JFrame(name);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 628);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public void appendEventOutput(String output) {
        eventOutputArea.append(output + "\n");
        eventOutputArea.setCaretPosition(eventOutputArea.getDocument().getLength());
    }

    public void clearEventOutput() {
        eventOutputArea.setText("");
    }

    public JButton getSpinButton() {
        return spinButton;
    }
}