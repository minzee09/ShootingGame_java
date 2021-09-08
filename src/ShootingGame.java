  
import javax.swing.*;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class ShootingGame extends JFrame {
	
    private Image bufferImage;
    private Graphics screenGraphic;

    private Image mainScreen = new ImageIcon("src/images/main_screen.png").getImage();
    private Image loadingScreen = new ImageIcon("src/images/loading_screen.png").getImage();
    private Image gameScreen = new ImageIcon("src/images/game_screen.png").getImage();
    private Image charScreen = new ImageIcon("src/images/char_screen.png").getImage();
    private Image bossScreen = new ImageIcon("src/images/boss_screen.png").getImage();

    private boolean isMainScreen, isLoadingScreen, isGameScreen, isCharScreen, isBossScreen;

    private Game game = new Game();
    private Game2 game2 = new Game2();

    private Audio backgroundMusic;

      public ShootingGame() {
        setTitle("Shooting Game");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        init();
    }

    private void init() {
        isMainScreen = true;
        isLoadingScreen = isCharScreen = isGameScreen = isBossScreen = false;

        backgroundMusic = new Audio("src/audio/menuBGM1.wav", true);
        backgroundMusic.start();
        
        addKeyListener(new KeyListener());
    }
    private void chooseChar() {
    	isMainScreen = false;
    	isCharScreen = true;
    }
    
    private void gameStart() {
        isMainScreen = false;
        isLoadingScreen = true;

        Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() {
                backgroundMusic.stop();
                isLoadingScreen = false;
                isGameScreen = true;
                
                game.start();
            }
        };
        loadingTimer.schedule(loadingTask, 3000);
    }
    

    public void paint(Graphics g) {
        bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage, 0, 0, null);
    }

    public void screenDraw(Graphics g) {
        if (isMainScreen) {
            g.drawImage(mainScreen, 0, 0, null);
        }
        if (isCharScreen) {
            g.drawImage(charScreen, 0, 0, null);
        }
        if (isLoadingScreen) {
            g.drawImage(loadingScreen, 0, 0, null);
        }
        if (isGameScreen) {
            g.drawImage(gameScreen, 0, 0, null);
            game.gameDraw(g);
        }
        if (isBossScreen) {
            g.drawImage(bossScreen, 0, 0, null);
            game2.bossGameDraw(g);
        }
        this.repaint();
    }

    class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if(game.getThisStage())game.setUp(true);
                    if(game2.getThisStage2()) game2.setUp2(true);
                    break;
                case KeyEvent.VK_S:
                	if(game.getThisStage())game.setDown(true);
                    if(game2.getThisStage2()) game2.setDown2(true);
                    break;
                case KeyEvent.VK_A:
                	if(game.getThisStage())game.setLeft(true);
                    if(game2.getThisStage2()) game2.setLeft2(true);
                    break;
                case KeyEvent.VK_D:
                	if(game.getThisStage())game.setRight(true);
                    if(game2.getThisStage2()) game2.setRight2(true);
                    break;
                case KeyEvent.VK_R:
                    if (game.isOver() && game.getThisStage()) game.reset();
                    if (game2.isOver() && game2.getThisStage2()) game2.reset();
                    break;
                case KeyEvent.VK_C:
                    if (game.nextStage() && game.isOver()) { isBossScreen = true; game2.start();}
                    break;
                case KeyEvent.VK_M:
                    if (game2.isOver() && game2.isComplete()) { setVisible(false); new ShootingGame(); }
                    break;
                case KeyEvent.VK_SPACE:
                	if(game.getThisStage())game.setShooting(true);
                    if(game2.getThisStage2()) game2.setShooting2(true);
                    break;
                case KeyEvent.VK_ENTER:
                    if (isMainScreen) chooseChar();
                    break;
                case KeyEvent.VK_1:
                    if (isCharScreen) {
                    	game.setPlayer(new ImageIcon("src/images/player.png").getImage());
                    	PlayerAttack.playerColor = 1; 
                    	gameStart();
                    }
                    break;
                case KeyEvent.VK_2:
                    if (isCharScreen) {
                    	game.setPlayer(new ImageIcon("src/images/player2.png").getImage());
                    	PlayerAttack.playerColor = 2; 
                    	gameStart();
                    }
                    break;
                case KeyEvent.VK_3:
                	if (isCharScreen) {
                    	game.setPlayer(new ImageIcon("src/images/player3.png").getImage());
                    	PlayerAttack.playerColor = 3; 
                    	gameStart();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    game.setUp(false);
                    game2.setUp2(false);
                    break;
                case KeyEvent.VK_S:
                    game.setDown(false);
                    game2.setDown2(false);
                    break;
                case KeyEvent.VK_A:
                    game.setLeft(false);
                    game2.setLeft2(false);
                    break;
                case KeyEvent.VK_D:
                    game.setRight(false);
                    game2.setRight2(false);
                    break;
                case KeyEvent.VK_SPACE:
                    game.setShooting(false);
                    game2.setShooting2(false);
                    break;
            }
        }
    }
}