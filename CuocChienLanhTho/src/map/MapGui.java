/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author ntd27
 */
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import java.util.Scanner;
import java.io.FileReader;
import java.awt.Image;

public class MapGui
{
    public static final int WIDTH = 15;
    public static final int W = 60;
    public static final int H = 60;
    public static final int SPACE = 0;
    public static final int WALL = 1;
    public static final int GREEN = 2;
    public static final int RED = 3;
    public static final int TEMP = 4;
    public int[][] map;
    private int[][] prices;
    private Image imagemap;
    private Image[] image;
    public static long dem;
    
    static {
        MapGui.dem = 0L;
    }
    
    public MapGui(final String s) throws FileNotFoundException {
        this.image = new Image[4];
        this.map = new int[WIDTH][WIDTH];
        this.prices = new int[WIDTH][WIDTH];
        final FileReader fr = new FileReader(this.getClass().getResource(s).getFile());
        final Scanner scan = new Scanner(fr);
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                this.map[i][j] = scan.nextInt();
            }
        }
        this.setPrices();
        this.imagemap = new ImageIcon(this.getClass().getResource("/lib/map.png")).getImage();
        this.image[1] = new ImageIcon(this.getClass().getResource("/lib/3.png")).getImage();
        this.image[3] = new ImageIcon(this.getClass().getResource("/lib/d.png")).getImage();
        this.image[2] = new ImageIcon(this.getClass().getResource("/lib/x.png")).getImage();
    }
    
    public MapGui(final int[][] m) {
        this.image = new Image[4];
        this.map = m;
        this.prices = new int[WIDTH][WIDTH];
        this.setPrices();
        this.imagemap = new ImageIcon(this.getClass().getResource("/lib/map.png")).getImage();
        this.image[1] = new ImageIcon(this.getClass().getResource("/lib/3.png")).getImage();
        this.image[3] = new ImageIcon(this.getClass().getResource("/lib/d.png")).getImage();
        this.image[2] = new ImageIcon(this.getClass().getResource("/lib/x.png")).getImage();
    }
    
    public MapGui(final MapGui ma) {
        this.image = new Image[4];
        this.map = new int[WIDTH][WIDTH];
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                this.map[i][j] = ma.map[i][j];
            }
        }
        this.prices = new int[WIDTH][WIDTH];
        this.imagemap = ma.imagemap;
        this.image = ma.image;
    }
    
    public boolean hasSpaceAround(final int x, final int y) {
        return this.isSpace(x - 1, y) || this.isSpace(x + 1, y) || this.isSpace(x, y - 1) || this.isSpace(x, y + 1);
    }
    
    public int amountSpacesAround(final int x, final int y) {
        int count = 0;
        if (this.isSpace(x - 1, y)) {
            ++count;
        }
        if (this.isSpace(x + 1, y)) {
            ++count;
        }
        if (this.isSpace(x, y - 1)) {
            ++count;
        }
        if (this.isSpace(x, y + 1)) {
            ++count;
        }
        return count;
    }
    
    public int amountSpacesNotTempAround(final int x, final int y) {
        int count = 0;
        if (this.isSpaceAndNotTemp(x - 1, y)) {
            ++count;
        }
        if (this.isSpaceAndNotTemp(x + 1, y)) {
            ++count;
        }
        if (this.isSpaceAndNotTemp(x, y - 1)) {
            ++count;
        }
        if (this.isSpaceAndNotTemp(x, y + 1)) {
            ++count;
        }
        return count;
    }
    
    public int[][] getPrices() {
        return this.prices;
    }
    
    private void setPrices() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                this.prices[i][j] = 1;
            }
        }
    }
    
    public Image getImageMap() {
        return this.imagemap;
    }
    
    public Image getImage(final int x, final int y) {
        if (x >= WIDTH || y >= WIDTH) {
            return null;
        }
        final int i = this.map[y][x];
        if (i > 0 && i < 4) {
            return this.image[i];
        }
        return null;
    }
    
    public void printMap() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
        System.out.println("***********");
    }
    
    public void printPrices() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                System.out.print(this.prices[i][j]);
            }
            System.out.println();
        }
    }
    
    public boolean isSpace(final int x, final int y) {
        return x < WIDTH && y < WIDTH && x >= 0 && y >= 0 && (this.map[y][x] == 0 || this.map[y][x] == 4);
    }
    
    public boolean isSpaceAndNotTemp(final int x, final int y) {
        return x < WIDTH && y < WIDTH && x >= 0 && y >= 0 && this.map[y][x] == 0;
    }
    
    public void setTemp(final int x, final int y) {
        this.map[y][x] = 4;
    }
    
    public int getPrice(final int x, final int y) {
        return this.prices[y][x];
    }
    
    public boolean isWall(final int x, final int y) {
        return x >= WIDTH || y >= WIDTH || x < 0 || y < 0 || this.map[y][x] == 1;
    }
    
    public void setRed(final int x, final int y) {
        this.map[y][x] = 3;
    }
    
    public void setGreen(final int x, final int y) {
        this.map[y][x] = 2;
    }
    
    public void setMap(final int x, final int y) {
        this.map[y][x] = 10;
    }
    
    public boolean isReachable(final int x, final int y) {
        return x < WIDTH && y < WIDTH && x >= 0 && y >= 0 && this.map[y][x] == 10;
    }
    
    public void setSpace(final int x, final int y) {
        this.map[y][x] = 0;
    }
}

