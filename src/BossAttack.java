import javax.swing.*;
import java.awt.*;

public class BossAttack {
    Image image = new ImageIcon("src/images/boss_attack.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int attack = 10;

    public BossAttack(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fire() {
        this.x -= 12;
    }
}