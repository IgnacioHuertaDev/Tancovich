import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

	Board board;
	
	public MouseInput(Board board) {
		this.board = board;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}
	
	public void mouseMoved(MouseEvent e) {
		
		//Coordenadas X Y del mouse
    	int mx  = e.getX();
    	int my  = e.getY();
    	//System.out.println("X: " + mx + " Y: " + my);
    	
    	if (board.getState() == Board.STATE.MAINMENU) {
			
			if(Board.isBetween(mx,Menu.Buttons[0][0],Menu.Buttons[0][1]) && Board.isBetween(my,Menu.Buttons[0][2],Menu.Buttons[0][3])) {
				board.setRender(2);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[1][0],Menu.Buttons[1][1]) && Board.isBetween(my,Menu.Buttons[1][2],Menu.Buttons[1][3])) {
				board.setRender(3);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[2][0],Menu.Buttons[2][1]) && Board.isBetween(my,Menu.Buttons[2][2],Menu.Buttons[2][3])) {
				board.setRender(4);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(5);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[4][0],Menu.Buttons[4][1]) && Board.isBetween(my,Menu.Buttons[4][2],Menu.Buttons[4][3])) {
				board.setRender(6);	
			}
			
			else {
				board.setRender(1);
			}
		}
    	else if(board.getState() == Board.STATE.HELP) 
    	{
    		if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(16);
			}			
			else
			{
				board.setRender(7);
			}
    	}
    	else if(board.getState() == Board.STATE.CREDITS) 
    	{
    		if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(15);
			}			
			else
			{
				board.setRender(14);
			}
    	}
    	else if(board.getState() == Board.STATE.CHOOSEMAP) 
    	{
    		if(Board.isBetween(mx,Menu.Buttons[8][0],Menu.Buttons[8][1]) && Board.isBetween(my,Menu.Buttons[8][2],Menu.Buttons[8][3])) {
				board.setRender(18);
			}
    		else if(Board.isBetween(mx,Menu.Buttons[9][0],Menu.Buttons[9][1]) && Board.isBetween(my,Menu.Buttons[9][2],Menu.Buttons[9][3])) {
				board.setRender(19);
			}
    		else if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(20);
			}
			else
			{
				board.setRender(17);
			}
    	}
    	else if(board.getState() == Board.STATE.PAUSE)
    	{
    		if(Board.isBetween(mx,Menu.Buttons[6][0],Menu.Buttons[6][1]) && Board.isBetween(my,Menu.Buttons[6][2],Menu.Buttons[6][3])) {
				board.setRender(9);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[7][0],Menu.Buttons[7][1]) && Board.isBetween(my,Menu.Buttons[7][2],Menu.Buttons[7][3])) {
				board.setRender(10);
			}
			
			else
			{
				board.setRender(8);
			}
    	}
    	else if(board.getState() == Board.STATE.GAMEOVER)
    	{
    		if(Board.isBetween(mx,Menu.Buttons[6][0],Menu.Buttons[6][1]) && Board.isBetween(my,Menu.Buttons[6][2],Menu.Buttons[6][3])) {
				board.setRender(12);
			}
			
			else if(Board.isBetween(mx,Menu.Buttons[7][0],Menu.Buttons[7][1]) && Board.isBetween(my,Menu.Buttons[7][2],Menu.Buttons[7][3])) {
				board.setRender(13);
			}
			
			else
			{
				board.setRender(11);
			}
    	}
	}

	public void mouseExited(MouseEvent e) {
				
	}

	public void mousePressed(MouseEvent e) {
		
		//Coordenadas X Y del mouse
		int mx  = e.getX();
		int my  = e.getY();
		//System.out.println("X: " + mx + " Y: " + my);

		if (board.getState() == Board.STATE.STARTMENU) {
			
			if(Board.isBetween(mx,Menu.Buttons[5][0],Menu.Buttons[5][1]) && Board.isBetween(my,Menu.Buttons[5][2],Menu.Buttons[5][3])) {
				board.setRender(1);
				board.setState(Board.STATE.MAINMENU);
			}
		}		
		if (board.getState() == Board.STATE.MAINMENU) {
			
			if(Board.isBetween(mx,Menu.Buttons[0][0],Menu.Buttons[0][1]) && Board.isBetween(my,Menu.Buttons[0][2],Menu.Buttons[0][3])) {
				board.setState(Board.STATE.CHOOSEMAP);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[1][0],Menu.Buttons[1][1]) && Board.isBetween(my,Menu.Buttons[1][2],Menu.Buttons[1][3])) {
				board.setRender(7);
				board.setState(Board.STATE.HELP);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[2][0],Menu.Buttons[2][1]) && Board.isBetween(my,Menu.Buttons[2][2],Menu.Buttons[2][3])) {
				board.setRender(14);
				board.setState(Board.STATE.CREDITS);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(0);
				board.setState(Board.STATE.STARTMENU);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[4][0],Menu.Buttons[4][1]) && Board.isBetween(my,Menu.Buttons[4][2],Menu.Buttons[4][3])) {
				System.exit(1);		
			}
		}		
		if(board.getState() == Board.STATE.HELP) {
			
			if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(1);
				board.setState(Board.STATE.MAINMENU);
			}
		}
		
		if(board.getState() == Board.STATE.CREDITS) {
			
			if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(1);
				board.setState(Board.STATE.MAINMENU);
			}
		}
		
		if(board.getState() == Board.STATE.CHOOSEMAP) {
			
			if(Board.isBetween(mx,Menu.Buttons[8][0],Menu.Buttons[8][1]) && Board.isBetween(my,Menu.Buttons[8][2],Menu.Buttons[8][3])) {
				board.setLvl(1);
				board.initGame();
				board.setState(Board.STATE.GAME);
			}
			if(Board.isBetween(mx,Menu.Buttons[9][0],Menu.Buttons[9][1]) && Board.isBetween(my,Menu.Buttons[9][2],Menu.Buttons[9][3])) {
				board.setLvl(2);
				board.initGame();
				board.setState(Board.STATE.GAME);
			}
			if(Board.isBetween(mx,Menu.Buttons[3][0],Menu.Buttons[3][1]) && Board.isBetween(my,Menu.Buttons[3][2],Menu.Buttons[3][3])) {
				board.setRender(1);
				board.setState(Board.STATE.MAINMENU);
			}
		}		
		if(board.getState() == Board.STATE.PAUSE) {
			
			if(Board.isBetween(mx,Menu.Buttons[6][0],Menu.Buttons[6][1]) && Board.isBetween(my,Menu.Buttons[6][2],Menu.Buttons[6][3])) {
				board.setState(Board.STATE.GAME);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[7][0],Menu.Buttons[7][1]) && Board.isBetween(my,Menu.Buttons[7][2],Menu.Buttons[7][3])) {
				board.setState(Board.STATE.MAINMENU);
			}
		}		
		if(board.getState() == Board.STATE.GAMEOVER) {
			
			if(Board.isBetween(mx,Menu.Buttons[6][0],Menu.Buttons[6][1]) && Board.isBetween(my,Menu.Buttons[6][2],Menu.Buttons[6][3])) {
				board.initGame();
				board.setState(Board.STATE.GAME);
			}
			
			if(Board.isBetween(mx,Menu.Buttons[7][0],Menu.Buttons[7][1]) && Board.isBetween(my,Menu.Buttons[7][2],Menu.Buttons[7][3])) {
				board.setState(Board.STATE.MAINMENU);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}
}
