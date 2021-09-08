import javax.swing.*;
import java.awt.*;

public class EnemyAttack {
    Image image = new ImageIcon("src/images/enemy_attack.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int attack = 5;
    static int enemyColor;

    public EnemyAttack(int x, int y) {
        this.x = x;
        this.y = y;
        
       
        if (enemyColor == 1) {
        	image = new ImageIcon("src/images/enemy_attack2.png").getImage();
        }
        else {
        	image = new ImageIcon("src/images/enemy_attack.png").getImage();
        }
        
    }

    public void fire() {
        this.x -= 12;
    }
}