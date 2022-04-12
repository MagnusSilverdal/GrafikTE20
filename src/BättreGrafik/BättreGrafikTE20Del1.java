package BättreGrafik;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Enkel grafik. Skapa en Canvas men skriv en egen metod för att anropa ritandet. För att kunna styra fps och ups
 * lägger vi den i egen tråd
 *
 * Created 2022-04-11
 *
 * @author Magnus Silverdal
 */

/**
 * Vi utökar klassen Canvas med vår bild och implementerar runnable så att den kan köras som en egen tråd
 */
public class BättreGrafikTE20Del1 extends Canvas implements Runnable {
    // Variabler för tråden
    private Thread thread;
    int fps = 30;
    private boolean isRunning;
    // Skapa en buffrad grafik så att vi kan rita bilder i förväg, bättre än dbg från tidigare
    private BufferStrategy bs;
    // Storleken på bilden
    private final int height = 600;
    private final int width = 800;
    // Variabler gör det lättare att placera
    int treeX = 200;
    int treeY = 200;
    int sunX = 700;
    int sunY = 100;
    int cloudX = 100;
    int cloudY = 50;

    /**
     * Skapa ett fönster och lägg in grafiken i det.
     */
    public BättreGrafikTE20Del1() {
        JFrame frame = new JFrame("Del 4");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Börja med animationen avslagen
        isRunning = false;
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
                lastTime = now;
            }
            paint();
        }
        stop();
    }

    /**
     * Nu gör vi en egen paint. Skapa en bufferStrategy så att vi får flera skärmar att jobba på, Java sköter det åt oss
     */
    public void paint() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Om vi inte suddar allt ritar vi över det som redan fanns. Ibland kan det vara bättre att bara sudda en bit
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        draw(g);
        // Det här byter skärm
        g.dispose();
        bs.show();
    }

    /**
     * Öka x-koordinaten med 5 i varje uppdatering och om den kommer ut mot högerkanten så flytta den längst till
     * vänster
     */
    private void update() {
        cloudX += 5;
        if (cloudX > 750)
            cloudX = 0;
    }

    /**
     * Rita ut alla saker. Ordningen är viktig eftersom vi kan rita saker på andra saker.
     * @param g grafiken
     */
    private void draw(Graphics g) {
        drawHeaven(g);
        drawMountains(g, 150);
        drawGrass(g);
        drawSun(g, sunX, sunY);
        drawCloud(g, cloudX,cloudY);
        drawTree(g, treeX,treeY);
        drawTree(g, treeX+30,treeY);
        drawTree(g, treeX+60,treeY);
    }

    /**
     * Rita ett bulligt moln
     * @param g grafiken
     * @param cloudX koordinat
     * @param cloudY koordinat
     */
    private void drawCloud(Graphics g, int cloudX, int cloudY) {
        g.setColor(Color.white);
        g.fillOval(cloudX,cloudY,30,30);
        g.fillOval(cloudX+20,cloudY-10,30,30+10);
        g.fillOval(cloudX+40,cloudY,30,30);
    }

    /**
     * En sol...
     * @param g grafiken
     * @param sunX koordinat
     * @param sunY koordinat
     */
    private void drawSun(Graphics g, int sunX, int sunY) {
        g.setColor(Color.yellow);
        g.fillOval(sunX,sunY,40,40);
    }

    /**
     * Rita gräs i nederkant av bilden
     * @param g grafiken
     */
    private void drawGrass(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0,250,width,height);
    }

    /**
     * Rita en bergskedja definierad som en polygon (en lista av x och y-koordinater)
     * @param g grafiken
     * @param y koordinat för höjden
     */
    private void drawMountains(Graphics g, int y) {
        g.setColor(Color.darkGray);
        int[] xs = {0,200,240,470,550,650,800};
        int[] ys = {y+100,y,y+50,y,y+50,y,y+100};
        Polygon shape = new Polygon(xs,ys,7);
        g.fillPolygon(shape);
    }

    /**
     * Rita himlen över hela bilden bilden
     * @param g grafiken
     */
    private void drawHeaven(Graphics g) {
        g.setColor(new Color(0x22FFDD));
        g.fillRect(0,0,width,height);
    }

    /**
     * Här är instruktionerna för att rita ett träd.
     * @param g Den Graphics som det ska ritas på
     * @param x En koordinat
     * @param y En annan koordinat
     */
    private void drawTree(Graphics g, int x, int y) {
        g.setColor(new Color(0x00DD33));
        g.fillRect(x,y,20,40);
        g.setColor(Color.white);
        g.fillRect(x+8,y+40,4,20);
    }

    /**
     * Nu kan vi starta vårt program
     * Skapa först en JFrame och en canvas, starta sedan tråden som sköter animationen.
     */
    public static void main(String[] args) {
        BättreGrafikTE20Del1 exempel = new BättreGrafikTE20Del1();
        exempel.start();
    }
}

