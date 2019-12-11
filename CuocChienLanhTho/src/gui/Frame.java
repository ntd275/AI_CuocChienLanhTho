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
import java.awt.event.ActionEvent;
import map.MapGui;
import java.io.FileNotFoundException;
import java.awt.event.KeyListener;
import java.awt.Container;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener
{
    Board board;
    Panel panel;
    PN pn;
    About ab;
    CardLayout card;
    JPanel mainpn;
    
    public Frame(final String s) {
        super(s);
        this.panel = new Panel(this);
        this.pn = new PN(this);
        this.ab = new About(this);
        this.card = new CardLayout();
        (this.mainpn = (JPanel)this.getContentPane()).setLayout(this.card);
        this.mainpn.add("pn", this.pn);
        this.mainpn.add("map", this.panel);
        this.mainpn.add("about", this.ab);
    }
    
    public void control() {
        this.card.show(this.mainpn, "pn");
    }
    
    public void map() {
        this.card.show(this.mainpn, "map");
    }
    
    public void play() throws FileNotFoundException {
        this.board = new Board();
        this.mainpn.add("play", this.board);
        this.addKeyListener(this.board);
        this.setFocusable(true);
        this.card.show(this.mainpn, "play");
    }
    
    public void play(final MapGui map) {
        this.board = new Board(map);
        this.mainpn.add("play1", this.board);
        this.addKeyListener(this.board);
        this.setFocusable(true);
        this.card.show(this.mainpn, "play1");
    }
    
    public void about() {
        this.card.show(this.mainpn, "about");
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
    }
}
