
public class Mine extends Sprite implements Entity {
    
	private int shooterId;
    private int damage;
    private boolean exploded = false;
    private int timer = 0;
    
	public Mine(int x, int y, int shooter) {
		super(x, y);		
        setDamage();
        setShooterId(shooter);
		init();
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
		this.damage = Physics.randomWithRange(35, 55);
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

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public boolean isAbove() {
		return getTimer() > 100;
	}
    
	public void init() {

        loadMine();
    }
	
	public void loadMine()
	{
		loadImage("Resources/mina.png");
	}

    public void update() {
          
		if(isAbove()) loadMine();

		if(isExploded() && isAlive()) 
		{
			if(isAlive()) setAlive(false);
			explodeSprite(30);
		}
    }
}