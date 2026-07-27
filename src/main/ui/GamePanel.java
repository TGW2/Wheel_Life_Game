package ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

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
        viewCurrentStatusButton.setBounds(70,20,220,40);
        leftPanel.add(viewCurrentStatusButton);
    }

    private void initializeButtonCostume() {
        costumeButton = new JButton("Costume");
        costumeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        costumeButton.setBounds(70,75,220,40);
        leftPanel.add(costumeButton);
    }

    private void initializePlayerImage() {
        playerImageLabel = new JLabel();

        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playerImageLabel.setBounds(40,130,280,350);

        ImageIcon originalIcon = new ImageIcon("src/main/assest/test1.jpeg");

        if (originalIcon.getIconWidth() == -1) {
            playerImageLabel.setText("Image not found");
            System.out.println("Cannot find image: src/main/assest/test1.jpeg");
        } else {
            Image scaledImage = originalIcon.getImage().getScaledInstance(276,346,Image.SCALE_SMOOTH);
            playerImageLabel.setIcon(new ImageIcon(scaledImage));
        }

        leftPanel.add(playerImageLabel);
    }

    private void initializeButtonViewExperiencedEvents() {
        viewExperiencedEventsButton = new JButton("View Experienced Events");
        viewExperiencedEventsButton.setFont(new Font("Arial", Font.PLAIN, 15));
        viewExperiencedEventsButton.setBounds(55,500,250,40);
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
        eventScrollPane.setBounds(40,20,300,250);

        rightPanel.add(eventScrollPane);
    }

    private void initializeButtonSpinWheel() {
        spinButton = new JButton("Spin Wheel");

        spinButton.setFont(new Font("Arial", Font.BOLD, 18));
        spinButton.setBounds(100,295,180,60);
        spinButton.addActionListener(e -> {
            eventOutputArea.append("Spin Wheel button clicked\n");
            eventOutputArea.setCaretPosition(eventOutputArea.getDocument().getLength());
            spinButton.setText("Spinning...");
        });

        rightPanel.add(spinButton);
    }

    private void initializeButtonSave() {
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.setBounds(100,380,180,45);
        rightPanel.add(saveButton);
    }

    private void initializeButtonLoad() {
        loadButton = new JButton("Load");
        loadButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loadButton.setBounds(100,445,180,45);
        rightPanel.add(loadButton);
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