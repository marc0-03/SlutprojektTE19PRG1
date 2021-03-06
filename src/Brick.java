import java.awt.*;

public class Brick {
    private Rectangle hitBox;
    private Color color;

    public Brick(int x, int y) {
        color = getRandomColor();
        hitBox = new Rectangle(x,y,60,20);
    }

    private Color getRandomColor() {
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

