import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Timer timer;
    private boolean ingame;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 30;
    private BufferedImage background;
    private List<Tank> tanks;
    //private List<Enemy> enemies;
    
private final int[][] tankPositions = {
    		
            {1, 40, 60}, 
            {2, 760, 540}
    };

    /*private final int[][] enemyPositions = {
    		
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };*/

    public Board() {
    	
        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        initTanks();
        //initEnemies();

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void initTanks() {

        tanks = new ArrayList<>();

        for (int[] p : tankPositions) {
        	tanks.add(new Tank(p[0], p[1], p[2]));
        }
    }

    /*public void initEnemies() {

        enemies = new ArrayList<>();

        for (int[] p : enemyPositions) {
            enemies.add(new Enemy(p[0], p[1]));
        }
    }*/

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
    	try {
        	background = ImageIO.read(getClass().getResourceAsStream("Resources/track.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	g.drawImage(background, 0, 0, null);
    }

    private void drawObjects(Graphics g) {
    	
    	for (Tank tank : tanks)
    	{
    		if (tank.isVisible()) {
            	
            	g.drawImage(tank.getImage(), tank.getX(), tank.getY(), this);
            }
    		
    		List<Missile> ms = tank.getMissiles();

            for (Missile missile : ms) {
                if (missile.isVisible()) {
                	if(ms.size() < 5) {
                		g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
                	}                    
                }
            }
            
            List<Mine> pm = tank.getMines();

            for (Mine mine : pm) {
            	if(mine.isVisible()) {
            		
                    g.drawImage(mine.getImage(), mine.getX(), mine.getY(), this);
            	}
            }
    	}        

       /* for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
            	
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
            else {
            	
            	g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Enemies left: " + enemies.size(), 5, 15);*/
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();
        for (Tank tank : tanks)
    	{
	        updateTanks(tank);
	        updateMissiles(tank);
	        updateMines(tank);
	        checkCollisions(tank);
    	}
        //updateEnemies();
        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateTanks(Tank tank) {

        if (tank.isVisible()) {

            tank.move();
        }
    }

    private void updateMissiles(Tank tank) {

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
    
    private void updateMines(Tank tank) {
    	
    	List <Mine> pm = tank.getMines();
		for (int i = 0; i < pm.size(); i++) {
			Mine mp = pm.get(i);
	
			if (mp.isVisible()) {
				mp.plantMine();
			} 
			else {
				pm.remove(i);
			}
		}
	}

    public void checkCollisions(Tank tank) {

        Shape r3 = tank.getShape();

        /*for (Enemy enemy : enemies) {

        	Shape r2 = enemy.getShape();

            if (Sprite.testIntersection(r2,r3)) {

                tank.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }*/

        List<Missile> ms = tank.getMissiles();

        for (Missile m : ms) {

            Shape r1 = m.getShape();

            for (Tank enemyTank : tanks) {

                Shape r2 = enemyTank.getShape();
                if(m.getShootedBy() != enemyTank.getId()) {
                	if (Sprite.testIntersection(r1,r2)) {
                    	
                    	m.setVisible(false);
                        enemyTank.setVisible(false);
                        
                        enemyTank.destroyTank();
                    }
                }
                
            }
        }
	       
        List<Mine> pm = tank.getMines();
	
	        
        for (Mine mp : pm) {
	
        	Shape r1 = mp.getShape();
	
	        /*for (Enemy enemy : enemies) {
	
	        	Shape r2 = enemy.getShape();
	
	        	if (Sprite.testIntersection(r1,r2)) {
	
	        		mp.setVisible(false);
	        		enemy.setVisible(false);
	        		enemy.destroyEnemy();
	        	}
	        }*/
        }
    }
    
    /*private void updateEnemies() {

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
    }*/
    
    @Override
    protected void processKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            Keyboard.keydown[e.getKeyCode()] = true;
        }
        else if (e.getID() == KeyEvent.KEY_RELEASED) {
            Keyboard.keydown[e.getKeyCode()] = false;
        }
    }
}