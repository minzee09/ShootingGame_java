import javax.swing.*;
import java.awt.*;

public class PlayerAttack {
    Image image = new ImageIcon("src/images/player_attack.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int attack = 5;
    static int playerColor;

    public PlayerAttack(int x, int y) {
        this.x = x;
        this.y = y;
        if (playerColor == 2) {
        	image = new ImageIcon("src/images/player_attack2.png").getImage();
        }
        else if (playerColor == 3) {
        	image = new ImageIcon("src/images/player_attack3.png").getImage();
        }
        
    }

    public void fire() {
        this.x += 15;
    }
}