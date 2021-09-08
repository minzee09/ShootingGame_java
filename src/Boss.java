import javax.swing.*;
import java.awt.*;

public class Boss {
    private Image image = new ImageIcon("src/images/boss.png").getImage();
    
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int hp = 30;

    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Image getBoss() {
    	return this.image;
    }
    
}