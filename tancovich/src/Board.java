import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage background;
    private Timer timer;
    private List<Enemy> enemies;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 15;
    private Tank tank;

    private final int[][] pos = {
    		
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };

    public Board() {
    	
    	try {        	
        	background = ImageIO.read(getClass().getResourceAsStream("Resources/track.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        
        
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        tank = new Tank(ICRAFT_X, ICRAFT_Y);

        initEnemies();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initEnemies() {

        enemies = new ArrayList<>();

        for (int[] p : pos) {
            enemies.add(new Enemy(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawBackground(g);
        
        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void drawBackground(Graphics g)
    {
    	g.drawImage(background, 0, 0, null);
    }

    private void drawObjects(Graphics g) {

        if (tank.isVisible()) {
           g.drawImage(tank.getImage(), tank.getX(), tank.getY(),this);
        }

        List<Missile> ms = tank.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
            else {
            	g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("enemies left: " + enemies.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();
        updateMissiles();
        updateenemies();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (tank.isVisible()) {

            tank.move();
        }
    }

    private void updateMissiles() {

        List<Missile> ms = tank.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateenemies() {

        if (enemies.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy a = enemies.get(i);

            if (a.isVisible()) {
                a.move();
            } else {
                enemies.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = tank.getBounds();

        for (Enemy enemy : enemies) {

            Rectangle r2 = enemy.getBounds();

            if (r3.intersects(r2)) {

                tank.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> ms = tank.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Enemy enemy : enemies) {

                Rectangle r2 = enemy.getBounds();

                if (r1.intersects(r2)) {
                	
                	m.setVisible(false);
                    enemy.setVisible(false);
                    
                    enemy.destroyEnemy();
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            tank.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            tank.keyPressed(e);
        }
    }
}