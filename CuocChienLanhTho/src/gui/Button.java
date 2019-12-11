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

import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

class Button extends JToggleButton
{
    String label;
    public static final String SELECTED = "WALL";
    public static final String UNSELECT = "";
    public final ImageIcon WALLIMAGE;
    
    public Button() {
        this.WALLIMAGE = new ImageIcon(this.getClass().getResource("/lib/3.png"));
        this.setMaximumSize(new Dimension(60, 60));
        this.setMinimumSize(new Dimension(60, 60));
        this.setBackground(Color.decode("#1695a3"));
        this.setBorder(new LineBorder(Color.white));
    }
    
    public void click() {
        if (this.isSelected()) {
            this.setIcon(this.WALLIMAGE);
        }
        else {
            this.setIcon(null);
        }
    }
    
    public void reset() {
        this.setSelected(false);
        this.setIcon(null);
    }
    
    public void setClick() {
        if (this.isSelected()) {
            this.setSelected(false);
            this.setIcon(null);
        }
        else {
            this.setSelected(true);
            this.setIcon(this.WALLIMAGE);
        }
    }
}