package Boids;

/**
 * Created 2022-04-11
 *
 * @author Magnus Silverdal
 */
public class Boid {
    Vector position;
    Vector velocity;
    Vector acceleration;
    double maxVelocity = 4;
    double maxAcceleration = 0.2;
    int width;
    int height;

    public Boid(int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector(Math.random()*width,Math.random()*height);
        double x = (Math.random()*2+1);
        double y = (Math.random()*2+1);
        if (Math.random() > 0.5)
            x = -x;
        if (Math.random() > 0.5)
            y = -y;

        velocity = new Vector(x,y);
//        velocity.normalize();
//        velocity.multiply(maxVelocity);
        acceleration = new Vector(0,0);
    }

    public void update(Boid[] boids) {
        edge(width,height);
        align(boids);
        cohesion(boids);
        //separation(boids);
        acceleration.limit(maxAcceleration);
        velocity.add(acceleration);
        velocity.limit(maxVelocity);
        position.add(velocity);
        acceleration.multiply(0);
    }

    private void edge(int width, int height) {
        if (this.position.x < 0)
            this.position.x = width;
        if (this.position.x > width)
            this.position.x = 0;
        if (this.position.y < 0)
            this.position.y = height;
        if (this.position.y > height)
            this.position.y = 0;
    }

    public void align(Boid[] boids) {
        double perception = 100;
        int numBoids = 0;
        Vector avgVel = new Vector(0,0);
        for (Boid b : boids) {
            if (this != b && this.dist(b) < perception) {
                numBoids++;
                avgVel.add(b.velocity);
            }
        }
        if (numBoids > 0) {
            avgVel.divide(numBoids);
            avgVel.sub(this.velocity);
        }
        acceleration.add(avgVel);
    }

    public void cohesion(Boid[] boids) {
        double perception = 100;
        int numBoids = 0;
        Vector avgPos = new Vector(0,0);
        for (Boid b : boids) {
            if (this != b && this.dist(b) < perception) {
                numBoids++;
                avgPos.add(b.position);
            }
        }
        if (numBoids > 0) {
            avgPos.divide(numBoids);
            avgPos.sub(this.position);
        }
        acceleration.add(avgPos);
    }

    public void separation(Boid[] boids) {
        double perception = 100;
        int numBoids = 0;
        Vector avgPos = new Vector(0,0);
        for (Boid b : boids) {
            if (this != b && this.dist(b) < perception) {
                Vector diff = new Vector(position.x-b.position.x,position.y-b.position.y);
                double dist = this.dist(b);
                diff.divide(dist);
                numBoids++;
                avgPos.add(diff);
            }
        }
        if (numBoids > 0) {
            avgPos.divide(numBoids);
            avgPos.sub(this.position);
            avgPos.multiply(-1);
        }
        acceleration.add(avgPos);
    }


    private double dist(Boid b) {
        return this.position.dist(b.position);
    }
}
