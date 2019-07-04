
public class Heart extends Sprite implements Entity{
	
	private int tankId;
	private int tankLifes;
	
	public static final int[][] heartsPositions = {
    		
            {80, 20},
            {420, 20}
    };
	
	public Heart(int x, int y, int tankId) {
		super(x, y);
		setTankId(tankId);
		init();		
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

	public void init() {
		
		loadHeart();
	}

	public void update() {
		
		if(getNumberLifes() > 0) loadHeart();
		else setVisible(false);
	}

	public void loadHeart()
	{
		loadImage("Resources/heart.png");
	}
}
