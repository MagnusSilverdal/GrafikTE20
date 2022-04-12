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
public class GrafikTE20Del1 extends Canvas {
    // Storleken på bilden
    private final int height = 600;
    private final int width = 800;
    // Variabler gör det lättare att placera
    int treeX = 200;
    int treeY = 200;

    /**
     * Skapa ett fönster och lägg in grafiken i det.
     */
    public GrafikTE20Del1() {
        JFrame frame = new JFrame("Del 1");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Det är den här metoden vi kapar. Istället för at bara rita en ljusgrå ryta i fönstret stoppar vi in vad vi vill.
     * @param g är en referens till grafiken i bilden. Med hjälp av den kan vi rita geometriska primitiver (grundformer)
     */
    public void paint(Graphics g) {
        drawTree(g, treeX,treeY);
        drawTree(g, treeX+30,treeY);
        drawTree(g, treeX+60,treeY);
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
        g.setColor(Color.black);
        g.drawRect(x+8,y+40,4,20);
    }

    /**
     * Nu kan vi starta vårt program
     * När vi kör det så skapas en ny instans av en GrafikTE20Del1, genom att konstrukltorn körs. Den skapar ett
     * fönster och lägger in sig själv där. Sedan anropas paint av systemet (det sker en notify() när vi gör JFramen
     * synlig) och våra träd visas.
     */
    public static void main(String[] args) {
        GrafikTE20Del1 exempel = new GrafikTE20Del1();
    }
}

