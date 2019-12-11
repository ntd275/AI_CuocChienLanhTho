/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author ntd27
 */

import ai.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import map.MapGui;
import player.*;

public class Board extends JPanel implements KeyListener, ActionListener
{
    public static final int HEIGHT = 720;
    public static final int WIDTH = 700;
    private final int offsetx = 20;
    private final int offsety = 20;
    MapGui map;
    RedPlayer red;
    GreenPlayer green;
    Timer timer;
    int x;
    int y;
    AI ai;
    boolean finished;
    
    public Board() throws FileNotFoundException {
        this.x = 0;
        this.y = 0;
        this.finished = false;
        this.setFocusable(true);
        this.addKeyListener(this);
        this.map = new MapGui("/lib/map.txt");
        this.red = new RedPlayer(MapGui.WIDTH-1, MapGui.WIDTH-1, this.map);
        this.green = new GreenPlayer(0, 0, this.map);
        this.map.setGreen(0, 0);
        this.map.setRed(MapGui.WIDTH-1, MapGui.WIDTH-1);
        (this.timer = new Timer(27, this)).start();
        if (new Random().nextInt(2) == 1) {
            this.setFirstTurn(this.red);
        }
        else {
            this.setFirstTurn(this.green);
        }
    }
    
    public Board(final MapGui m) {
        this.x = 0;
        this.y = 0;
        this.finished = false;
        this.setFocusable(true);
        this.addKeyListener(this);
        this.map = m;
        this.red = new RedPlayer(MapGui.WIDTH-1, MapGui.WIDTH-1, this.map);
        this.green = new GreenPlayer(0, 0, this.map);
        this.map.setGreen(0, 0);
        this.map.setRed(MapGui.WIDTH-1, MapGui.WIDTH-1);
        (this.timer = new Timer(27, this)).start();
        if (new Random().nextInt(2) == 1) {
            this.setFirstTurn(this.green);
        }
        else {
            this.setFirstTurn(this.red);
        }
    }
    
    private void setFirstTurn(final Player p) {
        p.setTurn(true);
    }
    
    private void changeTurn() {
        if (this.green.getTurn()) {
            this.green.setTurn(false);
            this.red.setTurn(true);
        }
        else {
            this.green.setTurn(true);
            this.red.setTurn(false);
        }
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.map.getImageMap(), 0, 0, this);
        for (int x = 0; x < MapGui.WIDTH; ++x) {
            for (int y = 0; y < MapGui.WIDTH; ++y) {
                if (!this.map.isSpace(x, y)) {
                    g.drawImage(this.map.getImage(x, y), x * 60 + 20, y * 60 + 20, this);
                }
            }
        }
        g.drawImage(this.red.getImage(), this.red.xp + 20, this.red.yp + 20, this);
        g.drawImage(this.green.getImage(), this.green.xp + 20, this.green.yp + 20, this);
        if (this.finished) {
            if (this.red.goable(this.map)) {
                g.drawImage(new ImageIcon(this.getClass().getResource("/lib/rw.png")).getImage(), 0, 0, this);
            }
            else {
                g.drawImage(new ImageIcon(this.getClass().getResource("/lib/gw.png")).getImage(), 0, 0, this);
            }
        }
        this.repaint();
    }
    
    @Override
    public void keyTyped(final KeyEvent ke) {
    }
    
    @Override
    public void keyPressed(final KeyEvent ke) {
    }
    
    @Override
    public void keyReleased(final KeyEvent ke) {
        if (this.green.getTurn() && !this.finished) {
            final int k = ke.getKeyCode();
            int dir = -1;
            if (k == 38) {
                dir = 2;
            }
            else if (k == 40) {
                dir = 0;
            }
            else if (k == 37) {
                dir = 1;
            }
            else if (k == 39) {
                dir = 3;
            }
            if (this.green.move(dir)) {
                this.map.setGreen(this.green.getX(), this.green.getY());
                this.changeTurn();
            }
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent ae) {
        if (!this.finished) {
            if (!this.green.goable(this.map)) {
                System.out.println("Do Thang");
                this.finished = true;
            }
            else if (this.red.getTurn()) {
                final Long T = System.nanoTime();
                AI ai = new AI(new Map(this.map,new Point(this.red.getX()+1,this.red.getY()+1),new Point(this.green.getX()+1,this.green.getY()+1)));              
                this.red.move(ai.caculate2());
                this.map.setRed(this.red.getX(), this.red.getY());
                if (!this.red.goable(this.map)) {
                    System.out.println("Xanh Thang");
                    this.finished = true;
                }
                else {
                    this.changeTurn();
                }
            }
        }
    }
}
