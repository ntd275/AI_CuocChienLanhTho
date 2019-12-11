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
import map.MapGui;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.border.Border;
import java.awt.Cursor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Panel extends JPanel implements MouseListener
{
    Button[][] buttons;
    JButton butok;
    Frame jf;
    Font font;
    
    public Panel(final Frame jf) {
        this.buttons = new Button[MapGui.WIDTH][MapGui.WIDTH];
        this.butok = new JButton("OK");
        this.font = new Font("MV Boli", 1, 18);
        this.jf = jf;
        this.butok.setBackground(Color.decode("#f1f1f1"));
        this.butok.setFont(this.font);
        this.butok.setIcon(new ImageIcon(this.getClass().getResource("/lib/ok.png")));
        this.butok.setText(null);
        this.butok.setCursor(new Cursor(12));
        this.butok.setBorder(null);
        this.addMouseListener(this);
        this.setFocusable(true);
        for (int i = 0; i < MapGui.WIDTH; ++i) {
            for (int j = 0; j < MapGui.WIDTH; ++j) {
                this.buttons[i][j] = new Button();
            }
        }
        this.setLayout(new GridLayout(MapGui.WIDTH, MapGui.WIDTH));
        for (int i = 0; i < MapGui.WIDTH; ++i) {
            for (int j = 0; j < MapGui.WIDTH; ++j) {
                if (i == MapGui.WIDTH/2 && j == MapGui.WIDTH/2) {
                    this.add(this.butok);
                    this.butok.addMouseListener(this);
                }
                else {
                    this.add(this.buttons[i][j]);
                    this.buttons[i][j].addMouseListener(this);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseExited(final MouseEvent arg0) {
    }
    
    @Override
    public void mousePressed(final MouseEvent arg0) {
        if (arg0.getSource() == this.butok) {
            final MapGui map = new MapGui(this.getMap());
            this.jf.play(map);
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
        for (int i = 0; i < MapGui.WIDTH; ++i) {
            for (int j = 0; j < MapGui.WIDTH; ++j) {
                if (arg0.getSource() == this.buttons[i][j]) {
                    this.buttons[i][j].click();
                    this.buttons[MapGui.WIDTH-1 - i][MapGui.WIDTH-1 - j].setClick();
                }
            }
        }
    }
    
    public MapGui getMap() {
        final int[][] map = new int[MapGui.WIDTH][MapGui.WIDTH];
        for (int i = 0; i < MapGui.WIDTH; ++i) {
            for (int j = 0; j < MapGui.WIDTH; ++j) {
                if (this.buttons[i][j].isSelected()) {
                    map[i][j] = 1;
                }
                else {
                    map[i][j] = 0;
                }
            }
        }
        return new MapGui(map);
    }
    
    public void check() {
        for (int i = 0; i < MapGui.WIDTH; ++i) {
            for (int j = 0; j < MapGui.WIDTH; ++j) {
                if (this.buttons[i][j].isSelected() && !this.buttons[MapGui.WIDTH-1 - i][MapGui.WIDTH-1 - j].isSelected()) {
                    this.buttons[i][j].reset();
                    this.buttons[MapGui.WIDTH-1 - i][MapGui.WIDTH-1 - j].reset();
                }
            }
        }
    }
}

