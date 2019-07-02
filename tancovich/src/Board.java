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
import java.awt.event.MouseEvent;
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
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 40;
    
    private BufferedImage background;
    private List<Tank> tanks;
    //private List<Enemy> enemies;
    private List<Box> boxes;
    private int lvl = 0;
    private List<ProgressBar> bars;
    private Timer timer;
    private Menu menu;
    private boolean enterControl = false;
    private boolean escapeControl = false;
    private int delayWeapon = 1;
    
    private final int[][] boxesPositionLvlOne = {
    		{293, 136, 55, 338},
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
            {2, 720, 480}
    };
    
    public static enum STATE {
    	STARTMENU,
    	MAINMENU,
    	HELP,
    	CREDITS,
    	GAME,
    	GAMEOVER
    };
    
    public int[][] Buttons = {
    	
    	{327,460,230,280}, //PLAY
    	{327,460,310,350}, //HELP
    	{327,460,380,420}, //CREDITS
    	{20,100,535,580}, //BACK
    	{720,780,535,580}, //EXIT
    	{265,500,425,475} //PRESS ENTER
    };
    
    public static STATE State = STATE.STARTMENU;
    private int render = 1;
    
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
        MouseInput mouseListener = new MouseInput(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        menu = new Menu();       
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    private void initGame() {
    	
    	if(lvl == 0) {
        	lvl = 1;
        }

        initTanks();
        //initEnemies();
        initBars();
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
    	
    	for (Tank t : tanks) {
    		
    		ProgressBar p = new ProgressBar(t.getId());
            p.setValue(t.getHealth());      
            p.setBounds(((t.getId()-1)*340)+100, 20, 300, 20);
            p.setVisible(true);
            bars.add(p);
            this.add(p);
    	}
    }
    
    public void initBoxes() {
    	
    	boxes = new ArrayList<>();
    	
    	switch (lvl) {
		case 1:
			for (int[] p : boxesPositionLvlOne) {
				boxes.add(new Box(p[0], p[1], p[2], p[3]));
	        }
			break;
		case 2:
			for (int[] p : boxesPositionLvlTwo) {
				boxes.add(new Box(p[0], p[1], p[2], p[3]));
	        }
			break;
			
		case 3:
			for (int[] p : boxesPositionLvlThree) {
				boxes.add(new Box(p[0], p[1], p[2], p[3]));
	        }
			break;

		default:			
			break;
		}
    }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

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
				initGame();
				State = STATE.GAME;
			}
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
				State = STATE.STARTMENU;	
			}
		}
		if (State == STATE.HELP) {
			menu.render(g,2);
			if(Keyboard.keydown[27] && !escapeControl)
			{
				escapeControl = true;
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
				render = 1;
				State = STATE.MAINMENU;				
			}
		}
		if(State == STATE.GAMEOVER) {
			drawGameOver(g);
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
        
        for (Box box : boxes) {       	
        	g.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
        }

        g.setColor(Color.WHITE);
        g.drawString("R:" + tanks.get(0).getR() + "   X:" + tanks.get(0).getX() + "  Y:" + tanks.get(0).getY(), 10, 30);
        //g.drawString("Enemies left: " + enemies.size(), 5, 15);  
        Font small = new Font("Helvetica", Font.BOLD, 15);
        g.setFont(small);
        g.drawString("Player 1:", 180, 16);
		g.drawString("Player 2:", 550, 16);
        
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 34);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
        
    }

    @Override  
    public void actionPerformed(ActionEvent e) {
    	
    	if (State == STATE.GAME) {
    		for (Tank tank: tanks)
	    	{
				setDelayWeapon(getdelayWeapon()+1);
	        	updateTanks(tank);
		        updateMissiles(tank);
		        updateMines(tank);
		        checkCollisions(tank);
	    	}
		}
		//updateEnemies();
    	repaint();
    }
    
    private void updateTanks(Tank tank) {

        if (tank.isVisible()) {

            tank.update();
            tank.reloadMissiles(getdelayWeapon());
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
    public void checkCollisions(Tank tank) {

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
                	if(mine.getShooterId() != tankObjective.getId() || mine.getShooterId() == tank.getId()  && getdelayWeapon() % 50 == 0) {
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
        		ArrayList<Integer> nextPos = new ArrayList<Integer>();
	    		if(tank.getForward() >= 0)
	    		{
	    			
	    			nextPos = tank.calculateNextPosition(true);
	    			if (Sprite.testIntersection(boxBound,nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
	    			{
	    				tank.setCanForward(false);
	    				tank.setForward(0);
	    			}
		            else if(!tank.isCanForward()) tank.setCanForward(true);
	    		}
	    		else
	    		{
	    			nextPos = tank.calculateNextPosition(false);
	    			if (Sprite.testIntersection(boxBound,nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
	    			{
	    				tank.setCanBack(false);
	    				tank.setForward(0);
		    		}
		            else if(!tank.isCanBack()) tank.setCanBack(true);
	    		}
        	}
	 
            for (Missile missile : missiles) {
           	
            	Shape missileBound = missile.getShape();
            	
            	if(Sprite.testIntersection(boxBound, missileBound)) {
            		
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
    
    public boolean isBetween(int x, int lower, int upper) {
    	return lower <= x && x <= upper;
    }
    
    public void mouseMoved(MouseEvent e) {
    	
    	//Coordenadas X Y del mouse
    	int mx  = e.getX();
    	int my  = e.getY();
    	//System.out.println("X: " + mx + " Y: " + my);
    	
    	if (State == STATE.MAINMENU) {
			
			if(isBetween(mx,Buttons[0][0],Buttons[0][1]) && isBetween(my,Buttons[0][2],Buttons[0][3])) {
				render = 3;
			}
			
			else if(isBetween(mx,Buttons[1][0],Buttons[1][1]) && isBetween(my,Buttons[1][2],Buttons[1][3])) {
				render = 4;
			}
			
			else if(isBetween(mx,Buttons[2][0],Buttons[2][1]) && isBetween(my,Buttons[2][2],Buttons[2][3])) {
				render = 5;
			}
			
			else if(isBetween(mx,Buttons[3][0],Buttons[3][1]) && isBetween(my,Buttons[3][2],Buttons[3][3])) {
				render = 6;
			}
			
			else if(isBetween(mx,Buttons[4][0],Buttons[4][1]) && isBetween(my,Buttons[4][2],Buttons[4][3])) {
				render = 7;	
			}
			
			else {
				render = 1;
			}
		}
    }
	
	public void mousePressed(MouseEvent e) { //Mouse Action
		
		//Coordenadas X Y del mouse
		int mx  = e.getX();
		int my  = e.getY();

		if (State == STATE.STARTMENU) {
			
			if(isBetween(mx,Buttons[5][0],Buttons[5][1]) && isBetween(my,Buttons[5][2],Buttons[5][3])) {
				State = STATE.MAINMENU;
			}
		}
		
		if (State == STATE.MAINMENU) {
			
			if(isBetween(mx,Buttons[0][0],Buttons[0][1]) && isBetween(my,Buttons[0][2],Buttons[0][3])) {
				initGame();
				State = STATE.GAME;
			}
			
			if(isBetween(mx,Buttons[1][0],Buttons[1][1]) && isBetween(my,Buttons[1][2],Buttons[1][3])) {
				State = STATE.HELP;
			}
			
			if(isBetween(mx,Buttons[2][0],Buttons[2][1]) && isBetween(my,Buttons[2][2],Buttons[2][3])) {
				State = STATE.CREDITS;
			}
			
			if(isBetween(mx,Buttons[3][0],Buttons[3][1]) && isBetween(my,Buttons[3][2],Buttons[3][3])) {
				State = STATE.STARTMENU;
			}
			
			if(isBetween(mx,Buttons[4][0],Buttons[4][1]) && isBetween(my,Buttons[4][2],Buttons[4][3])) {
				System.exit(1);		
			}
		}
		
		if(State == STATE.HELP) {
			
			if(isBetween(mx,Buttons[3][0],Buttons[3][1]) && isBetween(my,Buttons[3][2],Buttons[3][3])) {
				State = STATE.STARTMENU;
			}
		}
		
		if(State == STATE.CREDITS) {
			
			if(isBetween(mx,Buttons[3][0],Buttons[3][1]) && isBetween(my,Buttons[3][2],Buttons[3][3])) {
				State = STATE.STARTMENU;
			}
		}
	}

	public int getdelayWeapon() {
		return delayWeapon;
	}

	public void setDelayWeapon(int delayWeapon) {
		this.delayWeapon = delayWeapon;
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
