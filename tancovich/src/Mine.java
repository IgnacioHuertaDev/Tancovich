
public class Mine extends Sprite implements Entity {
    
	private int shooterId;
    private int damage;
    private boolean explode = false;
    private int timer = 0;
    
	public Mine(int x, int y, int shooter) {
		super(x, y);		
        setDamage();
        setShooterId(shooter);
		init();
	}
    
	public void init() {

        loadImage("Resources/mina.png");

    }

    public void update() {
          
		if (isAbove()) loadImage("Resources/minaOn.png");

		if(isExplode()) {
			if(isAlive()) setAlive(false);
			setDamage();
			explodeSprite(30); 
		}		

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
		this.damage = randomWithRange(35, 55);
		//Cuando la mina explota, el daño se vuelve cero para que la animacion no afecte al tanque
		if(isExplode()== true) this.damage = 0;
	}
	int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	public boolean isExplode() {
		return explode;
	}

	public void setExplode(boolean explode) {
		this.explode = explode;
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

}