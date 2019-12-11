package ai;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duc
 */
public class Point {
    public int x;
    public int y;
    public Point(){}
    public Point(int _x,int _y){
        x = _x;
        y = _y;
    }
    public Point(Point p){
        this(p.x,p.y);
    }
    
    public boolean equals (Point p){
        return x == p.x && y == p.y;
    }
}
