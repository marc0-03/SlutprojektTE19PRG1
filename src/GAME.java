import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created 2021-04-27
 *
 * @author
 */

public class GAME extends Canvas implements Runnable {

    private Thread thread;
    int fps = 60;
    private boolean isRunning;

    private BufferStrategy bs;

    private int platformx, platformsize, platformspeed;
    double speedmodifier;
    private boolean a,d,ballReady, paused;
    private double Angle;
    private int newBrickssWait, Level, Lives, Score, ScoreMuli;

    private int balllost, i, b, Map;
    private ArrayList<Brick> Bricks = new ArrayList<Brick>();
    private ArrayList<Powerup> Powups = new ArrayList<Powerup>();
    private ArrayList<Ball> Balls = new ArrayList<Ball>();
    private final int[] map1x = {210,210,210,210,210,280,280,280,280,280,350,350,350,350,350,420,420,420,420,420,490,490,490,490,490,560,560,560,560,560,630,630,630,630,630,700,700,700,700,700,770,770,770,770,770,840,840,840,840,840,910,910,910,910,910,980,980,980,980,980,};
    private final int[] map1y = {200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,};
    private final int[] map2x = { 210, 210, 210, 210, 210, 210, 210, 350, 350, 350, 350, 350, 350, 350, 420, 420, 420, 420, 420, 420, 420, 490, 490, 490, 490, 490, 490, 490, 560, 560, 560, 560, 560, 560, 560, 630, 630, 630, 630, 630, 630, 630, 700, 700, 700, 700, 700, 700, 700, 770, 770, 770, 770, 770, 770, 770, 980, 980, 980, 980, 980, 980, 980, 840, 840, 840, 840, 840, 840, 840,};
    private final int[] map2y = { 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320, 140, 170, 200, 230, 260, 290, 320,};
    private final int[] map3x = {210, 210};
    private final int[] map3y = {200, 230};
    private double R; //ball speed value


    public GAME() {
        JFrame frame = new JFrame("Breakout");
        this.setSize(1980,650);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        Reset();
        paused=false;
    }

    public void update() {
        if (Lives>0 && !paused) {
            PlatformMove();

            Rectangle rect2;

            for (b = 0; b < Balls.size(); b++) {
                Balls.get(b).X += Balls.get(b).getXv();
                if (Balls.get(b).getY() < 370) {
                    for (i = 0; i < Bricks.size(); i++) {
                        if (Balls.get(b).getRect().intersects(Bricks.get(i).getRect())) {

                            Balls.get(b).ballXv *= -1;

                            Score += 100*ScoreMuli;

                            if (Math.random() * 100 < 95) {
                                Powups.add(new Powerup(Bricks.get(i).getX() + 20, Bricks.get(i).getY()));
                            }

                            Bricks.remove(i);
                            Balls.get(b).X += Balls.get(b).getXv();
                            i = Bricks.size() + 1;
                        }
                    } //Ball Hits bricks and might summon power upp
                }

                Balls.get(b).Y += Balls.get(b).getYv();
                if (Balls.get(b).getY() < 370) {
                    for (i = 0; i < Bricks.size(); i++) {
                        if (Balls.get(b).getRect().intersects(Bricks.get(i).getRect())) {

                            Balls.get(b).ballYv *= -1;

                            Score += 100*ScoreMuli;

                            if (Math.random() * 100 < 95) {
                                Powups.add(new Powerup(Bricks.get(i).getX() + 20, Bricks.get(i).getY()));
                            }

                            Bricks.remove(i);
                            Balls.get(b).Y += Balls.get(b).getYv();
                            i = Bricks.size() + 1;
                        }
                    }
                }//Ball Hits bricks and might summon power upp


                ballHitWalls();
                ballHitCelling();


                rect2 = new Rectangle(platformx - platformsize, 625, platformsize * 2, 10);
                if (Balls.get(b).getRect().intersects(rect2)) {
                    Angle = Math.toRadians((Math.random() * 50) + 25);

                    Balls.get(b).ballYv = -1 * (R * Math.sin(Angle));

                    if (platformspeed > 0) {
                        Balls.get(b).ballXv = (R * Math.cos(Angle));
                    } else if (platformspeed < 0) {
                        Balls.get(b).ballXv = -1 * (R * Math.cos(Angle));
                    } else {
                        if (Balls.get(b).getXv() > 0) {
                            Balls.get(b).ballXv = (R * Math.cos(Angle));
                        } else {
                            Balls.get(b).ballXv = -1 * (R * Math.cos(Angle));
                        }
                    }

                } //Ball hits platform

                if (Balls.get(b).getY() > 670) {
                    Balls.remove(b);
                } //losing the ball
            }

            if (Balls.isEmpty() && !ballReady && newBrickssWait == 0) {
                if (balllost > 60) {

                    ballReady = true;

                    balllost = 0;
                    Powups.clear();
                    Lives--;
                    if (Lives==0){
                        newHigh();
                    }
                } else {
                    balllost++;
                }
            }

            newMapCheck();

            rect2 = new Rectangle(platformx - platformsize, 625, platformsize * 2, 10);

            powerCheckHit(rect2);
        }
    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        update();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,1366, 768);
        g.setColor(new Color(120,100,0));
        g.fillRect(0,0,200,768);
        g.fillRect(1050,0,230,768);
        g.setColor(new Color(150,130,0));
        g.fillRect(5,5,190,640);
        g.fillRect(1055,5,220,640);
        g.setColor(new Color(120,100,0));
        g.fillRect(20,20,160,600);
        g.fillRect(1070,20,160,160);
        g.fillRect(1070,195,160,390);
        g.setColor(Color.BLACK);
        g.fillRect(25,25,150,590);
        g.fillRect(1075,25,150,150);
        g.fillRect(1075,200,150,380);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.setColor(new Color(229, 187, 60));
        g.drawString("HighScores",27,45);
        g.drawString("lives: " + Lives,1087,45);
        g.fillRect(28,46,142,4);

        g.setFont(new Font("Monospaced", Font.BOLD, 28));
        ArrayList<Integer> highs = getHigh();
        for (i=0; i<highs.size(); i++) {
            g.drawString(highs.get(i) + "", 27,75+(i*25));
        }


        drawHeart(g,1100,60);
        drawPowerList(g,1080, 220);

        g.setColor(Color.white);
        g.fillRect(platformx-platformsize, 625, platformsize*2, 10);
        if (ballReady) {
            g.fillOval(platformx-5,610,10,10);
            if (Score==0) {
                g.setFont(new Font("Monospaced", Font.BOLD, 40));
                g.drawString("Use A And D To Move", 400, 500);
                g.setFont(new Font("Monospaced", Font.BOLD, 50));
                g.drawString("Press Space To Start", 330, 550);
            }
        }

        for (b=0; b < Balls.size(); b++) {
            g.fillOval((int) Balls.get(b).X, (int) Balls.get(b).Y, 10, 10);
        }

        for (i=0; i<Bricks.size(); i++) {
            drawBrick(g, Bricks.get(i).getX(), Bricks.get(i).getY(), Bricks.get(i).getColor());
        }
        for (i=0; i<Powups.size(); i++) {
            drawPower(g, (int) Powups.get(i).getX(), (int) Powups.get(i).getY(), Powups.get(i).getColor(), Powups.get(i).getPower());
        }

        if (newBrickssWait>0 && newBrickssWait<160) {
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 70));
            g.setColor(Color.white);
            g.drawString(("LEVEL " + Level + " COMPLETED"), 250, 100);
            if (Level%5 == 0) {
                g.drawString(("You gain 1 life"), 350, 400);
            }
        }

        if (Lives==0){
            g.setColor(Color.BLACK);
            g.fillRect(200,0,850,600);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 140));
            g.drawString("GAME OVER", 250, 120);
            g.setFont(new Font("Monospaced", Font.BOLD, 75));
            g.drawString((Score+" Pts"),450,300);
            g.drawString(("Press R to restart"), 215, 570);

            if (highs.contains(Score)) {
                g.setColor(new Color(120,100,0));
                g.drawString(("New HighScore"), 343, 219);
                g.setColor(new Color(150,130,0));
                g.drawString(("New HighScore"), 340, 220);
            }
        } else {
            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.setColor(Color.white);
            g.drawString(("Score: " + Score), 205,14);
            g.drawString(("Balls: " + Balls.size()), 205,28);
        }

        if (paused) {
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 200));
            g.setColor(new Color(0,0,0));
            g.drawString("Paused",300,300);

            g.setColor(new Color(50,50,50));
            g.drawString("Paused",297,303);

            g.setColor(new Color(100,100,100));
            g.drawString("Paused",294,306);

            g.setColor(new Color(150,150,150));
            g.drawString("Paused",291,309);

            g.setColor(new Color(200,200,200));
            g.drawString("Paused",288,312);

            g.setColor(new Color(255,255,255));
            g.drawString("Paused",285,315);

        }

        g.dispose();
        bs.show();
    }

    private void newMapCheck() {
        if (Bricks.isEmpty()) {
            if (newBrickssWait >= 180){
                newBrickssWait = 0;
                Balls.clear();
                Powups.clear();
                ballReady=true;
                ResetAtrb();
                Map = (int) ((Math.random()*2)+1);
                if (Map == 1) {
                    for (i = 0; i < map1x.length; i++) {
                        Bricks.add(new Brick(map1x[i], map1y[i]));
                    }
                } else {
                    for (i = 0; i < map2x.length; i++) {
                        Bricks.add(new Brick(map2x[i], map2y[i]));
                    }
                }
                R=5.5+Level*0.25;
                if (Level%5==0){
                    Lives++;
                }
                Level++;
            } else {
                newBrickssWait++;
            }

        }
    }

    private void powerCheckHit(Rectangle rect2) {
        if (Powups.size()>0) {
            for (i = 0; i < Powups.size(); i++) {
                Powups.get(i).Y += Powups.get(i).getFallSpeed();

            if (Powups.get(i).getY()>650) {
                Powups.remove(i);
            } else if (Powups.get(i).getRect().intersects(rect2)) {
                if (Powups.get(i).getPower()==1) {
                    if (speedmodifier<2.5) {
                        speedmodifier += 0.2;
                    }
                } else if (Powups.get(i).getPower()==2) {
                    if (speedmodifier>0.5) {
                        speedmodifier -= 0.2;
                    }
                } else if (Powups.get(i).getPower()==3) {
                    if (platformsize!=15) {
                        platformsize -= 10;
                    }
                } else if (Powups.get(i).getPower()==4){
                    if (platformsize!=115) {
                        platformsize += 10;
                        if(platformx-platformsize < 200) {
                            platformx = 200+platformsize;
                        } else if (platformx+platformsize>1050) {
                            platformx = 1050-platformsize;
                        }
                    }
                } else if (Powups.get(i).getPower()==5){
                   ballSpawnRandomDirection();
                   ballSpawnRandomDirection();
                   ballSpawnRandomDirection();
                } else {
                    if (ScoreMuli!=64) {
                        ScoreMuli *= 2;
                    }
                }
                Powups.remove(i);
            }
            }
        }
    } //Checks if the platform touches any powerups

    private void ballHitWalls() {
        if (Balls.get(b).getX() < 200) {
            Balls.get(b).ballXv *= -1;
            Balls.get(b).X = 200;
        } else if (Balls.get(b).getX() > 1040) {
            Balls.get(b).ballXv *= -1;
            Balls.get(b).X = 1040;
        } //Ball hits wall
    } //Checks if any balls touch a wall

    private void ballHitCelling() {
        if (Balls.get(b).getY() < 5) {
            Balls.get(b).ballYv *= -1;
            Balls.get(b).Y=5;
        }
    } //Checks if any balls touch the celling

    private void ballSpawnRandomDirection() {
        Angle = Math.toRadians((Math.random() * 50) + 25);

        int randdir = (int) (Math.random()*2)+1;

        if (randdir==1) {
            Balls.add(new Ball(platformx-5, 610, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));
        } else {
            Balls.add(new Ball(platformx-5, 610, -1*R*Math.cos(Angle), -1*R*Math.sin(Angle)));
        }
    } //Spaws a ball above the platform

    private void PlatformMove() {
        if ((platformspeed < 0 && platformx-platformsize+platformspeed < 200) && platformx != (200+platformsize)) {
            platformx = 200+platformsize;
        } else if ((platformspeed > 0 && platformx+platformsize+platformspeed > 1050) && platformx != (1050-platformsize)){
            platformx = 1050-platformsize;
        } else if ((platformspeed < 0 && platformx-platformsize > 200) || (platformspeed > 0 && (platformx+platformsize<1050))) {
            platformx += (platformspeed*speedmodifier);
        }
    } //Moves the platform

    private void shotBall() {
        Angle = Math.toRadians((Math.random()*50)+25);
        Double xv;

        if (platformspeed > 0) {
            xv = (R * Math.cos(Angle));
        } else if (platformspeed < 0) {
            xv = -1 * (R * Math.cos(Angle));
        } else {
            int Rand = (int) (Math.random()*2)+1;
            Angle = Math.toRadians(80);
            if (Rand==1) {
                xv = (R * Math.cos(Angle));
            } else {
                xv = -1 * (R * Math.cos(Angle));
            }
        }
        Balls.add(new Ball(platformx-5,610, xv, -1*(R*Math.sin(Angle))));

        ballReady=false;
    }

    private ArrayList<Integer> getHigh(){
        ArrayList<Integer> highscores = new ArrayList<>();
        try{
            File Higscores = new File("Highscore.txt");
            Scanner filereader = new Scanner(Higscores);
            while (filereader.hasNextLine()){
                highscores.add(filereader.nextInt());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return highscores;
    }

    private void newHigh() {
        ArrayList<Integer> scores =  getHigh();
        scores.add(Score);
        Collections.sort(scores);
        try {
            FileWriter Writer = new FileWriter("Highscore.txt");
            for (i = scores.size()-1; i>0;i--) {
                if (1!=i) {
                    Writer.write(scores.get(i)+"\n");
                } else {
                    Writer.write(scores.get(i)+"");
                }
            }
            Writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void drawBrick(Graphics g, int x, int y, Color C) {
        g.setColor(C.darker());
        g.fillRect(x,y,60,20);
        g.setColor(C);
        g.fillRect(x+3,y+3,54, 14);
    }

    private void drawHeart(Graphics g, int x, int y) {
        if (Lives==1) {
            g.setColor(Color.darkGray);
        } else if (Lives==2) {
            g.setColor(new Color(176, 41, 41));
        } else if (Lives>2) {
            g.setColor(new Color(200,0,0));
        } else {
            g.setColor(new Color(30,30,30));
        }
        g.fillOval(x,y,50,50);
        g.fillOval(x+40,y,50,50);
        int[] X = {x, x+45, x+90};
        int[] Y = {y+25, y+100, y+25};
        g.fillPolygon(X,Y,3);
    }

    private void drawPower(Graphics g, int x, int y, Color C, int P) {
        g.setColor(C);
        g.fillRect(x,y,20,20);
        g.setColor(C.darker());
        g.fillRect(x+2, y+2, 16, 16);
        g.setColor(C.brighter());
        if (P==5) {
            g.fillOval(x+2,y+2,7,7);
            g.fillOval(x+11,y+2,7,7);
            g.fillOval(x+6,y+10,8,8);
        } else if (P==1) {
            //speed up
            g.fillRect(x+4,y+8,12,4);
            g.fillRect(x+8,y+4,4,12);
        } else if (P==2) {
            //speed down
            g.fillRect(x+4,y+8,12,4);
        }  else if (P==3) {
            //platsize down
        }  else if (P==4) {
            //platsize up
        } else {
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            g.drawString("X2",x+2,y+14);

        }
    }

    private void drawPowerList(Graphics g, int x, int y) {
        g.setFont(new Font("Monospaced", Font.BOLD, 18));
        g.setColor(new Color(250, 4, 4));
        g.fillRect(x,y,20,20);
        g.setColor(new Color(250, 4, 4).darker());
        g.fillRect(x+2, y+2, 16, 16);
        g.drawString("Speed Up", x+25,y+15);

        g.setColor(new Color(200, 100, 4));
        g.fillRect(x,y+60,20,20);
        g.setColor(new Color(200, 100, 4).darker());
        g.fillRect(x+2, y+62, 16, 16);
        g.drawString("Speed Down", x+25,y+75);

        g.setColor(new Color(0, 100, 4));
        g.fillRect(x,y+120,20,20);
        g.setColor(new Color(0, 100, 4).darker());
        g.fillRect(x+2, y+122, 16, 16);
        g.drawString("Size Down", x+25,y+135);

        g.setColor(new Color(4, 150, 100));
        g.fillRect(x,y+180,20,20);
        g.setColor(new Color(4, 150, 100).darker());
        g.fillRect(x+2, y+182, 16, 16);
        g.drawString("Size Up", x+25,y+195);

        g.setColor(new Color(100,100,255));
        g.fillRect(x,y+240,20,20);
        g.setColor(new Color(100,100,255).darker());
        g.fillRect(x+2, y+242, 16, 16);
        g.drawString("More Balls", x+25,y+255);

        g.setColor(new Color(180, 0,255));
        g.fillRect(x,y+300,20,20);
        g.setColor(new Color(180, 0,255).darker());
        g.fillRect(x+2, y+302, 16, 16);
        g.drawString("Score Up", x+25,y+315);
    }

    private void Reset() {
        a=true;
        d=true;

        R=5.5; //Ballspeed

        Bricks.clear();
        Balls.clear();
        Powups.clear();
        ResetAtrb();
        Level = 1;
        Lives = 2;  //Must be above 0
        ballReady = true;
        platformx = (1366/2);
        Score = 0;

        Map = (int) ((Math.random()*2)+1);
        if (Map == 1) {
            for (i = 0; i < map1x.length; i++) {
                Bricks.add(new Brick(map1x[i], map1y[i]));
            }
        } else {
            for (i = 0; i < map2x.length; i++) {
                Bricks.add(new Brick(map2x[i], map2y[i]));
            }
        }
    }

    private void ResetAtrb() {
        speedmodifier = 1;
        platformsize = 25;
        newBrickssWait = 0;
        ScoreMuli=1;
    }



    public static void main(String[] args) {
        GAME painting = new GAME();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0 / fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now - lastTime > deltaT) {
                draw();
                lastTime = now;
            }
        }
        stop();
    }

    private class KL implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                if (a) {
                    platformspeed+=-4;
                    a=false;
                }
            }
            if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                if (d) {
                    platformspeed+=4;
                    d=false;
                }
            }
            if (e.getKeyChar() == ' ') {
                if (ballReady) {
                    shotBall();
                }
            }

            if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
                if (Lives<=0) {
                    Reset();
                }
            }

            if (e.getKeyChar() == 'b' || e.getKeyChar() == 'B') {
                for (i=0; i<500; i++) {
                    ballSpawnRandomDirection();
                }
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
                if (Lives!=0) {
                    if (paused) {
                        paused = false;
                    } else {
                        paused = true;
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                    platformspeed+=4;
                    a=true;
            }
            if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                    platformspeed+=-4;
                    d=true;
            }
        }
    }

}
