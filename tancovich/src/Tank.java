import java.util.ArrayList;
import java.util.List;

public class Tank extends Sprite implements Entity {

	private int id;
    private int forward;
    private List<Missile> missiles;
    private List<Mine> mines;
    private boolean fireControl = false;
    private boolean mineControl = false;
    private int health = 100;

	private final int[][] tankControls = {
    		    		
            {37, 38, 39, 40, 32, 17}, 
            {65, 87, 68, 83, 70, 71},
            {74, 73, 76, 75, 79, 80},
    		{100, 104, 102, 101, 106, 109}            
            
    };

    public Tank(int id, int x, int y) {
        super(x, y);
        this.id = id;
        init();
    }
    
    public int getForward() {
    	return forward;
    }
    
    public void setForward(int forward) {
    	this.forward = forward;
    }
    
    public List<Missile> getMissiles() {
        return missiles;
    }
    
    public List<Mine> getMines() {
        return mines;
    }
    
    public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

    public void init() {

        if(id == 1)
        {
        	loadImage("Resources/tankRed.png");
        }
        else if(id == 2)
        {
        	loadImage("Resources/tankBlue.png");
        }
        
        missiles = new ArrayList<>();
        mines = new ArrayList<>();
    }
    
    public void update() {
    	
    	checkControls();
    	
    	x = x + (int)(Math.sin(Math.toRadians(-r)) * forward);
        y = y + (int)(Math.cos(Math.toRadians(-r)) * forward);
        
        if(id == 1)
        {
        	loadImage("Resources/tankRed.png", r);
        }
        else if(id == 2)
        {
        	loadImage("Resources/tankBlue.png", r);
        }
    }
    
    public void checkControls()
    {
    	if (Keyboard.keydown[tankControls[id-1][0]]) //Izquierda
    	{
    		if(forward >= 0) r -= 5;
    		else r += 5;
    		
    	}
    	if (Keyboard.keydown[tankControls[id-1][2]]) //Derecha
    	{
    		if(forward >= 0) r += 5;
    		else r -= 5;
    	}
    	if(!Keyboard.keydown[tankControls[id-1][3]] && !Keyboard.keydown[tankControls[id-1][1]])
    	{
    		forward = 0;
    	}
    	else
    	{
        	if (Keyboard.keydown[tankControls[id-1][1]]) //Velocidad
        	{
        		forward = 5;
        	}
        	if (Keyboard.keydown[tankControls[id-1][3]]) //Freno
        	{
        		forward = -5;
        	}
    	}    	
    	if (Keyboard.keydown[tankControls[id-1][4]] && !fireControl) {
        	fire();
        	fireControl = true;
        }
    	if(!Keyboard.keydown[tankControls[id-1][4]]) // Tiro
    	{
    		fireControl = false;
    	}        
        if (Keyboard.keydown[tankControls[id-1][5]] && !mineControl) {
        	plant();
        	mineControl = true;
        }
        if(!Keyboard.keydown[tankControls[id-1][5]]) // Mina
        {
        	mineControl = false;
        }
    }
    
    public void fire()
    {       
    	missiles.add(new Missile(x + width / 2, y + height / 2, r));
    }
    
    public void plant() {
    	mines.add(new Mine(x + width / 2, y + height /2));
    }
}