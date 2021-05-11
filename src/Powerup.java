import java.awt.*;

public class Powerup {
        private Rectangle hitBox;
        private Color color;
        private Double fallSpeed;
        private int power;

    public Powerup(int x, int y) {
        power = (int) Math.random()*5;
        color = getColor();
        hitBox = new Rectangle(x,y,20,20);
        fallSpeed = (Math.random()*2)+1;
    }

    private Color getcorespondingColor() {
        Color color = new Color((int) (Math.random()*100+75), (int) (Math.random()*100+75), (int) (Math.random()*100+75));
        return color;
    }

    public int getX() {
        return hitBox.x;
    }

    public Rectangle getRect() {
        return hitBox;
    }

    public int getY() {
        return hitBox.y;
    }

    public Color getColor() {
        return color;
    }
}
