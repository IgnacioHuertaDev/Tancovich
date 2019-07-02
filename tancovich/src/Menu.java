import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Menu {
	
    private BufferedImage background;
    private JLabel label = new JLabel("");
    private String[] renders = {
    	"Resources/Menu/LOAD2.jpg",
    	"Resources/Menu/MENU1(PASIVO).jpg",
    	"Resources/Menu/HOWTOPLAY.jpg",
    	"Resources/Menu/MENU2(PLAY).jpg",
    	"Resources/Menu/MENU5(HELP).jpg",
    	"Resources/Menu/MENU6(CREDITS).jpg",
    	"Resources/Menu/MENU4(BACK).jpg",
    	"Resources/Menu/MENU3(EXIT).jpg"
    	
    };

    public JLabel getLabel() { 
    	return this.label;
    }
    
    public void render(Graphics g, int render) { //Menu PORTADA 
		try {
        	background = ImageIO.read(getClass().getResourceAsStream(renders[render]));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	g.drawImage(background, 0, 0, null);
	}
}
