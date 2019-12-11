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
import map.MapGui;
import java.awt.Image;

public abstract class Player extends Thread
{
    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;
    Image image;
    protected Image[] arrImage;
    private int x;
    private int y;
    public int xp;
    public int yp;
    private int wp;
    private int hp;
    private int direction;
    private int speed;
    private boolean turn;
    private MapGui map;
    
    public Player(final int x, final int y, final MapGui map) {
        this.arrImage = new Image[4];
        this.turn = false;
        this.x = x;
        this.y = y;
        this.wp = 60;
        this.hp = 60;
        this.xp = x * this.wp;
        this.yp = y * this.wp;
        this.map = map;
    }
    
    public boolean goable(final MapGui map) {
        return map.hasSpaceAround(this.x, this.y);
    }
    
    private boolean stepMove() {
        if (this.xp > this.x * this.wp) {
            if (this.xp - this.x * this.wp < this.speed) {
                this.xp = this.x * this.wp;
            }
            else {
                this.xp -= this.speed;
            }
        }
        else if (this.xp < this.x * this.wp) {
            if (this.x * this.wp - this.xp < this.speed) {
                this.xp = this.x * this.wp;
            }
            else {
                this.xp += this.speed;
            }
        }
        else if (this.yp > this.y * this.hp) {
            if (this.yp - this.y * this.hp < this.speed) {
                this.yp = this.y * this.hp;
            }
            else {
                this.yp -= this.speed;
            }
        }
        else if (this.yp < this.y * this.hp) {
            if (this.y * this.hp - this.yp < this.speed) {
                this.yp = this.y * this.hp;
            }
            else {
                this.yp += this.speed;
            }
        }
        return this.xp == this.x * this.wp && this.yp == this.y * this.hp;
    }
    
    public void setTurn(final boolean b) {
        this.turn = b;
    }
    
    public boolean getTurn() {
        return this.turn;
    }
    
    public boolean move(final int direction) {
        int xt = this.x;
        int yt = this.y;
        if (direction == 1) {
            --xt;
        }
        else if (direction == 3) {
            ++xt;
        }
        else if (direction == 2) {
            --yt;
        }
        else if (direction == 0) {
            ++yt;
        }
        if (this.map.isSpace(xt, yt)) {
            this.direction = direction;
            this.x = xt;
            this.y = yt;
            this.setXpYp();
            this.updateImage();
            return true;
        }
        return false;
    }
    
    private void updateImage() {
        this.image = this.arrImage[this.direction];
    }
    
    public Image getImage() {
        return this.image;
    }
    
    public static void printDirection(final int d) {
        if (d == 2) {
            System.out.println("UP");
        }
        else if (d == 0) {
            System.out.println("DOWN");
        }
        else if (d == 1) {
            System.out.println("LEFT");
        }
        else if (d == 3) {
            System.out.println("RIGHT");
        }
        else {
            System.out.println("NO DIR");
        }
    }
    
    private void setXpYp() {
        this.xp = this.x * this.wp;
        this.yp = this.y * this.hp;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    @Override
    public void run() {
    }
}

