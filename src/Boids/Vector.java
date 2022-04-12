package Boids;

/**
 * Created 2022-04-11
 *
 * @author Magnus Silverdal
 */
public class Vector {
    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector v2) {
        this.x += v2.x;
        this.y += v2.y;
    }

    public void sub(Vector v2) {
        this.x -= v2.x;
        this.y -= v2.y;
    }

    public void divide(double x) {
        if (x != 0) {
            this.x /= x;
            this.y /= x;
        }
    }

    public double dist(Vector v2) {
        double deltax = this.x-v2.x;
        double deltay = this.y-v2.y;
        return Math.sqrt(deltax*deltax+deltay*deltay);
    }

    public double length() {
        return dist(new Vector(0,0));
    }

    public void normalize() {
        double length = length();
        this.x /= length;
        this.y /= length;
    }

    public void multiply(double x) {
        this.x *= x;
        this.y *= x;
    }

    public void limit(double maxLength) {
        if (length() > maxLength) {
            normalize();
            multiply(maxLength);
        }
    }
}
