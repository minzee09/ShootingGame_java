import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {
    Image image = new ImageIcon("src/images/enemy.png").getImage();
    
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int hp = 10;
    

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        Random random = new Random();
        int randNum = random.nextInt(100);
       
        if (randNum %2 == 0) {
        	image = new ImageIcon("src/images/enemy2.png").getImage();
        	EnemyAttack.enemyColor = 1;
        }
        else{
        	
        	EnemyAttack.enemyColor = 0;
        }

    }

    public void move() {
         this.x -= 7;
    }
    
}