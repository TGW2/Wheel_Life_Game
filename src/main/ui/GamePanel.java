package ui;

import javax.swing.*;

import org.w3c.dom.css.RGBColor;

import model.Player;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class GamePanel extends JPanel {
    JFrame frame;

    public GamePanel(String name) {
        setBackground(new Color(254, 244, 226));
        // settings on window(structure)
        // create window with title(but still empty running this program)
        frame = new JFrame(name);
        frame.setSize(800, 628);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// giving exit button
        frame.setLocationRelativeTo(null);// centered
        frame.setVisible(true);

        frame.add(this);// adding this gamepanel object to jpanel
        frame.setVisible(true);// to make gamepanel's canvas visible

        System.out.println("Frame size: " + frame.getSize());
        System.out.println("Panel size: " + this.getSize());
    }

    protected void paintComponent(Graphics g) {
    super.paintComponent(g);// clear canvas

    // Wheel place(525,275)
    g.setColor(Color.black);g.drawArc(350,50,350,350,0,360);// 550,200

    // interac rectangle
    g.drawRect(400,420,250,70);

    // charact location
    g.drawRect(100,100,150,300);

    g.drawImage(Player.test1,100,100,150,300,this);
}

}
