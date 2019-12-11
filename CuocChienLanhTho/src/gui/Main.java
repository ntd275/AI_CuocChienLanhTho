/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.FileNotFoundException;

/**
 *
 * @author ntd27
 */
public class Main {

    public static void main(final String[] args) throws FileNotFoundException {
        final Frame jf = new Frame("AI");
        jf.add(new PN(jf));
        jf.setVisible(true);
        jf.setSize(700, 720);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setAlwaysOnTop(false);
        jf.setDefaultCloseOperation(3);
    }
    
}
