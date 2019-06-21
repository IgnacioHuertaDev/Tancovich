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
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 30;
    
    private BufferedImage background;
    private boolean ingame;
    private List<Tank> tanks;
    private List<Enemy> enemies;
    private List<BoxLevel> boxes;
    private int lvl;
    private List<ProgressBar> bars;
    private Timer timer;
    
    private final int[][] boxesPositionLvlOne = {
    		{292, 135, 54, 338},
            {460, 133, 52, 340}
    };
    
    private final int[][] boxesPositionLvlTwo = {
    		{100, 40, 20, 50},
            {140, 40, 20, 50}
    };
    
    private final int[][] boxesPositionLvlThree = {
    		{100, 40, 20, 50},
            {140, 40, 20, 50}
    };
    
    private final int[][] tankPositions = {
    		
            {1, 40, 60},
            {2, 760, 540}
    };

    private final int[][] enemyPositions = {
    		
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

    public int getLvl() {
    	return lvl;
    }
    
    public void setLvl(int lvl) {
    	this.lvl = lvl;
    }
    
    public Board() {
    	
        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        ingame = true;
        
        if(lvl == 0) {
        	lvl = 1;
        }

        initTanks();
        initEnemies();
        initBars();
        initBoxes();

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void initTanks() {

        tanks = new ArrayList<>();

        for (int[] p : tankPositions) {
        	tanks.add(new Tank(p[0], p[1], p[2]));
        }
    }

    public void initEnemies() {

        enemies = new ArrayList<>();

        for (int[] p : enemyPositions) {
            enemies.add(new Enemy(p[0], p[1]));
        }
    }
    
    public void initBars() {
    	
    	bars = new ArrayList<>();
    	
    	for (Tank t : tanks) {
    		
    		ProgressBar p = new ProgressBar("Jugador");
            p.setValue(t.getHealth());
            p.setBounds(15, 15, 300, 15);
            this.add(p);
            this.setVisible(true);
            
    	}
    }
    
    public void initBoxes() {
    	
    	boxes = new ArrayList<>();
    	
    	switch (lvl) {
		case 1:
			for (int[] p : boxesPositionLvlOne) {
				boxes.add(new BoxLevel(p[0], p[1], p[2], p[3]));
	        }
			break;
		case 2:
			for (int[] p : boxesPositionLvlTwo) {
				boxes.add(new BoxLevel(p[0], p[1], p[2], p[3]));
	        }
			break;
			
		case 3:
			for (int[] p : boxesPositionLvlThree) {
				boxes.add(new BoxLevel(p[0], p[1], p[2], p[3]));
	        }
			break;

		default:			
			break;
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
                	
                    g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
                }
            }
            
            List<Mine> pm = tank.getMines();

            for (Mine mine : pm) {
            	if(mine.isVisible()) {
            		
                    g.drawImage(mine.getImage(), mine.getX(), mine.getY(), this);
            	}
            }
           /* g.setColor(Color.WHITE);
            g.drawString("R:" + tank.getR() + "   X:" + tank.getX() + "  Y:" + tank.getY(), 10, 30);*/
    	}        

        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
            	
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
            else {
            	
            	g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
        }
        
        for (BoxLevel boxLevel : boxes) {       	
                g.drawRect(boxLevel.getX(), boxLevel.getY(), boxLevel.getWidth(), boxLevel.getHeight());
        }

        g.setColor(Color.WHITE);
        g.drawString("Enemies left: " + enemies.size(), 5, 15);
        
        g.setColor(Color.WHITE);
        g.drawString("R:" + tanks.get(0).getR() + "   X:" + tanks.get(0).getX() + "  Y:" + tanks.get(0).getY(), 10, 30);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over, no servis para nada";
        Font small = new Font("Helvetica", Font.BOLD, 34);
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
        updateEnemies();
        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateTanks(Tank tank) {

        if (tank.isVisible()) {

            tank.update();
        }
    }

    private void updateMissiles(Tank tank) {

        List<Missile> ms = tank.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.update();
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
				mp.update();
			} 
			else {
				pm.remove(i);
			}
		}
	}
    
    private void updateEnemies() {

        if (enemies.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy a = enemies.get(i);

            if (a.isVisible()) {
                a.update();
            } else {
                enemies.remove(i);
            }
        }
    }

    public void checkCollisions(Tank tank) {

        Shape tankBound = tank.getShape();

        for (Enemy enemy : enemies) {

        	Shape enemyBound = enemy.getShape();

            if (Sprite.testIntersection(enemyBound,tankBound)) {

                tank.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> missiles = tank.getMissiles();

        for (Missile missile : missiles) {

            Shape missileBound = missile.getShape();

            for (Enemy enemy : enemies) {

                Shape enemyBound = enemy.getShape();

                if (Sprite.testIntersection(missileBound,enemyBound)) {
                	
                	missile.setVisible(false);
                    enemy.setVisible(false);                    
                    enemy.destroyEnemy();
                }
            }
        }
	       
        List<Mine> minas = tank.getMines();
	
	        
        for (Mine mina : minas) {
	
        	Shape minaBound = mina.getShape();
	
	        for (Enemy enemy : enemies) {
	
	        	Shape enemyBound = enemy.getShape();
	
	        	if (Sprite.testIntersection(minaBound,enemyBound)) {
	
	        		mina.setVisible(false);
	        		enemy.setVisible(false);
	        		enemy.destroyEnemy();
	        	}
	        }
        }
        
        for (BoxLevel boxLevel : boxes) {

        	Shape boxBound = boxLevel.getShape();

            if (Sprite.testIntersection(boxBound,tankBound)) {
                tank.setForward(0);
                tank.setVisible(false);
            }
        }
    }
    
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