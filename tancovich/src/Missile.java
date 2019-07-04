

public class Missile extends Sprite implements Entity{

    private final int MISSILE_SPEED = 10;
    private int bounce = 0;
    private int shooterId;
    private int damage;
    private boolean exploded = false;

    public Missile(int x, int y, int r, int shooter) {
        super(x, y, r);
        setDamage();
        setShooterId(shooter);
        init();
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
		this.damage = Physics.randomWithRange(18, 32);
	}
	
	public void setDamage(int damage) {		
		this.damage = damage;
	}
	
	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}

    public void init() {
    	
    	loadMissile(shooterId);
    }

    public void update() {

    	if(isVisible())
        {
	    	if(isAlive())
	    	{
	    		x = x + (int)(Math.sin(Math.toRadians(-r)) * MISSILE_SPEED);
	    		y = y + (int)(Math.cos(Math.toRadians(-r)) * MISSILE_SPEED);
	    	}
	    	
	    	if(isExploded())
    		{
    			if(isAlive()) setAlive(false);
				explodeSprite(30);
    		}
	    	else
	    	{
	    		if(bounce >= 4) setExploded(true);
	    	}
        }
    }

    public void loadMissile(int id)
    {
    	if(id == 1)
    	{
    		loadImage("Resources/bulletRed.png", 180+r);
    	}
    	else if(id == 2)
    	{
    		loadImage("Resources/bulletBlue.png", 180+r);
    	}
    }
}
