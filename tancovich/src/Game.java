import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	public Game() {

        initUI();
       
    }

    private void initUI() {

    	try {
	    	Image background = ImageIO.read(getClass().getResourceAsStream("Resources/logoIcon.png"));
	    	setIconImage(background);
    	} 
    	catch (IOException ex) {
    		Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
		}
    	
        add(new Board());
        setResizable(false);
        pack();

        setTitle("Tancovich");
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable()  {
    
			public void run() {
	   			try {
	   				Game ex = new Game();
	   				ex.setVisible(true);
	   			}
	   			catch (Exception e) {
	   				e.printStackTrace();
	   			}
			}
    		
        });
    }
}
