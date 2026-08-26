package ui;

import model.Event;
import model.Event4;
import model.EventLog;
import model.GameLife;
import model.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** A warm editorial desktop presentation for Life Wheel. */
public class GamePanel extends JPanel {
    private static final Color PAPER = new Color(238, 232, 216);
    private static final Color PAPER_LIGHT = new Color(250, 247, 238);
    private static final Color INK = new Color(29, 42, 51);
    private static final Color MUTED = new Color(105, 113, 111);
    private static final Color CORAL = new Color(207, 91, 73);
    private static final Color GOLD = new Color(211, 155, 67);
    private static final Color TEAL = new Color(48, 126, 120);
    private static final Color LINE = new Color(209, 199, 179);

    private final JFrame frame;
    private final CardLayout cards = new CardLayout();
    private final Path savePath;
    private GameLife gameLife;
    private JLabel ageValue;
    private JLabel chapterValue;
    private JLabel playerValue;
    private JLabel placeValue;
    private JLabel legacyValue;
    private JLabel storyKicker;
    private JTextArea storyText;
    private JLabel impactText;
    private Meter spirit;
    private Meter joy;
    private Meter insight;
    private LifePath lifePath;
    private OrbitWheel wheel;
    private JButton turnButton;
    private Point dragOrigin;

    public GamePanel(String title) {
        savePath = Path.of(System.getProperty("user.home"), ".lifewheel", "journey.json");
        setLayout(cards);
        add(buildLanding(), "landing");
        add(buildJourney(), "journey");
        frame = new JFrame(title);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent event) {
                printLog();
                frame.dispose();
            }
        });
        frame.setMinimumSize(new Dimension(1080, 700));
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(38, 48, 54), 1));
        installWindowDrag(this);
        frame.setVisible(true);
    }

    private JPanel buildLanding() {
        JPanel landing = new PaperCanvas();
        landing.setLayout(new BorderLayout());
        landing.add(buildChrome(), BorderLayout.NORTH);
        JPanel stage = transparent(new GridBagLayout());
        JPanel content = transparent(new GridLayout(1, 2, 70, 0));
        content.setPreferredSize(new Dimension(1010, 610));
        content.add(buildLandingCopy());
        content.add(new CoverArt());
        stage.add(content);
        landing.add(stage, BorderLayout.CENTER);
        return landing;
    }

    private JPanel buildChrome() {
        JPanel chrome = transparent(new BorderLayout());
        chrome.setBorder(new EmptyBorder(10, 16, 8, 12));
        chrome.add(label("●", 8, Font.PLAIN, CORAL), BorderLayout.WEST);
        JPanel controls = transparent(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        JButton minimize = chromeButton("—");
        minimize.addActionListener(event -> frame.setState(Frame.ICONIFIED));
        JButton close = chromeButton("×");
        close.addActionListener(event -> frame.dispatchEvent(
                new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING)));
        controls.add(minimize);
        controls.add(close);
        chrome.add(controls, BorderLayout.EAST);
        return chrome;
    }

    private JPanel buildLandingCopy() {
        JPanel copy = transparent();
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        copy.setBorder(new EmptyBorder(52, 10, 45, 10));
        copy.add(label("A LIFE IN NINETY TURNS", 12, Font.BOLD, CORAL));
        copy.add(Box.createVerticalStrut(28));
        copy.add(label("LIFE", 70, Font.PLAIN, INK, "Serif"));
        JLabel wheelWord = label("WHEEL", 70, Font.ITALIC, INK, "Serif");
        wheelWord.setBorder(new EmptyBorder(-16, 0, 0, 0));
        copy.add(wheelWord);
        copy.add(Box.createVerticalStrut(28));
        JTextArea introduction = area("A small game about all the things that make a life: "
                + "the ordinary years, the sharp turns, and the people we carry with us.", 18, MUTED);
        introduction.setMaximumSize(new Dimension(455, 115));
        copy.add(introduction);
        copy.add(Box.createVerticalGlue());
        JButton begin = primaryButton("Begin a new life  →");
        begin.addActionListener(event -> startNewGame());
        copy.add(begin);
        copy.add(Box.createVerticalStrut(12));
        JButton resume = textButton("Continue the last story");
        resume.setEnabled(Files.exists(savePath));
        resume.addActionListener(event -> loadGame());
        copy.add(resume);
        copy.add(Box.createVerticalStrut(18));
        JButton guide = textButton("How it works");
        guide.addActionListener(event -> showGuide());
        copy.add(guide);
        return copy;
    }

    private JPanel buildJourney() {
        JPanel journey = new PaperCanvas();
        journey.setLayout(new BorderLayout());
        journey.setBorder(new EmptyBorder(24, 34, 24, 34));
        journey.add(buildNavigation(), BorderLayout.NORTH);
        journey.add(buildJourneyBody(), BorderLayout.CENTER);
        lifePath = new LifePath();
        journey.add(lifePath, BorderLayout.SOUTH);
        return journey;
    }

    private JPanel buildNavigation() {
        JPanel navigation = transparent(new BorderLayout());
        navigation.setBorder(new EmptyBorder(0, 0, 20, 0));
        navigation.add(label("LIFE WHEEL", 16, Font.BOLD, INK, "Serif"), BorderLayout.WEST);
        JPanel actions = transparent(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton journal = textButton("Journal");
        journal.addActionListener(event -> showJournal());
        JButton save = textButton("Save");
        save.addActionListener(event -> saveGame());
        JButton menu = textButton("Main menu");
        menu.addActionListener(event -> cards.show(this, "landing"));
        actions.add(journal);
        actions.add(save);
        actions.add(menu);
        JButton minimize = chromeButton("—");
        minimize.addActionListener(event -> frame.setState(Frame.ICONIFIED));
        JButton close = chromeButton("×");
        close.addActionListener(event -> frame.dispatchEvent(
                new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING)));
        actions.add(minimize);
        actions.add(close);
        navigation.add(actions, BorderLayout.EAST);
        return navigation;
    }

    private JPanel buildJourneyBody() {
        JPanel body = transparent(new BorderLayout(32, 0));
        body.setBorder(new EmptyBorder(5, 0, 22, 0));
        body.add(buildIdentity(), BorderLayout.WEST);
        body.add(buildStory(), BorderLayout.CENTER);
        body.add(buildWheel(), BorderLayout.EAST);
        return body;
    }

    private JPanel buildIdentity() {
        JPanel identity = transparent();
        identity.setPreferredSize(new Dimension(225, 520));
        identity.setLayout(new BoxLayout(identity, BoxLayout.Y_AXIS));
        ageValue = label("00", 78, Font.PLAIN, INK, "Serif");
        chapterValue = label("FIRST LIGHT", 11, Font.BOLD, CORAL);
        playerValue = label("Wanderer", 22, Font.PLAIN, INK, "Serif");
        placeValue = label("Somewhere", 13, Font.PLAIN, MUTED);
        identity.add(label("AGE", 11, Font.BOLD, MUTED));
        identity.add(ageValue);
        identity.add(chapterValue);
        identity.add(Box.createVerticalStrut(36));
        identity.add(playerValue);
        identity.add(Box.createVerticalStrut(4));
        identity.add(placeValue);
        identity.add(Box.createVerticalStrut(42));
        spirit = new Meter("SPIRIT", TEAL);
        joy = new Meter("JOY", GOLD);
        insight = new Meter("INSIGHT", CORAL);
        identity.add(spirit);
        identity.add(Box.createVerticalStrut(17));
        identity.add(joy);
        identity.add(Box.createVerticalStrut(17));
        identity.add(insight);
        identity.add(Box.createVerticalGlue());
        legacyValue = label("LEGACY 000", 12, Font.BOLD, MUTED);
        identity.add(legacyValue);
        return identity;
    }

    private JPanel buildStory() {
        JPanel story = new StorySheet();
        story.setLayout(new BorderLayout(0, 24));
        story.setBorder(new EmptyBorder(42, 48, 36, 48));
        storyKicker = label("PROLOGUE", 11, Font.BOLD, CORAL);
        storyText = area("A first breath.\n\nEverything is still possible.", 29, INK);
        storyText.setFont(new Font("Serif", Font.PLAIN, 29));
        impactText = label("TURN THE WHEEL TO BEGIN", 11, Font.BOLD, MUTED);
        story.add(storyKicker, BorderLayout.NORTH);
        story.add(storyText, BorderLayout.CENTER);
        story.add(impactText, BorderLayout.SOUTH);
        return story;
    }

    private JPanel buildWheel() {
        JPanel wheelPanel = transparent();
        wheelPanel.setPreferredSize(new Dimension(280, 520));
        wheelPanel.setLayout(new BoxLayout(wheelPanel, BoxLayout.Y_AXIS));
        wheelPanel.add(Box.createVerticalGlue());
        wheel = new OrbitWheel();
        wheel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wheelPanel.add(wheel);
        wheelPanel.add(Box.createVerticalStrut(28));
        turnButton = primaryButton("Turn the year");
        turnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        turnButton.addActionListener(this::turnYear);
        wheelPanel.add(turnButton);
        wheelPanel.add(Box.createVerticalStrut(12));
        JLabel shortcut = label("or press SPACE", 10, Font.BOLD, MUTED);
        shortcut.setAlignmentX(Component.CENTER_ALIGNMENT);
        wheelPanel.add(shortcut);
        wheelPanel.add(Box.createVerticalGlue());
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "turn");
        getActionMap().put("turn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                turnYear(event);
            }
        });
        return wheelPanel;
    }

    private void startNewGame() {
        JTextField name = styledField("Wanderer");
        JTextField origin = styledField("Vancouver");
        JPanel form = transparent(new GridLayout(0, 1, 7, 7));
        form.add(label("WHAT NAME WILL THIS LIFE CARRY?", 10, Font.BOLD, MUTED));
        form.add(name);
        form.add(Box.createVerticalStrut(5));
        form.add(label("WHERE DOES THE STORY BEGIN?", 10, Font.BOLD, MUTED));
        form.add(origin);
        int answer = showModal("A new life", form, "Begin", "Not yet");
        if (answer != 0) {
            return;
        }
        String safeName = name.getText().trim().isEmpty() ? "Wanderer" : name.getText().trim();
        String safeOrigin = origin.getText().trim().isEmpty() ? "Somewhere" : origin.getText().trim();
        gameLife = new GameLife(safeName, safeOrigin, 0);
        storyKicker.setText("PROLOGUE");
        storyText.setText("A first breath beneath the sky of " + safeOrigin
                + ".\n\nEverything is still possible.");
        impactText.setText("TURN THE WHEEL TO BEGIN");
        cards.show(this, "journey");
        refresh();
    }

    private void turnYear(ActionEvent ignored) {
        if (gameLife == null || wheel.isSpinning()) {
            return;
        }
        if (gameLife.isGameOver()) {
            showEnding();
            return;
        }
        turnButton.setEnabled(false);
        storyKicker.setText("THE YEAR IS TURNING");
        wheel.animate(() -> {
            gameLife.spinOnce();
            Event event = gameLife.getLastEvent();
            int eventAge = gameLife.getPlayer().getPlayerAge() - 1;
            if (event == null) {
                storyKicker.setText("A QUIET YEAR · " + eventAge);
                storyText.setText("Nothing announces itself. Still, the year leaves a mark.");
                impactText.setText("NO GREAT CHANGE");
            } else {
                storyKicker.setText(event.getCategory() + " · AGE " + eventAge);
                storyText.setText(event.getEventDescription());
                impactText.setText(event.getImpactSummary());
            }
            refresh();
            turnButton.setEnabled(!gameLife.isGameOver());
            if (gameLife.isGameOver()) {
                showEnding();
            }
        });
    }

    private void refresh() {
        if (gameLife == null) {
            return;
        }
        Player player = gameLife.getPlayer();
        ageValue.setText(String.format("%02d", player.getPlayerAge()));
        chapterValue.setText(gameLife.getLifeChapter());
        playerValue.setText(player.getPlayerName());
        placeValue.setText("born in " + player.getLocation());
        legacyValue.setText("LEGACY  " + gameLife.getLegacyScore());
        spirit.setValue(player.getPlayerSan());
        joy.setValue(player.getPlayerMood());
        insight.setValue(player.getWisdom());
        lifePath.setAge(player.getPlayerAge());
        repaint();
    }

    private void saveGame() {
        if (gameLife == null) {
            return;
        }
        try {
            Files.createDirectories(savePath.getParent());
            gameLife.saveGame(savePath.toString());
            String before = storyKicker.getText();
            storyKicker.setText("STORY SAVED");
            Timer timer = new Timer(1300, event -> storyKicker.setText(before));
            timer.setRepeats(false);
            timer.start();
        } catch (IOException exception) {
            showError("The story could not be saved.");
        }
    }

    private void loadGame() {
        try {
            gameLife = new GameLife();
            gameLife.loadGame(savePath.toString());
            cards.show(this, "journey");
            storyKicker.setText("WELCOME BACK");
            storyText.setText("The story waits exactly where you left it.");
            impactText.setText(gameLife.getLifeChapter());
            turnButton.setEnabled(!gameLife.isGameOver());
            refresh();
        } catch (IOException | RuntimeException exception) {
            showError("The saved story is missing or damaged.");
        }
    }

    private void showJournal() {
        if (gameLife == null) {
            return;
        }
        JTextArea journal = area(gameLife.getExperiencedEventsText(), 16, INK);
        journal.setBackground(PAPER_LIGHT);
        journal.setBorder(new EmptyBorder(22, 24, 22, 24));
        JScrollPane scroll = new JScrollPane(journal);
        scroll.setBorder(BorderFactory.createLineBorder(LINE));
        scroll.setPreferredSize(new Dimension(650, 500));
        showModal(gameLife.getPlayer().getPlayerName() + " — a life in fragments", scroll, "Close");
    }

    private void showEnding() {
        turnButton.setEnabled(false);
        storyKicker.setText("EPILOGUE");
        storyText.setText(gameLife.getEndingTitle() + "\n\n" + gameLife.getEndingText());
        impactText.setText("LEGACY " + gameLife.getLegacyScore());
        JTextArea ending = area(gameLife.getEndingText(), 17, INK);
        ending.setPreferredSize(new Dimension(520, 180));
        int choice = showModal(gameLife.getEndingTitle(), ending, "Keep this story", "Begin again");
        if (choice == 0) {
            saveGame();
        } else if (choice == 1) {
            startNewGame();
        }
    }

    private void showGuide() {
        JTextArea guide = area("Turn once to advance one year.\n\n"
                + "Spirit, Joy and Insight respond to each memory. Earlier moments unlock later ones.\n"
                + "A life ends at age 90, or when Spirit or Joy reaches zero.\n\n"
                + "There is no best ending—only the life you happened to live.", 16, INK);
        guide.setPreferredSize(new Dimension(520, 190));
        showModal("How it works", guide, "I understand");
    }

    private JPanel transparent() {
        return transparent(new FlowLayout());
    }

    private JPanel transparent(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    private JLabel label(String text, int size, int style, Color color) {
        return label(text, size, style, color, "SansSerif");
    }

    private JLabel label(String text, int size, int style, Color color, String family) {
        JLabel result = new JLabel(text);
        result.setFont(new Font(family, style, size));
        result.setForeground(color);
        return result;
    }

    private JTextArea area(String text, int size, Color color) {
        JTextArea result = new JTextArea(text);
        result.setEditable(false);
        result.setFocusable(false);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        result.setOpaque(false);
        result.setFont(new Font("SansSerif", Font.PLAIN, size));
        result.setForeground(color);
        return result;
    }

    private JButton primaryButton(String text) {
        JButton result = new RoundedButton(text);
        result.setFont(new Font("SansSerif", Font.BOLD, 14));
        result.setForeground(Color.WHITE);
        result.setPreferredSize(new Dimension(245, 50));
        result.setMaximumSize(new Dimension(245, 50));
        result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return result;
    }

    private JButton textButton(String text) {
        JButton result = new JButton(text);
        result.setFont(new Font("SansSerif", Font.BOLD, 12));
        result.setForeground(INK);
        result.setContentAreaFilled(false);
        result.setFocusPainted(false);
        result.setBorder(new EmptyBorder(9, 10, 9, 10));
        result.setHorizontalAlignment(SwingConstants.LEFT);
        result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return result;
    }

    private JButton chromeButton(String text) {
        JButton result = textButton(text);
        result.setFont(new Font("SansSerif", Font.PLAIN, 17));
        result.setPreferredSize(new Dimension(32, 28));
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setBorder(new EmptyBorder(1, 4, 1, 4));
        return result;
    }

    private JTextField styledField(String value) {
        JTextField result = new JTextField(value);
        result.setFont(new Font("SansSerif", Font.PLAIN, 16));
        result.setForeground(INK);
        result.setBackground(PAPER_LIGHT);
        result.setCaretColor(CORAL);
        result.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(LINE),
                new EmptyBorder(11, 12, 11, 12)));
        return result;
    }

    private int showModal(String title, Component content, String... actions) {
        JDialog dialog = new JDialog(frame, true);
        dialog.setUndecorated(true);
        JPanel shell = new StorySheet();
        shell.setLayout(new BorderLayout(0, 22));
        shell.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(INK),
                new EmptyBorder(26, 30, 24, 30)));
        shell.add(label(title, 26, Font.PLAIN, INK, "Serif"), BorderLayout.NORTH);
        shell.add(content, BorderLayout.CENTER);
        JPanel buttons = transparent(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        int[] selected = {-1};
        for (int i = actions.length - 1; i >= 0; i--) {
            int index = i;
            JButton action = i == 0 ? primaryButton(actions[i]) : textButton(actions[i]);
            if (i == 0) {
                action.setPreferredSize(new Dimension(170, 44));
                action.setMaximumSize(new Dimension(170, 44));
            }
            action.addActionListener(event -> {
                selected[0] = index;
                dialog.dispose();
            });
            buttons.add(action);
        }
        shell.add(buttons, BorderLayout.SOUTH);
        dialog.setContentPane(shell);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(520, 240));
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        return selected[0];
    }

    private void installWindowDrag(Component component) {
        MouseAdapter drag = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                dragOrigin = event.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                if (dragOrigin != null && frame != null) {
                    Point screen = event.getLocationOnScreen();
                    frame.setLocation(screen.x - dragOrigin.x, screen.y - dragOrigin.y);
                }
            }
        };
        component.addMouseListener(drag);
        component.addMouseMotionListener(drag);
    }

    private void showError(String message) {
        JTextArea copy = area(message, 16, INK);
        copy.setPreferredSize(new Dimension(430, 70));
        showModal("Something went wrong", copy, "Close");
    }

    private void printLog() {
        for (Event4 event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    public JButton getSpinButton() {
        return turnButton;
    }

    public void appendEventOutput(String output) {
        if (storyText != null) {
            storyText.append("\n" + output);
        }
    }

    public void clearEventOutput() {
        if (storyText != null) {
            storyText.setText("");
        }
    }

    private class PaperCanvas extends JPanel {
        PaperCanvas() {
            setBackground(PAPER);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setColor(new Color(255, 255, 255, 35));
            for (int y = 0; y < getHeight(); y += 9) {
                g.drawLine(0, y, getWidth(), y);
            }
            g.setColor(new Color(196, 83, 67, 18));
            g.fillOval(-180, getHeight() - 320, 430, 430);
            g.dispose();
        }
    }

    private class CoverArt extends JPanel {
        CoverArt() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = quality(graphics);
            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            g.setStroke(new BasicStroke(1.2f));
            for (int i = 0; i < 7; i++) {
                int radius = 72 + i * 28;
                g.setColor(i % 2 == 0 ? new Color(207, 91, 73, 130) : new Color(48, 126, 120, 100));
                g.draw(new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));
            }
            g.setColor(INK);
            g.fillOval(cx - 11, cy - 11, 22, 22);
            String[] words = {"FIRST LIGHT", "BECOMING", "MAKING A MARK", "DEEP ROOTS", "GOLDEN HOUR"};
            g.setFont(new Font("SansSerif", Font.BOLD, 10));
            for (int i = 0; i < words.length; i++) {
                double angle = -1.45 + i * 1.15;
                int radius = 105 + i * 22;
                int x = cx + (int) (Math.cos(angle) * radius);
                int y = cy + (int) (Math.sin(angle) * radius);
                g.setColor(i % 2 == 0 ? CORAL : TEAL);
                g.fillOval(x - 5, y - 5, 10, 10);
                g.drawString(words[i], x + 10, y + 4);
            }
            g.dispose();
        }
    }

    private class StorySheet extends JPanel {
        StorySheet() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = quality(graphics);
            g.setColor(new Color(91, 72, 53, 18));
            g.fillRoundRect(7, 10, getWidth() - 8, getHeight() - 9, 8, 8);
            g.setColor(PAPER_LIGHT);
            g.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 9, 8, 8);
            g.setColor(LINE);
            g.drawRoundRect(0, 0, getWidth() - 8, getHeight() - 9, 8, 8);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private class RoundedButton extends JButton {
        RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = quality(graphics);
            Color fill = isEnabled() ? CORAL : new Color(175, 166, 150);
            if (getModel().isRollover() && isEnabled()) {
                fill = fill.darker();
            }
            g.setColor(fill);
            g.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private class Meter extends JPanel {
        private final String name;
        private final Color color;
        private int value;

        Meter(String name, Color color) {
            this.name = name;
            this.color = color;
            setOpaque(false);
            setMaximumSize(new Dimension(225, 34));
            setPreferredSize(new Dimension(225, 34));
        }

        void setValue(int newValue) {
            value = newValue;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = quality(graphics);
            g.setFont(new Font("SansSerif", Font.BOLD, 10));
            g.setColor(MUTED);
            g.drawString(name, 0, 10);
            String amount = Integer.toString(value);
            g.drawString(amount, getWidth() - g.getFontMetrics().stringWidth(amount), 10);
            g.setColor(LINE);
            g.fillRoundRect(0, 22, getWidth(), 4, 4, 4);
            g.setColor(color);
            g.fillRoundRect(0, 22, (int) (getWidth() * value / 100.0), 4, 4, 4);
            g.dispose();
        }
    }

    private class LifePath extends JPanel {
        private int age;

        LifePath() {
            setOpaque(false);
            setPreferredSize(new Dimension(800, 52));
        }

        void setAge(int newAge) {
            age = newAge;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = quality(graphics);
            int left = 12;
            int right = getWidth() - 12;
            int y = 20;
            g.setStroke(new BasicStroke(2));
            g.setColor(LINE);
            g.drawLine(left, y, right, y);
            int progress = left + (int) ((right - left) * Math.min(90, age) / 90.0);
            g.setColor(CORAL);
            g.drawLine(left, y, progress, y);
            int[] years = {0, 18, 45, 68, 90};
            g.setFont(new Font("SansSerif", Font.BOLD, 9));
            for (int year : years) {
                int x = left + (int) ((right - left) * year / 90.0);
                g.setColor(year <= age ? CORAL : LINE);
                g.fillOval(x - 4, y - 4, 8, 8);
                g.setColor(MUTED);
                g.drawString(Integer.toString(year), x - 5, y + 23);
            }
            g.dispose();
        }
    }

    private class OrbitWheel extends JPanel {
        private double rotation;
        private boolean spinning;

        OrbitWheel() {
            setOpaque(false);
            setPreferredSize(new Dimension(260, 275));
            setMaximumSize(new Dimension(260, 275));
        }

        boolean isSpinning() {
            return spinning;
        }

        void animate(Runnable finish) {
            spinning = true;
            int[] tick = {0};
            Timer timer = new Timer(16, null);
            timer.addActionListener(event -> {
                tick[0]++;
                rotation += Math.max(0.025, 0.22 * (1.0 - tick[0] / 88.0));
                repaint();
                if (tick[0] >= 88) {
                    timer.stop();
                    spinning = false;
                    finish.run();
                }
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = quality(graphics);
            int size = Math.min(getWidth(), getHeight()) - 42;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            g.setStroke(new BasicStroke(2));
            Color[] colors = {CORAL, GOLD, TEAL, INK};
            for (int i = 0; i < 12; i++) {
                g.setColor(new Color(colors[i % colors.length].getRed(), colors[i % colors.length].getGreen(),
                        colors[i % colors.length].getBlue(), 185));
                g.draw(new Arc2D.Double(x, y, size, size, Math.toDegrees(rotation) + i * 30 + 2, 24, Arc2D.OPEN));
            }
            g.setColor(LINE);
            g.drawOval(x + 21, y + 21, size - 42, size - 42);
            g.drawOval(x + 52, y + 52, size - 104, size - 104);
            Path2D needle = new Path2D.Double();
            needle.moveTo(getWidth() / 2.0, y + 16);
            needle.lineTo(getWidth() / 2.0 - 8, y - 3);
            needle.lineTo(getWidth() / 2.0 + 8, y - 3);
            needle.closePath();
            g.setColor(CORAL);
            g.fill(needle);
            g.setColor(INK);
            g.fillOval(getWidth() / 2 - 6, getHeight() / 2 - 6, 12, 12);
            g.dispose();
        }
    }

    private Graphics2D quality(Graphics graphics) {
        Graphics2D copy = (Graphics2D) graphics.create();
        copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        copy.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return copy;
    }
}
