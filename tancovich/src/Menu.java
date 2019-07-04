import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Menu {
	
	private Board board;
    private BufferedImage background;
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
    	"Resources/Menu/HowToPlay/HOWTOPLAYBACK.jpg",
    	"Resources/Menu/ChooseMap/CHOOSEMAP(PASIVO).jpg", //17    	
    	"Resources/Menu/ChooseMap/CHOOSEMAP(LVL1).jpg",
    	"Resources/Menu/ChooseMap/CHOOSEMAP(LVL2).jpg",
    	"Resources/Menu/ChooseMap/CHOOSEMAP(BACK).jpg"    	
    };
    
    public static int[][] Buttons = {
        	
    	{327,460,230,280}, //PLAY
    	{327,460,310,350}, //HELP
    	{327,460,380,420}, //CREDITS
    	{20,100,535,580}, //BACK
    	{720,780,535,580}, //EXIT
    	{265,500,425,475}, //PRESS ENTER
    	{270,530,240,272}, //RESUME
    	{265,530,320,350}, //BACK TO MENU
    	{140,365,180,350}, //MAP 1
    	{440,665,180,350} //MAP 2
    };

    public Menu(Board board) {
		super();
		this.board = board;
	}
    
    public void render(Graphics g, int render) {
		try {
        	background = ImageIO.read(getClass().getResourceAsStream(renders[render]));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	g.drawImage(background, 0, 0, null);
    	board.repaint();
	}
    
    public void drawGameOver()
    {
    	board.setRender(11);
    	board.setState(Board.STATE.GAMEOVER);
    }
}
