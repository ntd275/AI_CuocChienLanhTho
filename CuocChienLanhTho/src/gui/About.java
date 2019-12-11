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
import java.awt.Cursor;
import javax.swing.border.Border;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class About extends JPanel implements MouseListener
{
    private JLabel background;
    private Frame jf;
    private JButton back;
    
    public About(final Frame jf) {
        this.jf = jf;
        this.setLayout(null);
        this.background = new JLabel();
        (this.back = new JButton("")).setIcon(new ImageIcon(this.getClass().getResource("/lib/back.png")));
        this.add(this.back);
        this.background.setIcon(new ImageIcon(this.getClass().getResource("/lib/bg5.png")));
        this.add(this.background);
        this.back.setBounds(50, 600, 202, 44);
        this.back.setBorder(null);
        this.back.setCursor(new Cursor(12));
        this.background.setBounds(0, -30, 750, 750);
        this.back.addMouseListener(this);
        this.setFocusable(true);
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
        if (arg0.getSource() == this.back) {
            this.jf.control();
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
    }
}
