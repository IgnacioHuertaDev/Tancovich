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
		board.mouseMoved(e);
	}

	public void mouseExited(MouseEvent e) {
				
	}

	public void mousePressed(MouseEvent e) {
		board.mousePressed(e);	
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
