import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class Board extends JPanel  implements ActionListener{

	private static final long serialVersionUID = 1L;
    public static int BOARD_WIDTH = 800;
    public static int BOARD_HEIGHT = 600;
    private final int DELAY = 40;
    
    private int lvl = 0;
    private BufferedImage background;
    
    private List<Tank> tanks;
    private List<Box> boxes;
    private List<ProgressBar> bars;
    private List<Hearts> hearts;
    //private List<Enemy> enemies;
    
    private Timer timer;
    private Menu menu;
    private boolean enterControl = false;
    private boolean escapeControl = false;
    
    private final int[][] tankPositions = {
    		
            {1, 40, 60},
            {2, 720, 480}
    };
    
    private final int[][] boxesPositionBorder = {
    		{0, -1, 800, 1, 0}, //ARRIBA
    		{800, 0, 1, 600, 0}, //DERECHA
    		{0, 600, 800, 1, 0}, //ABAJO
    		{-1, 0, 1, 600, 0} //IZQUIERDA
    };
    
    private final int[][] boxesPositionLvlOne = {
    		{293, 136, 55, 338, 0},
            {460, 133, 52, 340, 0}    		
    };
    
    private final int[][] boxesPositionLvlTwo = {
    		{292, 0, 250, 118, 1},
            {292, 255, 250, 112, 1},
    		{292, 503, 250, 92, 1}
    };
    
    private final int[][] heartsPositions = {
    		
            {80, 20},
            {420, 20}
    };
    
    
    public static enum STATE {
    	STARTMENU,
    	MAINMENU,
    	HELP,
    	CREDITS,
    	CHOOSEMAP,
    	GAME,
    	PAUSE,
    	GAMEOVER
    };
    
    public STATE State = STATE.STARTMENU;
    private int render;
    
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

    public int getLvl() {
    	return lvl;
    }
    
    public void setLvl(int lvl) {
    	this.lvl = lvl;
    }
    
    public List<Hearts> getHearts() {
		return hearts;
	}
    
    public void setRender(int render)
    {
    	this.render = render;
    }
    
    public STATE getState() {
		return State;
	}

	public void setState(STATE state) {
		State = state;
	}

	private void initBoard() {

        setFocusable(true);       
        setPreferredSize(new Dimension(Board.BOARD_WIDTH, Board.BOARD_HEIGHT));
        MouseInput mouseListener = new MouseInput(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        menu = new Menu(this);
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void initGame() {
    	
    	if(getLvl() == 0) {
    		setLvl(1);
        }

        initTanks();
        //initEnemies();
        hideBars();
        initBars();
        initHearts();
        initBoxes();
    }
    
    public void initTanks() {

        tanks = new ArrayList<>();

        for (int[] p : tankPositions) {
        	tanks.add(new Tank(p[0], p[1], p[2]));
        }
    }

   /* public void initEnemies() {

        enemies = new ArrayList<>();

        for (int[] p : enemyPositions) {
            enemies.add(new Enemy(p[0], p[1]));
        }
    }*/
    
    public void initBars() {
    	
    	bars = new ArrayList<>();
    	
    	for (Tank tank : tanks) {
    		ProgressBar bar = new ProgressBar(tank.getId());
            bar.setValue(tank.getHealth());      
            bar.setBounds(((tank.getId()-1)*340)+100, 20, 300, 20);
            bar.setVisible(true);
            bars.add(bar);
            this.add(bar);
    	}
    }
    
    public void initHearts() {
    	
    	hearts = new ArrayList<>();
    	
    	for (Tank tank : tanks) {
    		
	    	hearts.add(new Hearts (heartsPositions[tank.getId()-1][0], heartsPositions[tank.getId()-1][1], tank.getId()));
    	}
    }
    
    public void hideBars() {
    	
    	if(bars != null)
    	{
    		for (ProgressBar bar : bars) {
        		
        		bar.setVisible(false);
        	}
    	}    	
    }
    
    public void initBoxes() {
    	
    	boxes = new ArrayList<>();
    	
    	for (int[] p : boxesPositionBorder) {
			Box newBox = new Box(p[0], p[1], p[2], p[3]);
			if(p[4] == 1) newBox.setMissileInside(true);
			boxes.add(newBox);			
        }
    	
    	switch (lvl) {
		case 1:
			for (int[] p : boxesPositionLvlOne) {
				Box newBox = new Box(p[0], p[1], p[2], p[3]);
				if(p[4] == 1) newBox.setMissileInside(true);
				boxes.add(newBox);				
	        }
			break;
		case 2:
			for (int[] p : boxesPositionLvlTwo) {
				Box newBox = new Box(p[0], p[1], p[2], p[3]);
				if(p[4] == 1) newBox.setMissileInside(true);
				boxes.add(newBox);
	        }
			break;

		default:			
			break;
		}
    }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(State != STATE.GAME && State != STATE.PAUSE && State != STATE.GAMEOVER) hideBars();

		if (State == STATE.STARTMENU) {
			menu.render(g,0);
			if(Keyboard.keydown[10] && !enterControl)
			{
				enterControl = true;
				render = 1;
				State = STATE.MAINMENU;
			}
			if(Keyboard.keydown[27] && !escapeControl)
			{
				System.exit(1);
			}
		}
		if(State == STATE.MAINMENU) {
			menu.render(g,render);
			if(Keyboard.keydown[10] && !enterControl)
			{
				enterControl = true;
				State = STATE.CHOOSEMAP;
			}
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				State = STATE.STARTMENU;	
			}
		}
		if (State == STATE.HELP) {
			menu.render(g,render);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				render = 1;
				State = STATE.MAINMENU;	
			}
		}
		if (State == STATE.CREDITS) {
			menu.render(g,render);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				render = 1;
				State = STATE.MAINMENU;
			}
		}
		if (State == STATE.CHOOSEMAP) {
			menu.render(g,render);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				render = 1;
				State = STATE.MAINMENU;
			}
		}
		if(State == STATE.GAME) {
			drawBackground(g);
			drawObjects(g);
			Toolkit.getDefaultToolkit().sync();
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				render = 8;
				State = STATE.PAUSE;				
			}
		}
		if(State == STATE.PAUSE) {
			drawBackground(g);
			drawObjects(g);
			Toolkit.getDefaultToolkit().sync();
			menu.render(g,render);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				State = STATE.GAME;				
			}
		}
		if(State == STATE.GAMEOVER) {
			drawBackground(g);
			drawObjects(g);
			Toolkit.getDefaultToolkit().sync();
			menu.render(g,render);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				State = STATE.MAINMENU;				
			}
		}
		
		if(!Keyboard.keydown[10])
		{
			enterControl = false;
		}
		if(!Keyboard.keydown[27])
		{
			escapeControl = false;
		}
	}
    
    private void drawBackground(Graphics g)
    {
    	String mapImage = "";
    	if(lvl == 1) mapImage = "Resources/track.png";
    	else if(lvl == 2) mapImage = "Resources/track2.png";
    	
    	try {
        	background = ImageIO.read(getClass().getResourceAsStream(mapImage));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	g.drawImage(background, 0, 0, null);
    }

    private void drawObjects(Graphics g) {

    	for (Tank tank : tanks)
    	{
    		//Hago visible primero la mina que el tanque para que esta no se dibuje encima de este
            List<Mine> pm = tank.getMines();

            for (Mine mine : pm) {
            	if(mine.isVisible()) {
            		
                    g.drawImage(mine.getImage(), mine.getX(), mine.getY(), this);
                    if(tank.isMineControl() == true)
                    g.drawString( tank.getMinesNumber() + " mines left.", tank.getX()-12, tank.getY()-5);
            	}
            }
    		            
    		if (tank.isVisible()) {
            	
            	g.drawImage(tank.getImage(), tank.getX(), tank.getY(), this);
            }
    		
    		List<Hearts> ht = getHearts();
    		
    		for (Hearts hearts : ht) {
                if (hearts.isVisible()) {
                	
                    g.drawImage(hearts.getImage(), hearts.getX(), hearts.getY(), this);
                }
    		}
    		
    		List<Missile> ms = tank.getMissiles();

            for (Missile missile : ms) {
                if (missile.isVisible()) {
                	
                    g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
                    if(tank.isFireControl() == true)
                    g.drawString( tank.getMissileNumber() + " missiles left.", tank.getX()-12, tank.getY()-5);
                    if(!tank.CanFire()) g.drawString("No missiles left.", tank.getX()-12, tank.getY()-5);
                }
            }   
            
    	}       

        /*for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
            	
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
            else {
            	
            	g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
        }*/
        
    	Color color = new Color(0,0,0,0);
        for (Box box : boxes) {        	
        	g.setColor(color);
        	g.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());        	
        }

        g.setColor(Color.WHITE);
        //g.drawString("R:" + tanks.get(0).getR() + "   X:" + tanks.get(0).getX() + "  Y:" + tanks.get(0).getY(), 10, 30);
        //g.drawString("Enemies left: " + enemies.size(), 5, 15); 
        Font small = new Font("Helvetica", Font.BOLD, 15);
        g.drawString(""+tanks.get(0).getLifes(), 85, 33);
        g.drawString(""+tanks.get(1).getLifes(), 425, 33);
        g.setFont(small);
        g.drawString("Player 1:", 180, 16);
		g.drawString("Player 2:", 550, 16);
        
    }

    @Override  
    public void actionPerformed(ActionEvent e) {
    	
    	if (State == STATE.GAME) {
    		int tankCounter = 0;
    		for (Tank tank: tanks)
	    	{
    			if(tank.isVisible()) tankCounter++;
	        	updateTanks(tank);
		        updateMissiles(tank);
		        updateHearts(tank);
		        updateMines(tank);
		        checkCollisions(tank);
	    	}
    		if(tankCounter < 2)
    		{
    			render = 11;
    			State = STATE.GAMEOVER;
    		}
    		//updateEnemies();
    		repaint();
		}    	
    }
    
    private void updateTanks(Tank tank) {

        if (tank.isVisible()) {

            tank.update();
            if(tank.getMissileNumber() <= 1) tank.setTimer(tank.getTimer()+1);
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
    
    private void updateHearts (Tank tank) {
        
    	List<Hearts> ht = getHearts();
        for (int i = 0; i < ht.size(); i++) {

        	Hearts h = ht.get(i);

            if (h.isVisible()) {
            	h.setNumberLifes(tank.getLifes());
            	h.update();
            } else {
                ht.remove(i);
            }
        } 
    }
    
    private void updateMines(Tank tank) {
    	
    	List <Mine> pm = tank.getMines();
		for (int i = 0; i < pm.size(); i++) {
			Mine mp = pm.get(i);
			if (mp.isVisible()) {
				mp.update();
		    	mp.setTimer(mp.getTimer()+1);
			} 
			else {
				pm.remove(i);
			}
		}
	}

//    /*private void updateEnemies() {
//
//        if (enemies.isEmpty()) {
//
//            ingame = false;
//            return;
//        }
//
//        for (int i = 0; i < enemies.size(); i++) {
//
//            Enemy a = enemies.get(i);
//
//            if (a.isVisible()) {
//                a.update();
//            } else {
//                enemies.remove(i);
//            }
//        }
//    }*/
//
    public void checkTanks()
    {
    	
    }
    
    public void checkCollisions(Tank tank) {

    	if(tank.getCollisionedBox() != null) tank.restoreMovement(tank.getCollisionedBox());
    	
    	Shape tankBound = tank.getShape();

        /*for (Enemy enemy : enemies) {

        	Shape enemyBound = enemy.getShape();

            if (Sprite.testIntersection(enemyBound,tankBound)) {

                tank.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }*/

        List<Missile> missiles = tank.getMissiles();

        for (Missile missile : missiles) {
        	
            Shape missileBound = missile.getShape();

            /*for (Enemy enemy : enemies) {

                Shape enemyBound = enemy.getShape();

                if (Sprite.testIntersection(missileBound,enemyBound)) {
                	
                	missile.setVisible(false);                   
                    enemy.destroyEnemy();
                }
            }*/
            
            for (Tank tankObjective : tanks) {

                Shape tankeBound = tankObjective.getShape();

                if (Sprite.testIntersection(missileBound, tankeBound)) {
                	if((missile.getBounce() >= 1 && missile.getShooterId() == tank.getId()) || (missile.getShooterId() != tankObjective.getId())) {
                		if(tankObjective.visible) {
                		tankObjective.setHealth(tankObjective.getHealth() - missile.getDamage());   
                		for (int i = 0; i < bars.size(); i++) {
                			if(bars.get(i).getTankId() == tankObjective.getId()) {
                				bars.get(i).setValue(tankObjective.getHealth());
                			}
                		}
                		missile.setVisible(false); 
                		}
                	}                	           	                	                 
                }
            }
        }
	       
        List<Mine> minas = tank.getMines();
	
	        
        for (Mine mine : minas) {
	
        	Shape mineBound = mine.getShape();
            
        	for (Tank tankObjective : tanks) {
                
        		Shape tankeBound = tankObjective.getShape();
	        	
                if (Sprite.testIntersection(mineBound,tankeBound)) {
                	if(mine.getShooterId() != tankObjective.getId() || mine.getShooterId() == tank.getId() && mine.isAbove()) {
                		if(tankObjective.visible) {
                		tankObjective.setHealth(tankObjective.getHealth() - mine.getDamage());   
                		for (int i = 0; i < bars.size(); i++) {
                			if(bars.get(i).getTankId() == tankObjective.getId()) {
                				bars.get(i).setValue(tankObjective.getHealth());
                			}
                		}
                		mine.setExplode(true);
                		}
                	}
	        	}
	        }
        	for (Missile m : missiles) {
        		Shape missileBound = m.getShape();
        		if(Sprite.testIntersection(mineBound, missileBound)) {
        			mine.setExplode(true);
        			m.setVisible(false);
        		}
        	}
        }
        
        
        for (Box box : boxes) {

        	Shape boxBound = box.getShape();
        	
        	if (Sprite.testIntersection(boxBound,tankBound))
        	{
        		tank.banMovement(box);
        	}
	 
            for (Missile missile : missiles) {
           	
            	Shape missileBound = missile.getShape();
            	
            	if(!box.isMissileInside() && Sprite.testIntersection(boxBound, missileBound)) {
            		
            		//Superior e inferior
            		if((missile.getY() < box.getY() && isBetween(missile.getX(),box.getX(),box.getX()+box.getWidth()-missile.getWidth())) || 
            		(missile.getY() > box.getY() + box.getHeight() - missile.getHeight() && isBetween(missile.getX(),box.getX(),box.getX()+box.getWidth()-missile.getWidth())))
            		{
            			missile.setR(180-missile.getR());
            		}            		
            		//Izquierdo y derecho
            		if(missile.getX() < box.getX() && isBetween(missile.getY(),box.getY(),box.getY()+box.getHeight()-missile.getHeight()) || missile.getX() > box.getX() + box.getWidth() - missile.getWidth() && isBetween(missile.getY(),box.getY(),box.getY()+box.getHeight()-missile.getHeight()))
            		{
            			missile.setR(missile.getR()*-1);
            		}
            		
            		//Agrego rebote
            		missile.setBounce(missile.getBounce()+1);
            	}
            }
        }
    }
    
    public static boolean isBetween(int x, int lower, int upper) {
    	return lower <= x && x <= upper;
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
