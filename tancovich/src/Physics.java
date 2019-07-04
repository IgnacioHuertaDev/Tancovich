import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Physics {
	
	public static void checkCollisionMissileBox(Missile missile, Box box)
	{
		//Superior e inferior
		if((missile.getY() < box.getY() && isBetween(missile.getX(),box.getX(),box.getX()+box.getWidth()-missile.getWidth())) || 
		(missile.getY() > box.getY() + box.getHeight() - missile.getHeight() && isBetween(missile.getX(),box.getX(),box.getX()+box.getWidth()-missile.getWidth())))
		{
			missile.setR(180-missile.getR());
			missile.loadMissile(missile.getShooterId());
		}            		
		//Izquierdo y derecho
		if(missile.getX() < box.getX() && isBetween(missile.getY(),box.getY(),box.getY()+box.getHeight()-missile.getHeight()) || missile.getX() > box.getX() + box.getWidth() - missile.getWidth() && isBetween(missile.getY(),box.getY(),box.getY()+box.getHeight()-missile.getHeight()))
		{
			missile.setR(missile.getR()*-1);
			missile.loadMissile(missile.getShooterId());
		}
		
		//Agrego rebote
		missile.setBounce(missile.getBounce()+1);
	}
	
	public static void checkCollisionMissileTank(Missile missile, Tank tank) {
		
		if((missile.getBounce() >= 1 && missile.getShooterId() == tank.getId()) || (missile.getShooterId() != tank.getId())) {
    		if(tank.isVisible()) {
        		tank.setHealth(tank.getHealth() - missile.getDamage());
        		missile.setDamage(0);
        		missile.setExploded(true);
    		}
    	}
		
	}	

	public static void checkCollisionMineTank(Mine mine, Tank tank) {
		
		if(mine.getShooterId() != tank.getId() || mine.getShooterId() == tank.getId() && mine.isAbove()) {
    		
    		if(tank.visible) {
    			
        		tank.setHealth(tank.getHealth() - mine.getDamage());
        		mine.setDamage(0);
        		mine.setExploded(true);
    		}
    	}		
	}

	public static void checkCollisionMineMissile(Mine mine, Missile missile) {
		mine.setExploded(true);
		missile.setVisible(false);
	}
	
	public static void checkCollisionTankBox(Tank tank, Box box)
    {
    	Shape boxBound = box.getShape();
    	ArrayList<Integer> nextPos = new ArrayList<Integer>();
    	if(tank.getForward() > 0)
		{
			nextPos = tank.calculateNextPosition(true);
			if(Physics.testIntersection(boxBound,nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
			{
				tank.setCanForward(false);
				tank.setForward(0);
    			tank.setCollisionedBox(box);
			}	    			
		}
		if(tank.getForward() < 0)
		{
			nextPos = tank.calculateNextPosition(false);
			if(Physics.testIntersection(boxBound,nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
			{
				tank.setCanBack(false);
				tank.setForward(0);
				tank.setCollisionedBox(box);
			}
		}
    }
    
    public static void restoreTankMovement(Tank tank, Box box)
    {
    	ArrayList<Integer> nextPos = new ArrayList<Integer>();
    	if(!tank.isCanForward())
    	{    		
    		nextPos = tank.calculateNextPosition(true);
    		if(!Physics.testIntersection(box.getShape(),nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
    		{
        		tank.setCanForward(true);
        		tank.setCollisionedBox(null);
    		}
    	}
    	if(!tank.isCanBack())
    	{
    		nextPos = tank.calculateNextPosition(false);
    		if(!Physics.testIntersection(box.getShape(),nextPos.get(0),nextPos.get(1),tank.getWidth()-4,tank.getHeight()-4))
    		{
    			tank.setCanBack(true);
    			tank.setCollisionedBox(null);
    		}
    	}
    }
    
    public static boolean testIntersection(Shape shapeA, Shape shapeB) {
    	Area areaA = new Area(shapeA);
    	areaA.intersect(new Area(shapeB));
    	return !areaA.isEmpty();
    }
    
    public static boolean testIntersection(Shape shapeA, int x, int y, int width, int height) {
    	Shape shapeB = new Rectangle(x, y, width, height);
    	Area areaA = new Area(shapeA);
    	areaA.intersect(new Area(shapeB));
    	return !areaA.isEmpty();
    }
	
	public static boolean isBetween(int x, int lower, int upper) {
    	return lower <= x && x <= upper;
    }
	
	public static int randomWithRange(int min, int max) {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
}
