import java.awt.*;

public class Powerup {
        private Color color;
        private Double fallSpeed;
        private int power;
        public double X,Y;

    public Powerup(int x, int y) {
        power = (int) ((Math.random()*6)+1);
        color = correspondingColor();
        X = x;
        Y = y;
        fallSpeed = (Math.random()*2)+1;
    }

    private Color correspondingColor() {
        Color colo;
        if (power==1) {
            colo = new Color(250, 4, 4);
        } else if (power==2){
            colo = new Color(200, 100, 4);
        } else if (power==3){
            colo = new Color(0, 100, 4);
        } else if (power==4){
            colo = new Color(4, 150, 100);
        } else if (power==5){
            colo = new Color(100,100,255);
        } else {
            colo = new Color(180, 0,255);
        }
        return colo;
    }

    public double getX() {
        return X;
    }

    public int getPower() {
        return power;
    }

    public Rectangle getRect() {
        Rectangle hitBox = new Rectangle((int)X, (int)Y, 20, 20);
        return hitBox;
    }

    public double getY() {
        return Y;
    }

    public Color getColor() {
        return color;
    }

    public double getFallSpeed(){
        return fallSpeed;
    }
}
