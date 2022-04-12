import javax.swing.*;
import java.awt.*;

/**
 * Enkel grafik. Skapa en Canvas och skriv om paint-metoden så att den ritar det du vill.
 * Alla metoder på Graphics g är värda att utforska...
 *
 * Created 2022-04-11
 *
 * @author Magnus Silverdal
 */

/**
 * Vi utökar klassen Canvas med vår bild
 */
public class GrafikTE20Del4 extends Canvas {
    // Skapa en bild i minnet som vi ritar till
    Graphics dbg;
    Image image;
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
    public GrafikTE20Del4() {
        JFrame frame = new JFrame("Del 4");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Det är den här metoden vi kapar. Istället för at bara rita en ljusgrå ryta i fönstret stoppar vi in vad vi vill.
     * @param g är en referens till grafiken i bilden. Med hjälp av den kan vi rita geometriska primitiver (grundformer)
     * Alla förändringar i bilden ligger i en update-metod
     * Nu ritar vi på den andra skärmen, som bara finns i minnet. Då försvinner nästan flimret men vi kan fortfarande
     * inte utnyttja tiden till något vettigt
     */
    public void paint(Graphics g) {
        if (image == null) {
            // skapa en andra skärm i minnet som vi kan rita till
            image = createImage(width, height);
            if (image == null) {
                System.out.println("image is still null!");
                return;
            } else {
                dbg = image.getGraphics();
            }
        }
        update();
        draw(dbg);
        // Kopiera innehållet i minnet ut på den fysiska skärmen
        g.drawImage(image, 0, 0, null);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
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
     * @param y kordinat för höjden
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
     * När vi kör det så skapas en ny instans av en GrafikTE20Del1, genom att konstrukltorn körs. Den skapar ett
     * fönster och lägger in sig själv där. Sedan anropas paint av systemet (det sker en notify() när vi gör JFramen
     * synlig) och våra träd visas.
     */
    public static void main(String[] args) {
        GrafikTE20Del4 exempel = new GrafikTE20Del4();
    }
}
