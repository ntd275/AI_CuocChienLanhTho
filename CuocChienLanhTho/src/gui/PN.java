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
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import javax.swing.border.Border;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class PN extends JPanel implements MouseListener
{
    private JButton play;
    private JButton map;
    private JButton about;
    private JButton exit;
    private JLabel background;
    private Frame jf;
    
    public PN(final Frame jf) {
        this.initComponents();
        this.jf = jf;
        this.play.addMouseListener(this);
        this.map.addMouseListener(this);
        this.about.addMouseListener(this);
        this.exit.addMouseListener(this);
    }
    
    private void initComponents() {
        this.play = new JButton();
        this.map = new JButton();
        this.about = new JButton();
        this.exit = new JButton();
        this.background = new JLabel();
        this.setLayout(null);
        this.play.setBorder(null);
        this.map.setBorder(null);
        this.about.setBorder(null);
        this.exit.setBorder(null);
        final Cursor cur = new Cursor(12);
        this.play.setCursor(cur);
        this.map.setCursor(cur);
        this.about.setCursor(cur);
        this.exit.setCursor(cur);
        this.play.setIcon(new ImageIcon(this.getClass().getResource("/lib/play.png")));
        this.map.setIcon(new ImageIcon(this.getClass().getResource("/lib/createmap.png")));
        this.about.setIcon(new ImageIcon(this.getClass().getResource("/lib/about.png")));
        this.exit.setIcon(new ImageIcon(this.getClass().getResource("/lib/exit.png")));
        final int n = 50;
        this.play.setBounds(450, 230 + n, 202, 44);
        this.map.setBounds(450, 300 + n, 202, 44);
        this.about.setBounds(450, 370 + n, 202, 44);
        this.exit.setBounds(450, 440 + n, 202, 44);
        this.add(this.play);
        this.add(this.map);
        this.add(this.about);
        this.add(this.exit);
        this.background.setIcon(new ImageIcon(this.getClass().getResource("/lib/bg4.png")));
        this.add(this.background);
        this.background.setBounds(0, -30, 750, 750);
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
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
        if (arg0.getSource() == this.play) {
            this.jf.setSize(940, 960);
            this.jf.map();
        }
        if (arg0.getSource() == this.map) {
            this.jf.setSize(940, 960);
            this.jf.map();
        }
        if (arg0.getSource() == this.about) {
            this.jf.about();
        }
        if (arg0.getSource() == this.exit) {
            System.exit(0);
        }
    }
}
