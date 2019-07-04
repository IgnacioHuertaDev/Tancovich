public class Box extends Sprite {
	
	private boolean destroyable = false;
	private boolean missileInside = false;
	
	public static final int[][] boxesPositionBorder = {
    		{0, -1, 800, 1, 0}, //ARRIBA
    		{800, 0, 1, 600, 0}, //DERECHA
    		{0, 600, 800, 1, 0}, //ABAJO
    		{-1, 0, 1, 600, 0} //IZQUIERDA
    };
    
    public static final int[][] boxesPositionLvlOne = {
    		{293, 136, 55, 338, 0},
            {460, 133, 52, 340, 0}    		
    };
    
    public static final int[][] boxesPositionLvlTwo = {
    		{292, 0, 250, 118, 1},
            {292, 255, 250, 112, 1},
    		{292, 503, 250, 92, 1}
    };

	public Box(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	public boolean isMissileInside() {
		return missileInside;
	}

	public void setMissileInside(boolean missileInside) {
		this.missileInside = missileInside;
	}
}
