import java.awt.*;

public class Ball {
        public double ballXv;
        public double ballYv;
        public double X,Y;

        public Ball(int x, int y, double ballxv, double ballyv) {
            X = x;
            Y = y;
            ballXv = ballxv;
            ballYv = ballyv;
        }

        public Rectangle getRect() {
            return new Rectangle((int) X, (int) Y, 10, 10);
        }

        public double getX() {
            return X;
        }

        public double getY() {
            return Y;
        }

        public Double getXv() {
            return ballXv;
        }

        public double getYv(){
            return ballYv;
        }
    }
