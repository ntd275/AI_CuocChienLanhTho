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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AI {

    public Map map;
    public final int INFINITY = 9999999;
    public final int NEGATE_INFINITY = -9999999;
    public final int DEPTH_MAX = 30;
    public final long MAX_TIME = 900000000;
    public final int SC = 500;
    public final int WN = 5; 
    public final int WE = 2; 
    public final int WPV = 5; 
    public final int WD = 4; 
    public int depth = 12;
    public boolean curturn = true; // true ai false player 
    public boolean pv = false;
    public boolean timeout = false;
    public ArrayList<Point> historyai = new ArrayList<>();
    public ArrayList<Point> historyplayer = new ArrayList<>();
    public int scorev1max;
    public long timebegin = 0;
    public Point bestmove = null;

    public AI(Map m) {
        map = m;
    }
    
    public int caculate2(){
        move(map.ai);
        move(map.player);
        Point old = new Point(map.ai);
        Point p = caculate();
        if (p.x == old.x-1) return 1;
        if (p.x == old.x+1) return 3;
        if (p.y == old.y-1) return 2;
        if (p.y == old.y+1) return 0;
        return 0;
    }

    public Point caculate() {
        timebegin = System.nanoTime();
        timeout = false;
        depth = 0;
        Point p;
        while (!timeout && depth <= DEPTH_MAX) {
            depth++;
            if (pv) {
                p = findlongestpath();
            } else if (phanvung()) {
                pv = true;
                p = findlongestpath();
            } else {
                p = alphabeta();
            }
            if(!timeout){
                if(p == null) bestmove =new Point(1, 1); 
                else bestmove = new Point(p);
            }
        }
        move(bestmove);
        System.out.print("DEPTH: ");
        System.out.println(depth);
        System.out.print(bestmove.x);
        System.out.print(" ");
        System.out.println(bestmove.y);
        return bestmove;
    }

    public Point findlongestpath() {
        scorev1max = 0;
        ArrayList<Point> next;
        next = nextmove();
        Point p = null;
        int v = NEGATE_INFINITY;
        for (int i = 0; i < next.size(); i++) {
            moveai(next.get(i));
            int temp = search(depth);
            if (temp > v) {
                v = temp;
                p = next.get(i);
            }
            undoai();
        }
        return p;
    }

    public int search(int d) {
        if(timeout) return 0;
        if(System.nanoTime()- timebegin > MAX_TIME){
            timeout = true;
            return 0;
        }
        
        if (d == 0) {
            return scorev1();
        }
        int v = NEGATE_INFINITY + 100 - d;
        ArrayList<Point> next;
        next = nextmove();
        Point p = null;
        for (int i = 0; i < next.size(); i++) {
            moveai(next.get(i));
            v = Math.max(v, search(d - 1));
            undoai();
        }
        return v;
    }

    public int scorev1() {
        int[][] dd = new int[map.width + 2][map.width + 2];
        int s = 0;
        Queue<Point> q;
        q = new LinkedList<>();

        ArrayList<Point> next;
        next = nextmove();

        for (int i = 0; i < next.size(); i++) {
            int t = 0;

            Point ptemp = next.get(i);
            dd[ptemp.x][ptemp.y] = 1;
            q.add(ptemp);
            while (!q.isEmpty()) {
                Point p = q.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0) {
                    if (map.isspace(temp)) {
                        dd[temp.x][temp.y] = 1;
                        t += map.spacearound(temp);
                        q.add(temp);
                    }
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0) {
                    if (map.isspace(temp)) {
                        dd[temp.x][temp.y] = 1;
                        t += map.spacearound(temp);
                        q.add(temp);
                    }
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0) {
                    if (map.isspace(temp)) {
                        dd[temp.x][temp.y] = 1;
                        t += map.spacearound(temp);
                        q.add(temp);
                    }
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0) {
                    if (map.isspace(temp)) {
                        dd[temp.x][temp.y] = 1;
                        t += map.spacearound(temp);
                        q.add(temp);
                    }
                }
            }
            s = Math.max(s, t);
        }
        return s;
    }

    public Point alphabeta() {
        int v = NEGATE_INFINITY;
        int a = NEGATE_INFINITY;
        int b = INFINITY;
        Point p = null;
        ArrayList<Point> next;
        next = nextmove();
        for (int i = 0; i < next.size(); i++) {
            move(next.get(i));
            int temp = minvalue(a, b, depth);
            if (temp > v) {
                v = temp;
                p = next.get(i);
            }
            undo();
            a = Math.max(a, v);
        }
        System.out.println(v);
        return p;
    }

    public int maxvalue(int a, int b, int d) {
        if(timeout) return 0;
        if(System.nanoTime()- timebegin > MAX_TIME){
            timeout = true;
            return 0;
        }
        if (d == 0) {
            if (phanvung()) {
                int temp = luonggiapv();
                if(temp > 0) return temp << WPV;
                return SC+(temp << 1);
            }
            return luonggiachuapv();
        }
        int v = this.NEGATE_INFINITY + 100 - d;
        ArrayList<Point> next;
        next = nextmove();
        for (int i = 0; i < next.size(); i++) {
            move(next.get(i));
            v = Math.max(v, minvalue(a, b, d - 1));
            undo();
            if (v >= b) {
                return v;
            }
            a = Math.max(a, v);
        }
        return v;

    }

    public int minvalue(int a, int b, int d) {
        if(timeout) return 0;
        if(System.nanoTime()- timebegin > MAX_TIME){
            timeout = true;
            return 0;
        }
        if (d == 0) {
            if (phanvung()) {
                int temp = luonggiapv();
                if(temp > 0) return temp << WPV;
                return SC + (temp << 1);
            }
            return luonggiachuapv();
        }
        int v = this.INFINITY - 100 + d;
        ArrayList<Point> next;
        next = nextmove();
        for (int i = 0; i < next.size(); i++) {
            move(next.get(i));
            v = Math.min(v, maxvalue(a, b, d - 1));
            undo();
            if (v <= a) {
                return v;
            }
            b = Math.min(b, v);
        }
        return v;
    }

    public ArrayList<Point> nextmove() {
        ArrayList<Point> t;
        t = new ArrayList<>();
        Point cur = curturn ? map.ai : map.player;
        Point temp;
        temp = new Point(cur.x, cur.y + 1);
        if (this.map.isspace(temp)) {
            t.add(temp);
        }
        temp = new Point(cur.x, cur.y - 1);
        if (this.map.isspace(temp)) {
            t.add(temp);
        }
        temp = new Point(cur.x + 1, cur.y);
        if (this.map.isspace(temp)) {
            t.add(temp);
        }
        temp = new Point(cur.x - 1, cur.y);
        if (this.map.isspace(temp)) {
            t.add(temp);
        }
        return t;
    }

    public void move(Point p) {
        if (curturn) {
            map.setai(p);
            historyai.add(p);
        } else {
            map.setplayer(p);
            historyplayer.add(p);
        }
        curturn = !curturn;
    }

    public void moveai(Point p) {
        map.setai(p);
        historyai.add(p);
    }

    public void undo() {
        if (curturn) {
            int last = historyplayer.size() - 1;
            Point p = historyplayer.get(last);
            map.setspace(p);
            map.player = historyplayer.get(last - 1);
            historyplayer.remove(last);
        } else {

            int last = historyai.size() - 1;
            Point p = historyai.get(last);
            map.setspace(p);
            map.ai = historyai.get(last - 1);
            historyai.remove(last);
        }
        curturn = !curturn;
    }

    public void undoai() {
        int last = historyai.size() - 1;
        Point p = historyai.get(last);
        map.setspace(p);
        map.ai = historyai.get(last - 1);
        historyai.remove(last);
    }

    public boolean phanvung() {
        Map mtemp = new Map(map);
        Queue<Point> q;
        q = new LinkedList<>();
        q.add(mtemp.ai);

        while (!q.isEmpty()) {
            Point p = q.poll();
            Point temp;

            temp = new Point(p.x + 1, p.y);
            if (temp.equals(mtemp.player)) {
                return false;
            }
            if (mtemp.isspace(temp)) {
                mtemp.setai(temp);
                q.add(temp);
            }

            temp = new Point(p.x - 1, p.y);
            if (temp.equals(mtemp.player)) {
                return false;
            }
            if (mtemp.isspace(temp)) {
                mtemp.setai(temp);
                q.add(temp);
            }

            temp = new Point(p.x, p.y + 1);
            if (temp.equals(mtemp.player)) {
                return false;
            }
            if (mtemp.isspace(temp)) {
                mtemp.setai(temp);
                q.add(temp);
            }

            temp = new Point(p.x, p.y - 1);
            if (temp.equals(mtemp.player)) {
                return false;
            }
            if (mtemp.isspace(temp)) {
                mtemp.setai(temp);
                q.add(temp);
            }
        }
        return true;
    }
    
    public int distantCenter(){
        Point center = new Point(map.width/2+1,map.width/2+1);
        return Math.abs(map.ai.x-center.x)+Math.abs(map.ai.y-center.y);
    }

    public int luonggiachuapv() {
        int[][] dd = new int[map.width + 2][map.width + 2];
        int score_node = 0;
        int score_egde = 0;
        Queue<Point> q1;
        q1 = new LinkedList<>();
        Queue<Point> q2;
        q2 = new LinkedList<>();
        q1.add(map.ai);
        q2.add(map.player);
        while (!q1.isEmpty() || !q2.isEmpty()) {

            Queue<Point> qtemp;
            qtemp = new LinkedList<>();
            while (!q1.isEmpty()) {
                Point p = q1.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    score_node++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    score_node++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    score_node++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    score_node++;
                    qtemp.add(temp);
                }
            }

            q1 = qtemp;

            qtemp = new LinkedList<>();

            while (!q2.isEmpty()) {
                Point p = q2.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    score_node--;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    score_node--;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    score_node--;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    score_node--;
                    qtemp.add(temp);
                }
            }
            q2 = qtemp;
        }
        return (score_node << WN) + (score_egde << WE) - (distantCenter() << WD);
    }
    
    public int luonggiapv(){
        int[][] dd = new int[map.width + 2][map.width + 2];
        int maxmoveai = 0;
        int maxmoveplayer = 0;
        int score_egde = 0;
        int sumblack = 0;
        int sumwhite = 0;
        Queue<Point> q;
        q = new LinkedList<>();
        q.add(map.ai);
        while (!q.isEmpty()) {
            Queue<Point> qtemp;
            qtemp = new LinkedList<>();
            while (!q.isEmpty()) {
                Point p = q.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumblack ++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumblack ++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumblack++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumblack++;
                    qtemp.add(temp);
                }
            }

            q = qtemp;
            qtemp = new LinkedList<>();

            while (!q.isEmpty()) {
                Point p = q.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde += map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }
            }
            q = qtemp;
        }
        
        if(sumblack > sumwhite){
            maxmoveai = (sumwhite << 1) + 1;
        }
        else{
            maxmoveai = sumblack << 1;
        }
        
        sumblack =0;
        sumwhite =0;
        
        
        q.add(map.player);
        while (!q.isEmpty()) {
            Queue<Point> qtemp;
            qtemp = new LinkedList<>();
            while (!q.isEmpty()) {
                Point p = q.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumblack ++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumblack ++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumblack++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumblack++;
                    qtemp.add(temp);
                }
            }

            q = qtemp;
            qtemp = new LinkedList<>();

            while (!q.isEmpty()) {
                Point p = q.poll();
                Point temp;

                temp = new Point(p.x + 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x - 1, p.y);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y + 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }

                temp = new Point(p.x, p.y - 1);
                if (dd[temp.x][temp.y] == 0 && map.isspace(temp)) {
                    dd[temp.x][temp.y] = 1;
                    score_egde -= map.spacearound(temp);
                    sumwhite++;
                    qtemp.add(temp);
                }
            }
            q = qtemp;
        }
        
        if(sumblack > sumwhite){
            maxmoveplayer = (sumwhite << 1) + 1;
        }
        else{
            maxmoveplayer = sumblack << 1;
        }
        
        return ((maxmoveai-maxmoveplayer) << WN) + (score_egde << WE);
    }
}
