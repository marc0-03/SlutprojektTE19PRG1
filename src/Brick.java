import org.w3c.dom.css.Rect;

import java.awt.*;

public class Brick {
    private Rectangle hitBox;
    private int color;

    public Brick(int x, int y) {
        color = getRandomColor();
        hitBox = new Rectangle(x,y,60,20);
    }

    private int getRandomColor() {
        return 0;
    }

    public int getX() {
        return hitBox.x;
    }

    public int getY() {
        return hitBox.y;
    }



}

