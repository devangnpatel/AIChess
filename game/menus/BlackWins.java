package game.menus;
        
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Contains paths and performs File I/O for sprite images of pieces
 * @author devang
 */
public class BlackWins {
    
    private final static String GRAPHICS_FILE_ROOT = "menus/";
    
    public BlackWins()
    {

    }
    
    private String getMenuFilename()
    {
        String graphicsFileName = "";
        
        graphicsFileName += GRAPHICS_FILE_ROOT;
        graphicsFileName += "BlackWins.png";
        
        return graphicsFileName;
    }
        
    private BufferedImage loadImage()
    {
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource(getMenuFilename()));
        } catch (IOException e) {

        }
        
        if (image == null) return null;
        
        return image;
    }
}
