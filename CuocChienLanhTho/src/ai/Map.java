package ai;

import java.util.LinkedList;
import java.util.Queue;
import map.MapGui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author duc
 */
public class Map {

    public int[][] map;
    public int width;
    public Point ai;
    public Point player;

    public Map(int[][] m, int w, Point prepoint) {
        map = m;
        width = w;
        for(int i = 0; i <= width+1; i++){
            map[0][i] = 3;
            map[width+1][i] = 3;
            map[i][0] = 3;
            map[i][width+1]=3;
        }
        ai = new Point(prepoint.x, prepoint.y);
        player = new Point(width + 1 - ai.x , width + 1 - ai.y); 
    }
    
    public Map(MapGui m,Point ai,Point player){
        this.ai = ai;
        this.player = player;
        this.width = MapGui.WIDTH;
        this.map = new int[width+2][width+2];
        for(int i = 0; i < width; i++)
            for(int j = 0; j < width; j++)
                this.map[i+1][j+1] = m.map[j][i];
        for(int i = 0; i <= width+1; i++){
            map[0][i] = 3;
            map[width+1][i] = 3;
            map[i][0] = 3;
            map[i][width+1]=3;
        }
    }
    
    public Map(Map m){
        width = m.width;
        map = new int[width+2][width+2];
        for(int i = 0; i < width+2; i++)
            System.arraycopy(m.map[i], 0, map[i], 0, width+2);
        ai = new Point(m.ai);
        player = new Point(m.player);
    }

    public boolean isspace(Point p) {
        return map[p.x][p.y] == 0;
    }
    
    public boolean isspace(int x,int y){
        return map[x][y] == 0;
    }
    
    public int spacearound(Point p){
        int s = 0;
        if(isspace(p.x+1,p.y)) s++;
        if(isspace(p.x-1,p.y)) s++;
        if(isspace(p.x,p.y+1)) s++;
        if(isspace(p.x,p.y-1)) s++;
        return s;
    }

    public void setai(Point p) {
        map[p.x][p.y] = 1;
        ai = p;
    }
    
    public void setplayer(Point p){
        map[p.x][p.y] = 2;
        player = p;
    }
    
    public void setspace(Point p){
        map[p.x][p.y] = 0;
    }
}
