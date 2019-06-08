import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Tank extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;

    public Tank(int x, int y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {

        missiles = new ArrayList<>();
        loadImage("tancovich/src/Resources/tankRed.png");
        getImageDimensions();   
    }
    
    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();


        if (key == KeyEvent.VK_SPACE) {
        	fire(e);
        }
        
        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            
            loadImage("tancovich/src/Resources/tankRedLeft.png");
            getImageDimensions();
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            
            loadImage("tancovich/src/Resources/tankRedRight.png");
            getImageDimensions();
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
            
            loadImage("tancovich/src/Resources/tankRedUp.png");
            getImageDimensions();
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
            
            loadImage("tancovich/src/Resources/tankRedDown.png");
            getImageDimensions();
        }
        
        if(key == (KeyEvent.VK_DOWN + KeyEvent.VK_RIGHT)) {
            
        	dy = -1;
            
            loadImage("tancovich/src/Resources/tank_blue.png");
            getImageDimensions();
        }
    }

    public void fire(KeyEvent e)
    {       
    	missiles.add(new Missile(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}