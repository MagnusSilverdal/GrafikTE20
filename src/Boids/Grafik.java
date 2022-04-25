package Boids;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created 2022-04-11
 *
 * @author Magnus Silverdal
 */
public class Grafik extends Canvas implements Runnable{
    private Thread thread;
    int fps = 1;
    private boolean isRunning;
    private BufferStrategy bs;
    private final int height = 1000;
    private final int width = 1600;
    // En massa Boids...
    private Boid[] boids;

    public Grafik() {
        JFrame frame = new JFrame("Boids...");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        isRunning = false;
        boids = new Boid[100];
        for (int i = 0 ; i < boids.length ; i++)
            boids[i] = new Boid(width,height);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0/fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now-lastTime > deltaT) {
                update();
                paint();
                lastTime = now;
            }
        }
        stop();
    }

    public void paint() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        draw(g);
        g.dispose();
        bs.show();
    }

    private void update() {
        for (int i = 0 ; i < boids.length ; i++)
            boids[i].update(boids);
    }

    private void draw(Graphics g) {
        for (int i = 0 ; i < boids.length ; i++)
            drawBoid(g, boids[i].position, boids[i].velocity);
    }

    /**
     * Rita en "fågel"
     * @param g grafiken
     * @param pos koordinat
     * @param vel hastighet
     */
    private void drawBoid(Graphics g, Vector pos,Vector vel) {
        g.setColor(Color.black);
        g.fillOval((int)pos.x, (int)pos.y,20,20);
    }

    public static void main(String[] args) {
        Grafik exempel = new Grafik();
        exempel.start();
    }
}

