
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game2 extends Thread {
    private int delay = 20;
    private long pretime;
    private int cnt;
    private int score;

    private Game game = new Game();
    private Image player = new ImageIcon("src/images/player.png").getImage();

    private int playerX, playerY;
    private int playerWidth = player.getWidth(null);
    private int playerHeight = player.getHeight(null);
    private int playerHp = 30;
    private int playerSpeed = 5;
    
    private boolean thisStage2;
    private boolean up,down,left,right,shooting;
    private boolean isOver, isComplete;
    private boolean gameOver = false;

    private ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
    private ArrayList<BossAttack> bossAttackList = new ArrayList<BossAttack>();
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();
    private ArrayList<HealthItem> healthList = new ArrayList<HealthItem>();

    private PlayerAttack playerAttack;
    private Boss boss = new Boss(900,0);
    private BossAttack bossAttack;
    private Enemy enemy;
    private EnemyAttack enemyAttack;
    private HealthItem health;

    private Audio backgroundMusic;
    private Audio hitSound;
    private Audio itemSound;
    private Audio winSound;
    
    

    @Override
    public void run() {
        backgroundMusic = new Audio("src/audio/gameBGM2.wav", true);
        hitSound = new Audio("src/audio/hitSound.wav", false);
        itemSound = new Audio("src/audio/itemSound.wav", false);
        winSound = new Audio("src/audio/winSound.wav", false);
        
        reset();
        while (true) {
            while (!isOver) {
                pretime = System.currentTimeMillis();
                if (System.currentTimeMillis() - pretime < delay) {
                    try {
                        Thread.sleep(delay - System.currentTimeMillis() + pretime);
                        thisStage2 = true;
                        keyProcess();
                        playerAttackProcess();
                        bossAttackProcess();
                        enemyAppearProcess();
                        enemyMoveProcess();
                        enemyAttackProcess();
                        healthAppearProcess();
                        healthItemMove();
                        healthProcess();
                        playerSelect();
                        cnt++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }  

    private void playerSelect() {
    	if(PlayerAttack.playerColor == 2) {
    		player = new ImageIcon("src/images/player2.png").getImage();
    	}
    	else if(PlayerAttack.playerColor == 3) {
    		player = new ImageIcon("src/images/player3.png").getImage();
    	}
    		
    }
    
    public void reset() {
        isOver = false;
        gameOver = false;
        cnt = 0;
        score = 0;
        playerX = 10;
        playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;
        playerHp = 30;
        

        backgroundMusic.start();

        playerAttackList.clear();
        bossAttackList.clear();
        enemyAttackList.clear();
        enemyList.clear();
        healthList.clear();
    }

    private void healthAppearProcess() {
    	if (cnt % 95 == 0) {
            health = new HealthItem(1120, (int)(Math.random()*621));
            healthList.add(health);
        }
    	
    }
    
    private void healthItemMove() {
    	for (int i = 0; i< healthList.size(); i++) {
    		health = healthList.get(i);
    		health.move();
        }
    }
    
    private void keyProcess() {
        if (up && playerY - playerSpeed > 0) playerY -= playerSpeed;
        if (down && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
        if (left && playerX - playerSpeed > 0) playerX -= playerSpeed;
        if (right && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
        if (shooting && cnt % 15 == 0) {
            playerAttack = new PlayerAttack(playerX + 222, playerY + 100);
            playerAttackList.add(playerAttack);
        }
    }
    
    private void playerAttackProcess() {
        for (int i = 0; i < playerAttackList.size(); i++) {
            playerAttack = playerAttackList.get(i);
            playerAttack.fire();
            
            for (int j = 0; j < enemyList.size(); j++) {
                enemy = enemyList.get(j);
                if (playerAttack.x > enemy.x && playerAttack.x < enemy.x + enemy.width && playerAttack.y > enemy.y && playerAttack.y < enemy.y + enemy.height) {
                    enemy.hp  -= playerAttack.attack;
                    playerAttackList.remove(playerAttack);
                }
                if (enemy.hp <= 0) {
                    hitSound.start();
                    enemyList.remove(enemy);
                    score += 1000;
                }
            }

                if (playerAttack.x > boss.x && playerAttack.x < boss.x + boss.width && playerAttack.y > boss.y && playerAttack.y < boss.y + boss.height) {
                	boss.hp  -= playerAttack.attack;
                    playerAttackList.remove(playerAttack);
                }
                if (boss.hp <= 0) {
                    hitSound.start();
                    winSound.start();
                }
        }
    }

    private void enemyAppearProcess() {
        if (cnt % 50 == 0) {
            enemy = new Enemy(1120, (int)(Math.random()*621));
            enemyList.add(enemy);
        }
    }

    private void enemyMoveProcess() {
        for (int i = 0; i< enemyList.size(); i++) {
            enemy = enemyList.get(i);
            enemy.move();
        }
    }

    private void enemyAttackProcess() {
        if (cnt % 50 == 0) {
            enemyAttack = new EnemyAttack(enemy.x - 79, enemy.y + 35);
            enemyAttackList.add(enemyAttack);
        }

        for (int i = 0; i < enemyAttackList.size(); i++) {
            enemyAttack = enemyAttackList.get(i);
            enemyAttack.fire();

            if (enemyAttack.x > playerX & enemyAttack.x < playerX + playerWidth && enemyAttack.y > playerY && enemyAttack.y < playerY + playerHeight) {
                hitSound.start();
                playerHp -= enemyAttack.attack;
                enemyAttackList.remove(enemyAttack);
                if (playerHp <= 0) {
                	isOver = true;
                	gameOver = true;
                }
            }
        }
    }
    
    private void healthProcess() {

    	for (int i = 0; i < healthList.size(); i++) {
        	health = healthList.get(i);
        	
            if (health.x > playerX & health.x < playerX + playerWidth && health.y > playerY && health.y < playerY + playerHeight) {
                itemSound.start();
                if (playerHp == 30) {
                	playerHp +=0;
                }
                else
                	playerHp += health.hp;
                
                healthList.remove(health);
            }
    	}
    }

    private void bossAttackProcess() {
        if (cnt % 60 == 0) {
        	bossAttack = new BossAttack(boss.x - 79, boss.y + 125);
            bossAttackList.add(bossAttack);
            bossAttack = new BossAttack(boss.x - 79, boss.y + 70);
            bossAttackList.add(bossAttack);
        }

        for (int i = 0; i < bossAttackList.size(); i++) {
        	bossAttack = bossAttackList.get(i);
        	bossAttack.fire();

            if (bossAttack.x > playerX & bossAttack.x < playerX + playerWidth && bossAttack.y > playerY && bossAttack.y < playerY + playerHeight) {
                hitSound.start();
                playerHp -=bossAttack.attack;
                bossAttackList.remove(bossAttack);
                if (playerHp <= 0) {
                	isOver = true;
                	gameOver = true;
                }
            }
        }
    }
    

    public void bossGameDraw(Graphics g) {
        playerDraw(g);
        bossDraw(g);
        enemyDraw(g);
        healthDraw(g);
        infoDraw(g);
    }

    public void infoDraw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SCORE : " + score, 40, 80);
        g.drawString("MISSION : Defeat Boss", 750, 80);
        if (isOver() && gameOver()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 80));
            
            g.drawString("Press R to restart", 295, 380);
        }
        if(boss.hp == 0) {
        	isOver=true;
        	isComplete = true;
        	backgroundMusic.stop();
        	
    		g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 68));
            g.drawString("YOU WIN!", 450, 360);
            g.drawString("Press M to Return to Main", 175, 420);
    	}
    }

    public void playerDraw(Graphics g) {
        g.drawImage(player, playerX, playerY, null);
        g.setColor(Color.GREEN);
        g.fillRect(playerX - 1, playerY - 40, playerHp * 6, 20);
        for (int i = 0; i < playerAttackList.size(); i++) {
            playerAttack = playerAttackList.get(i);
            g.drawImage(playerAttack.image, playerAttack.x, playerAttack.y, null);
        }
    }

    public void bossDraw(Graphics g) {
    	
        g.drawImage(boss.getBoss(), boss.x, boss.y = (Main.SCREEN_HEIGHT - boss.height) / 2, null);
        g.setColor(Color.GREEN);
        g.fillRect(boss.x - 1, boss.y - 40, boss.hp * 15, 20);
        
        for (int i = 0; i < bossAttackList.size(); i++) {
        	bossAttack = bossAttackList.get(i);
            g.drawImage(bossAttack.image, bossAttack.x, bossAttack.y, null);
        }
    }
    
    public void healthDraw(Graphics g) {
        for (int i = 0; i< healthList.size(); i++) {
            health = healthList.get(i);
            g.drawImage(health.image, health.x, health.y, null);
        }
    }
    
    public void enemyDraw(Graphics g) {
        for (int i = 0; i< enemyList.size(); i++) {
            enemy = enemyList.get(i);
            g.drawImage(enemy.image, enemy.x, enemy.y, null);
            g.setColor(Color.GREEN);
            g.fillRect(enemy.x + 1, enemy.y - 40, enemy.hp * 15, 20);
        }
        for (int i = 0; i < enemyAttackList.size(); i++) {
            enemyAttack = enemyAttackList.get(i);
            g.drawImage(enemyAttack.image, enemyAttack.x, enemyAttack.y, null);
        }
    }

    public boolean isOver() {
        return isOver;
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public boolean gameOver() {
        return gameOver;
    }

	public boolean getThisStage2 () {
		return this.thisStage2;
		
	}
	
	public void setUp2(boolean up) {
        this.up = up;
    }

    public void setDown2(boolean down) {
        this.down = down;
    }

    public void setLeft2(boolean left) {
        this.left = left;
    }

    public void setRight2(boolean right) {
        this.right = right;
    }

    public void setShooting2(boolean shooting) {
        this.shooting = shooting;
    }

}
