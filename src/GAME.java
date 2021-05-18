import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created 2021-04-27
 *
 * @author
 */

public class GAME extends Canvas implements Runnable {

    private Thread thread;
    int fps = 30;
    private boolean isRunning;

    private BufferStrategy bs;

    private int platformx, platformsize, platformspeed;
    double speedmodifier;
    private boolean a,d;
    private double Angle;

    private int balllost, i, b;
    private ArrayList<Brick> Bricks = new ArrayList<Brick>();
    private ArrayList<Powerup> Powups = new ArrayList<Powerup>();
    private ArrayList<Ball> Balls = new ArrayList<Ball>();
    private int map1x[] = {210,210,210,210,210,280,280,280,280,280,350,350,350,350,350,420,420,420,420,420,490,490,490,490,490,560,560,560,560,560,630,630,630,630,630,700,700,700,700,700,770,770,770,770,770,840,840,840,840,840,910,910,910,910,910,980,980,980,980,980,};
    private int map1y[] = {200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,200,230,260,290,320,};
    private int map2x[] = {210, 210};
    private int map2y[] = {200, 230};
    private double R; //ball speed value


    public GAME() {
        JFrame frame = new JFrame("Breakout");
        this.setSize(1980,650);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        a=true;
        d=true;

        for (i=0; i<map1x.length; i++) {
            Bricks.add(new Brick(map1x[i], map1y[i]));
        }



        R=Math.sqrt(30);
        Angle = Math.toRadians((Math.random()*50)+25);

        Balls.add(new Ball(700, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));



        platformspeed = 0;
        speedmodifier = 1;
        platformsize = 25;
        platformx = ((1366/2)-platformsize);
    }


    public void update() {

            if (platformspeed < 0 && platformx > 200){
                platformx += (platformspeed*speedmodifier);
            } else if (platformspeed > 0 && (platformx+(platformsize*2))<1050) {
                platformx += (platformspeed*speedmodifier);
            } else if (platformspeed < 0 && platformx < 200) {
                platformx = 200;
            } else if (platformspeed > 0 && (platformx+(platformsize*2))>1050) {
                platformx = 1050-(platformsize*2);
            }

        Rectangle rect2;

            for (b=0; b < Balls.size(); b++) {
                Balls.get(b).X += Balls.get(b).getXv();
                if (Balls.get(b).getY() > 185 && Balls.get(b).getY() < 350) {
                    for (i = 0; i < Bricks.size(); i++) {
                        if (Balls.get(b).getRect().intersects(Bricks.get(i).getRect())) {

                            Balls.get(b).X *= -1;

                            if (Math.random() * 100 < 15) {
                                Powups.add(new Powerup(Bricks.get(i).getX() + 20, Bricks.get(i).getY()));
                            }

                            Bricks.remove(i);
                            i = Bricks.size() + 1;
                        }
                    } //Ball Hits bricks and might summon power upp
                }

                Balls.get(b).Y += Balls.get(b).getYv();
                if (Balls.get(b).getY() > 185 && Balls.get(b).getY() < 350) {
                    for (i = 0; i < Bricks.size(); i++) {
                        if (Balls.get(b).getRect().intersects(Bricks.get(i).getRect())) {

                            Balls.get(b).Y *= -1;

                            if (Math.random() * 100 < 15) {
                                Powups.add(new Powerup(Bricks.get(i).getX() + 20, Bricks.get(i).getY()));
                            }

                            Bricks.remove(i);
                            i = Bricks.size() + 1;
                        }
                    }
                }//Ball Hits bricks and might summon power upp


                if (Balls.get(b).getX() < 200) {
                    Balls.get(b).ballXv = Balls.get(b).ballXv * -1;
                } else if (Balls.get(b).getX() > 1040) {
                    Balls.get(b).ballXv = Balls.get(b).ballXv * -1;
                } //Ball hits wall
                if (Balls.get(b).getY() < 5) {
                    Balls.get(b).ballYv = Balls.get(b).ballYv * -1;
                } //Ball hits celling


                rect2 = new Rectangle(platformx, 625, platformsize * 2, 10);
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

                    if (Bricks.isEmpty()) {
                        for (i = 0; i < map1x.length; i++) {
                            Bricks.add(new Brick(map1x[i], map1y[i]));
                        }
                    }
                } //Ball hits platform

                if (Balls.get(b).getY() > 670) {
                    Balls.remove(b);
                } //losing the ball
            }

        if (Balls.isEmpty()) {
            if (balllost > 60) {
                Angle = Math.toRadians((Math.random() * 50) + 25);

                Balls.add(new Ball(platformx + platformsize - 5, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));

                balllost = 0;
            } else {
                balllost++;
            }
        }

        rect2 = new Rectangle(platformx, 625, platformsize * 2, 10);

        if (Powups.size()>0) {
            for (i = 0; i < Powups.size(); i++) {
                Powups.get(i).Y += Powups.get(i).getFallSpeed();

            if (Powups.get(i).getY()>650) {
                Powups.remove(i);
            } else if (Powups.get(i).getRect().intersects(rect2)) {
                if (Powups.get(i).getPower()==1) {
                    speedmodifier = 1.5;
                    Balls.add(new Ball(platformx + platformsize - 5, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));
                } else if (Powups.get(i).getPower()==2) {
                    speedmodifier = 0.8;
                    Balls.add(new Ball(platformx + platformsize - 5, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));
                } else if (Powups.get(i).getPower()==3) {
                    platformsize = 15;
                    Balls.add(new Ball(platformx + platformsize - 5, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));
                } else {
                    platformsize = 35;
                    Balls.add(new Ball(platformx + platformsize - 5, 590, R*Math.cos(Angle), -1*(R*Math.sin(Angle))));
                }
                Powups.remove(i);
            }
            }
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

        g.setColor(Color.white);
        g.fillRect(platformx, 625, platformsize*2, 10);
        for (b=0; b < Balls.size(); b++) {
            g.fillOval((int) Balls.get(b).X, (int) Balls.get(b).Y, 10, 10);
        }

        for (i=0; i<Bricks.size(); i++) {
            drawBrick(g, Bricks.get(i).getX(), Bricks.get(i).getY(), Bricks.get(i).getColor());
        }

        for (i=0; i<Powups.size(); i++) {
            drawPower(g, (int) Powups.get(i).getX(), (int) Powups.get(i).getY(), Powups.get(i).getColor());
        }

        g.dispose();
        bs.show();
    }

    private void drawBrick(Graphics g, int x, int y, Color C) {
        g.setColor(C.darker());
        g.fillRect(x,y,60,20);
        g.setColor(C);
        g.fillRect(x+3,y+3,54, 14);
    }

    private void drawPower(Graphics g, int x, int y, Color C) {
        g.setColor(C);
        g.fillRect(x,y,20,20);
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
                update();
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
        }

        @Override
        public void keyPressed(KeyEvent e) {

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
