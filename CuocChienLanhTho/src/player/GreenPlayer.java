/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author ntd27
 */
import javax.swing.ImageIcon;
import map.MapGui;

public class GreenPlayer extends Player
{
    public GreenPlayer(final int x, final int y, final MapGui map) {
        super(x, y, map);
        this.arrImage[0] = new ImageIcon(this.getClass().getResource("/lib/xex_4.png")).getImage();
        this.arrImage[1] = new ImageIcon(this.getClass().getResource("/lib/xex_2.png")).getImage();
        this.arrImage[2] = new ImageIcon(this.getClass().getResource("/lib/xex.png")).getImage();
        this.arrImage[3] = new ImageIcon(this.getClass().getResource("/lib/xex_3.png")).getImage();
        this.image = this.arrImage[0];
    }
}
