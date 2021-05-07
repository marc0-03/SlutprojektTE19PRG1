import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;

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

    private int balllost, i;
    private Collection<int> Brickx = new ArrayList<>();

    private double ballx, bally, ballxV, ballyV, R;


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

        ballx = 700;
        bally = 590;

        R=Math.sqrt(30);

        Angle = Math.toRadians((Math.random()*50)+25);

        ballyV = -1*(R*Math.sin(Angle));
        ballxV = (R*Math.cos(Angle));



        platformspeed = 0;
        speedmodifier = 1;
        platformsize = 25;
        platformx = ((1366/2)-platformsize);
    }


    public void update() {




            if (platformspeed < 0 && platformx > 200){
                platformx += (platformspeed*speedmodifier);
            } else if (platformspeed > 0 && (platformx+(platformsize*2))<1080) {
                platformx += (platformspeed*speedmodifier);
            }

            ballx += ballxV;
            bally += ballyV;

            if (ballx < 200) {
                ballxV = ballxV*-1;
            } else if (ballx > 1070) {
                ballxV = ballxV*-1;
            }
            if (bally < 5) {
                ballyV = ballyV*-1;
            }

            Rectangle rect1 = new Rectangle((int)ballx, (int)bally, 10, 10);
        Rectangle rect2 = new Rectangle(platformx, 625, platformsize*2, 10);

        if (rect1.intersects(rect2)) {
            Angle = Math.toRadians((Math.random()*50)+25);

            ballyV = -1*(R*Math.sin(Angle));

            if (platformspeed>0) {
                ballxV = (R*Math.cos(Angle));
            } else if (platformspeed<0) {
                ballxV = -1*(R*Math.cos(Angle));
            } else {
                if (ballxV>0) {
                    ballxV = (R*Math.cos(Angle));
                } else {
                    ballxV = -1*(R*Math.cos(Angle));
                }
            }
        }

        if (bally>670){

            if (balllost>60) {
                bally = 590;
                ballx = platformx+platformsize-5;

                Angle = Math.toRadians((Math.random()*50)+25);

                ballyV = -1 * (R * Math.sin(Angle));
                int randomdirection = (int) (Math.random()*2);

                if (randomdirection==2) {
                    ballxV = (R * Math.cos(Angle));
                } else {
                    ballxV = -1*(R * Math.cos(Angle));
                }
                balllost = 0;
            } else {
                balllost++;
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
        g.fillRect(1080,0,200,768);
        g.setColor(new Color(150,130,0));
        g.fillRect(5,5,190,640);
        g.fillRect(1085,5,190,640);

        g.setColor(Color.white);
        g.fillRect(platformx, 625, platformsize*2, 10);

        g.fillOval((int)ballx, (int)bally,10,10);



        g.dispose();
        bs.show();
    }

    private void drawBrick(Graphics g, int x, int y) {
        g.fillRect(x,y,60,20);
        g.fillRect(x+5,y+5,50, 10);
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
