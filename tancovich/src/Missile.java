

public class Missile extends Sprite implements Entity{

    private final int MISSILE_SPEED = 10;
    private int bounce = 0;
    private int shooterId;
    private int damage;

    public Missile(int x, int y, int r, int shooter) {
        super(x, y, r);
        setDamage();
        setShooterId(shooter);
        init();
    }

    public void init() {
    	
    	if(shooterId == 1) {
    		loadImage("Resources/bulletRed.png", 180+r);
    	}
    	else {
    		loadImage("Resources/bulletBlue.png", 180+r);
    	}
    }

    public void update() {

    	if(isVisible())
        {
	    	if(isAlive())
	    	{
	    		x = x + (int)(Math.sin(Math.toRadians(-r)) * MISSILE_SPEED);
	    		y = y + (int)(Math.cos(Math.toRadians(-r)) * MISSILE_SPEED);
	
	    		//Rebote contra laterales
		        if (x < 0 || x > BOARD_WIDTH - getWidth())
		        {
		        	setR(-getR());
		        	bounce++;
		        }
		        //Rebote superior e inferior
		        if (y < 0 || y > BOARD_HEIGHT - getHeight())
		        {
		        	setR(180-getR());
		        	bounce++;
		        }
	    	}
	        
	        if(bounce < 4)
        	{
	        	if(shooterId == 1) 
	        	{
		    		loadImage("Resources/bulletRed.png", 180+r);
		    	}
		        else 
		        {
		    		loadImage("Resources/bulletBlue.png", 180+r);
		    	}
        	}
        	else
        	{
        		if(this.isAlive()) this.setAlive(false);
        		explodeSprite(30);
        		explosionCounter++; 	    			
        	}	        
        }
    }

	public int getBounce() {
		return bounce;
	}

	public void setBounce(int bounce) {
		this.bounce = bounce;
	}

	public int getShooterId() {
		return shooterId;
	}

	public void setShooterId(int shooterId) {
		this.shooterId = shooterId;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage() {
		this.damage = randomWithRange(18, 32);
	}
	
	int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}   
}
