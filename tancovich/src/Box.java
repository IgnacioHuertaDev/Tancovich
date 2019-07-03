public class Box extends Sprite {
	
	private boolean destroyable = false;
	private boolean missileInside = false;

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
