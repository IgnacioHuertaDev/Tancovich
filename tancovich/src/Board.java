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
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    private final int DELAY = 40;
    
    private int lvl = 0;
    private BufferedImage background;
    
    private List<Tank> tanks;
    private List<Box> boxes;
    private List<ProgressBar> bars;
    private List<Heart> hearts;
    //private List<Enemy> enemies;
    
    private Timer timer;
    private Menu menu;
    private boolean enterControl = false;
    private boolean escapeControl = false;    
    
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
    
    public List<ProgressBar> getBars() {
		return bars;
	}

	public void setBars(List<ProgressBar> bars) {
		this.bars = bars;
	}

	public List<Heart> getHearts() {
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

        for (int i = 0; i < Tank.tankPositions.length; i++) {
        	
        	tanks.add(new Tank(i+1, Tank.tankPositions[i][0], Tank.tankPositions[i][1], Tank.tankPositions[i][2]));
        }
    }

	/*public void initEnemies() {

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
    		
	    	hearts.add(new Heart (Heart.heartsPositions[tank.getId()-1][0], Heart.heartsPositions[tank.getId()-1][1], tank.getId()));
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
    	
    	for (int[] p : Box.boxesPositionBorder) {
    		
			Box newBox = new Box(p[0], p[1], p[2], p[3]);
			if(p[4] == 1) newBox.setMissileInside(true);
			boxes.add(newBox);			
        }
    	
    	switch (lvl) {
			case 1:
				for (int[] p : Box.boxesPositionLvlOne) {
					
					Box newBox = new Box(p[0], p[1], p[2], p[3]);
					if(p[4] == 1) newBox.setMissileInside(true);
					boxes.add(newBox);				
		        }
				break;
			case 2:
				for (int[] p : Box.boxesPositionLvlTwo) {
					
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
                    if(tank.getMineControl() > 0) g.drawString(tank.getMinesNumber() + " mines left.", tank.getX()-12, tank.getY()-5);
            	}
            }
            
            List<Missile> ms = tank.getMissiles();

            for (Missile missile : ms) {
                if (missile.isVisible()) {
                	
                    g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
                    if(tank.getMissileControl() > 0) g.drawString( tank.getMissileNumber() + " missiles left.", tank.getX()-12, tank.getY()-5);
                    if(!tank.canFire()) g.drawString("No missiles left.", tank.getX()-12, tank.getY()-5);
                }
            }
    		            
    		if (tank.isVisible()) {
            	
            	g.drawImage(tank.getImage(), tank.getX(), tank.getY(), this);
            }
    		
    		List<Heart> ht = getHearts();
    		
    		for (Heart hearts : ht) {
    			
                if (hearts.isVisible()) {
                	
                    g.drawImage(hearts.getImage(), hearts.getX(), hearts.getY(), this);
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
    			if(tank.getLifes() > 0) tankCounter++;
    			updateTanks(tank);
		        updateMissiles(tank);
		        updateBars(tank);		        
		        updateHearts(tank);
		        updateMines(tank);
		        checkCollisions(tank);
	    	}
    		if(tankCounter < 2) menu.drawGameOver();    		
    		//updateEnemies();
    		repaint();
		}    	
    }
    
    private void updateTanks(Tank tank) {

    	tank.update();
    	if(tank.getMissileNumber() < 1) tank.setTimer(tank.getTimer()+1);
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
    
    private void updateBars (Tank tank) {
        
    	List<ProgressBar> bs = getBars();
    	
        for (int i = 0; i < bs.size(); i++) {

        	ProgressBar b = bs.get(i);
   			if(b.getTankId() == tank.getId()) b.setValue(tank.getHealth());
        } 
    }
    
    private void updateHearts (Tank tank) {
        
    	List<Heart> ht = getHearts();
    	
        for (int i = 0; i < ht.size(); i++) {

        	Heart h = ht.get(i);

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
    
    public void checkCollisions(Tank tank) {

    	if(tank.getCollisionedBox() != null) Physics.restoreTankMovement(tank, tank.getCollisionedBox());
    	
    	Shape tankBound = tank.getShape();

        /*for (Enemy enemy : enemies) {

        	Shape enemyBound = enemy.getShape();

            if (Physics.testIntersection(enemyBound,tankBound)) {

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

                if (Physics.testIntersection(missileBound,enemyBound)) {
                	
                	missile.setVisible(false);                   
                    enemy.destroyEnemy();
                }
            }*/
            
            for (Tank tankObjective : tanks) {

                Shape tankeBound = tankObjective.getShape();

                if (Physics.testIntersection(missileBound, tankeBound)) {
                	
                	Physics.checkCollisionMissileTank(missile,tankObjective);                	
                }
            }
        }
	       
        List<Mine> minas = tank.getMines();	
	        
        for (Mine mine : minas) {
	
        	Shape mineBound = mine.getShape();
            
        	for (Tank tankObjective : tanks) {
                
        		Shape tankeBound = tankObjective.getShape();
	        	
                if (Physics.testIntersection(mineBound,tankeBound)) {
                	
                	Physics.checkCollisionMineTank(mine, tankObjective);
	        	}
	        }
        	for (Missile missile : missiles) {
        		Shape missileBound = missile.getShape();
        		if(Physics.testIntersection(mineBound, missileBound)) {
        			
        			Physics.checkCollisionMineMissile(mine, missile);        			
        		}
        	}
        }
        
        
        for (Box box : boxes) {

        	Shape boxBound = box.getShape();
        	
        	if (Physics.testIntersection(boxBound,tankBound))
        	{
        		Physics.checkCollisionTankBox(tank, box);
        	}
	 
            for (Missile missile : missiles) {
           	
            	Shape missileBound = missile.getShape();
            	
            	if(!box.isMissileInside() && Physics.testIntersection(boxBound, missileBound)) {
            		
            		Physics.checkCollisionMissileBox(missile,box);
            	}
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
