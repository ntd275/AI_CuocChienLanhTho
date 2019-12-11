/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author duc
 */
public class PointS extends Point {
    public int d;
    
    PointS(int x,int y,int d){
        this.x = x;
        this.y = y;
        this.d = d;
    }
    
    PointS(Point p,int d){
        this(p.x,p.y,d);
    }
}
