
public class Hearts extends Sprite implements Entity{
	
	private int tankId;
	private int tankLifes;
	
	public Hearts(int x, int y, int tankId) {
		super(x, y);
		setTankId(tankId);
		init();
		
	}

	@Override
	public void init() {
		if(getTankId() == 1) {
			
			loadImage("Resources/heart.png");
		}
		else if(getTankId() == 2){
			
			loadImage("Resources/heart.png");
		}
	}

	@Override
	public void update() {
		if(getTankId() == 1) {
			if(getNumberLifes() > 0) loadImage("Resources/heart.png");
			else setVisible(false);
		}
		//else setVisible(false);
		
		else if(getTankId() == 2) {
			if(getNumberLifes() > 0) loadImage("Resources/heart.png");
			else setVisible(false);
		}
	}

	public void setNumberLifes (int tankLife) {
		this.tankLifes = tankLife;
	}
	
	public int getNumberLifes() {
		return this.tankLifes;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
}
