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
    	"Resources/Menu/LOAD2.jpg", //0
    	"Resources/Menu/MainMenu/MENU1(PASIVO).jpg", 
    	"Resources/Menu/MainMenu/MENU2(PLAY).jpg",
    	"Resources/Menu/MainMenu/MENU5(HELP).jpg",
    	"Resources/Menu/MainMenu/MENU6(CREDITS).jpg",
    	"Resources/Menu/MainMenu/MENU4(BACK).jpg", //5
    	"Resources/Menu/MainMenu/MENU3(EXIT).jpg",
    	"Resources/Menu/HowToPlay/HOWTOPLAY.jpg",
    	"Resources/Menu/PauseMenu/PAUSE(PASIVO).png", //8
    	"Resources/Menu/PauseMenu/PAUSE(RESUME).png",
    	"Resources/Menu/PauseMenu/PAUSE(BACK).png",
    	"Resources/Menu/GameOver/GAMEOVER(PASIVO).png", //11
    	"Resources/Menu/GameOver/PLAYAGAIN.png",
    	"Resources/Menu/GameOver/BACKTOMENU.png",
    	"Resources/Menu/Credits/CREDITS.jpg", //14
    	"Resources/Menu/Credits/CREDITSBACK.jpg",
    	"Resources/Menu/HowToPlay/HOWTOPLAYBACK.jpg"
    };

    public JLabel getLabel() { 
    	return this.label;
    }
    
    public void render(Graphics g, int render) {
		try {
        	background = ImageIO.read(getClass().getResourceAsStream(renders[render]));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	g.drawImage(background, 0, 0, null);
	}
}
