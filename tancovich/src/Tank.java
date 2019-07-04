import java.util.ArrayList;
import java.util.List;

public class Tank extends Sprite implements Entity{

	private static final int TANK_ROTATION = 5;
	private static final int TANK_SPEED = 5;
	private static final int MAX_MISSILES = 8;
	private static final int MAX_MINES = 4;
	private static final int MISSILE_INTERVAL = 15;
	private static final int MINE_INTERVAL = 30;
	
	private int id;
    private int forward;
    private int health = 100;
    private int impacts = 0;
    private List<Missile> missiles;
    private List<Mine> mines;
    private boolean canForward = true;
    private boolean canBack = true;
    private int missileControl = 0;
	private int mineControl = 0;    
    private int missileNumber = MAX_MISSILES;
    private int minesNumber = MAX_MINES;
    private int timer = 0;
    private int lifes = 3;
    private Box collisionedBox = null;
    private boolean exploded = false;
    
    public static final int[][] tankPositions = {
    		
            {60, 60, -45},
            {680, 480, 135}
    };
    
	private final int[][] tankControls = {
    		    		
            {37, 38, 39, 40, 32, 17}, 
            {65, 87, 68, 83, 70, 71},
            {74, 73, 76, 75, 79, 80},
    		{100, 104, 102, 101, 106, 109}
    };

    public Tank(int id, int x, int y, int r) {
        super(x, y, r);
        this.id = id;
        init();
    }
    
    public int getId() {
		return this.id;
	}
    
    public int getForward() {
		return forward;		
	}
	
	public void setForward(int forward) {
		this.forward = forward;
	}
	
	public boolean isCanForward() {
		return canForward;
	}
	
	public void setCanForward(boolean canForward) {
		this.canForward = canForward;
	}
	
	public boolean isCanBack() {
		return canBack;
	}
	
	public void setCanBack(boolean canBack) {
		this.canBack = canBack;
	}	

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {		
		this.health = health;
		if(health <= 0) setExploded(true);
	}
    
    public int getImpacts() {
		return impacts;
	}

	public void setImpacts(int impacts) {
		this.impacts = impacts;
	}

	public List<Missile> getMissiles() {
        return missiles;
    }
    
    public List<Mine> getMines() {
        return mines;
    }
    
    public boolean canFire() {
		return getMissileNumber() > 0;
	}

	public boolean canPlant() {
		return getMinesNumber() > 0;
	}

	public int getMinesNumber() {
		return minesNumber;
	}

	public void setMinesNumber(int minesNumber) {
		this.minesNumber = minesNumber;
	}
    public int getMineControl() {
		return mineControl;
	}

	public void setMineControl(int mineControl) {
		this.mineControl = mineControl;
	}

	public int getMissileNumber() {
		return missileNumber;
	}

	public void setMissileNumber(int missileNumber) {
		this.missileNumber = missileNumber;
	}
	
	public int getMissileControl() {
		return missileControl;
	}

	public void setMissileControl(int missileControl) {
		this.missileControl = missileControl;
	}
	
	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}	

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public Box getCollisionedBox() {
		return collisionedBox;
	}

	public void setCollisionedBox(Box collisionedBox) {
		this.collisionedBox = collisionedBox;
	}

    public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}

	public void init() {

    	loadTank(id);
        missiles = new ArrayList<>();
        mines = new ArrayList<>();
    }
    
    public void update() {
        
        if(isVisible())
        {
        	if(isAlive())
        	{
    			checkControls();            	
            	x = x + (int)(Math.sin(Math.toRadians(-r)) * getForward());
                y = y + (int)(Math.cos(Math.toRadians(-r)) * getForward());
                reloadMissiles();
        	}
        	
        	if(isExploded())
    		{
    			if(isAlive()) setAlive(false);
				explodeSprite(30);
    		}
        }
        else
        {
        	if (getLifes() > 0) 
        	{
        		init();        		
        		setVisible(true);
        		setAlive(true);
        		setExploded(false);
        		setLifes(getLifes()-1);
            	setHealth(100);
            	setX(tankPositions[getId()-1][0]);
            	setY(tankPositions[getId()-1][1]);
            	setR(tankPositions[getId()-1][2]);
            }
        }
        if(missileControl > 0) missileControl--;
        if(mineControl > 0) mineControl--;
    }
    
    public void loadTank(int id)
    {
    	if(id == 1)
        {
        	loadImage("Resources/tankRed.png");
        }
        else if(id == 2)
        {
        	loadImage("Resources/tankBlue.png");
        }
    }
    
    public void checkControls()
    {
    	if (Keyboard.keydown[tankControls[id-1][0]]) //Izquierda
    	{
    		if(forward >= 0) setR(getR() - TANK_ROTATION);
    		else setR(getR()+TANK_ROTATION);
    		loadTank(id);
    		
    	}
    	if (Keyboard.keydown[tankControls[id-1][2]]) //Derecha
    	{
    		if(forward >= 0) setR(getR() + TANK_ROTATION);
    		else setR(getR()-TANK_ROTATION);
    		loadTank(id);
    	}
    	if(!Keyboard.keydown[tankControls[id-1][3]] && !Keyboard.keydown[tankControls[id-1][1]])
    	{
    		setForward(0);
    	}
    	else
    	{
        	if (isCanForward() && Keyboard.keydown[tankControls[id-1][1]]) //Marcha adelante
        	{
        		setForward(TANK_SPEED);
        	}
        	if (isCanBack() && Keyboard.keydown[tankControls[id-1][3]]) //Marcha atras
        	{
        		setForward(-TANK_SPEED);
        	}
    	}    	
    	if (Keyboard.keydown[tankControls[id-1][4]] && missileControl == 0 && canFire()) {
        	fire();
        	missileControl = MISSILE_INTERVAL;
        	setMissileNumber(getMissileNumber()-1);
        }      
        if (Keyboard.keydown[tankControls[id-1][5]] && mineControl == 0 && canPlant()) {
        	plant();
        	mineControl = MINE_INTERVAL;
        	setMinesNumber(getMinesNumber()-1);
        }
    }
    
    public void fire() {
    	
    	missiles.add(new Missile(x + width / 2, y + height / 2, r, getId()));
    }
    
    public void plant() {
    	
    	mines.add(new Mine(x + width / 2, y + height /2, getId()));
    }
    
	public void reloadMissiles () {
		
        if(getTimer() > 50 && !canFire()) {
        	setMissileNumber(10);
        	setTimer(0);
        }
	}
    
    public ArrayList<Integer> calculateNextPosition(boolean forward) {
    	
    	ArrayList<Integer> nextPos = new ArrayList<Integer>();
    	nextPos.add(getX() + (int)(Math.sin(Math.toRadians(-getR())) * (forward ? 5 : -5)));
    	nextPos.add(getY() + (int)(Math.cos(Math.toRadians(-getR())) * (forward ? 5 : -5)));
    	return nextPos;
    }
}