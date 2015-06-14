package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import akyStudio.framework.Animate;

public class myOwn extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final int appWidth = 800, appHeight = 480;
	final String myGameGroundRowOrColumnSpread ="row", myGameOceanRowOrColumnSpread = "row";

	MyPlayer myPlayer;
	static MyBg myBG1, myBG2, myBG3;
	Graphics myGraphics;
	BufferedImage myImage, myCharacter, myBGImage, myDuckChar, myJumpChar,
			heliBoy1Image, heliBoy2Image, myPlayerBulletImage;
 	BufferedImage myGameGroundImage, myGameOceanImage;
	Timer gameTimer;
	Enemy_HeliBoy heliBoy1, heliBoy2;
	PlayerBullet myPlayerBullet;
	ArrayList<Image> myPlayerAnimationImages, enemyAnimationImages;
	Animate myPlayerAnimation, enemyAnimation;
	TileMap myGameGround1,myGameGround2,myGameGround3, myGameOcean1,myGameOcean2,myGameOcean3;
	int[][] myGameGroundSpread1,myGameGroundSpread2,myGameGroundSpread3,myGameOceanSpread1,myGameOceanSpread2,myGameOceanSpread3;

	@Override
	public void init() {

		setSize(appWidth, appHeight);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);

		// Grabbing Required Images
		myDuckChar = getBufferedImageResource("characterAnimation/down.png");
		myJumpChar = getBufferedImageResource("characterAnimation/jumped.png");
		myBGImage = getBufferedImageResource("environment_n_backgrounds/background.png");
		myPlayerBulletImage = getBufferedImageResource("bullet.jpg");
		myGameGroundImage = getBufferedImageResource("environment_n_backgrounds/tiledirt.png");
		myGameOceanImage = getBufferedImageResource("environment_n_backgrounds/tileocean.png");

		// Grabbing Animation resources
		myPlayerAnimationImages = animate("resource/images/characterAnimation");
		enemyAnimationImages = animate("resource/images/enemyAnimation");

		// Initializing Player Animation
		int[] myPlayerAnimationdurations = { 25, 10, 15, 10 };
		myPlayerAnimation = new Animate(myPlayerAnimationImages,
				myPlayerAnimationdurations);

		// Initializing Enemy Animation
		int[] enemyAnimationDurations = { 4, 7, 7, 7, 7, 7, 7, 7 };
		enemyAnimation = new Animate(enemyAnimationImages,
				enemyAnimationDurations);

		// Grabbing Animation images for Players and Enemy
		myCharacter = (BufferedImage) myPlayerAnimation.calculateFrame();
		heliBoy1Image = heliBoy2Image = (BufferedImage) enemyAnimation.calculateFrame();

		// Adding Key Listeners so that program can respond to the key events
		addKeyListener(this);

	}

	@Override
	public void start() {

		// Initializing Background
		myBG1 = new MyBg(0, 0);
		myBG2 = new MyBg(2160, 0);
		myBG3 = new MyBg(-2160, 0);

		//Initializing Grounds and oceans, 2nd Background
		myGameGround1 = new TileMap(-2160,0,appWidth, appHeight, myGameGroundImage.getWidth(),myGameGroundImage.getHeight() , myGameGroundRowOrColumnSpread, 11,4);
		myGameOcean1 = new TileMap(-2160,0,appWidth, appHeight, myGameOceanImage.getWidth(), myGameOceanImage.getHeight(), myGameOceanRowOrColumnSpread, 10,3);
		myGameGround2 = new TileMap(0,0,appWidth, appHeight, myGameGroundImage.getWidth(),myGameGroundImage.getHeight() , myGameGroundRowOrColumnSpread, 11,4);
		myGameOcean2 = new TileMap(0,0,appWidth, appHeight, myGameOceanImage.getWidth(), myGameOceanImage.getHeight(), myGameOceanRowOrColumnSpread, 10,3);
		myGameGround3 = new TileMap(2160,0,appWidth, appHeight, myGameGroundImage.getWidth(),myGameGroundImage.getHeight() , myGameGroundRowOrColumnSpread, 11,4);
		myGameOcean3 = new TileMap(2160,0,appWidth, appHeight, myGameOceanImage.getWidth(), myGameOceanImage.getHeight(), myGameOceanRowOrColumnSpread, 10,3);

		myGameGroundSpread1 = myGameGround1.tileSpreader();
		myGameOceanSpread1 = myGameOcean1.tileSpreader();
		myGameGroundSpread2 = myGameGround2.tileSpreader();
		myGameOceanSpread2 = myGameOcean2.tileSpreader();
		myGameGroundSpread3 = myGameGround3.tileSpreader();
		myGameOceanSpread3 = myGameOcean3.tileSpreader();
		
		// Initializing Player and his animation
		myPlayer = new MyPlayer(100, 400);

		// Initializing Enemies and their respective timers to allow them born
		heliBoy1 = new Enemy_HeliBoy(550, 40);
		heliBoy2 = new Enemy_HeliBoy(570, 250);
		gameTimer = new Timer();
		startNextEnemyCounter(heliBoy1);

		// Initializing bullets
		myPlayerBullet = new PlayerBullet(0, 0);

		// Finally starting the Applet/Game in a thread
		Thread myThread = new Thread(this);
		myThread.start();
	}

	public void bulletShoot() {
		if (myPlayer.ducked) {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 95);
			myPlayerBullet.setProjectileY(myPlayer.getY() + 10);
			myPlayerBullet.setJumpShoot(false);
		}

		else if (myPlayer.getJumping() > 0) {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 110);
			myPlayerBullet.setProjectileY(myPlayer.getY() - 41);
			myPlayerBullet.setJumpShoot(true);
		} else {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 110);
			myPlayerBullet.setProjectileY(myPlayer.getY() - 23);
			myPlayerBullet.setJumpShoot(false);
		}
		myPlayerBullet.visible = true;
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myBG1.calculatePosition();
			myBG2.calculatePosition();
			myBG3.calculatePosition();
			
			myPlayer.calculatePosition();			
			myGameGroundSpread1 = myGameGround1.calculatePosition(myGameGroundSpread1, myPlayer.getSpeedx());
			myGameOceanSpread1 = myGameOcean1.calculatePosition(myGameOceanSpread1, myPlayer.getSpeedx());
			myGameGroundSpread2 = myGameGround2.calculatePosition(myGameGroundSpread2, myPlayer.getSpeedx());
			myGameOceanSpread2 = myGameOcean2.calculatePosition(myGameOceanSpread2, myPlayer.getSpeedx());
			myGameGroundSpread3 = myGameGround3.calculatePosition(myGameGroundSpread3, myPlayer.getSpeedx());
			myGameOceanSpread3 = myGameOcean3.calculatePosition(myGameOceanSpread3, myPlayer.getSpeedx());

			myCharacter = (BufferedImage) myPlayerAnimation.calculateFrame();

			if (myPlayerBullet.visible)
				myPlayerBullet.calculatePosition();
			if (checkBornForEachEnemy(heliBoy1)) {
				heliBoy1.buzzPosition();
				heliBoy1Image = (BufferedImage) enemyAnimation.calculateFrame();
			}
			if (checkBornForEachEnemy(heliBoy2)) {
				heliBoy2.buzzPosition();
				heliBoy2Image = (BufferedImage) enemyAnimation.calculateFrame();
			}
			repaint();
			// System.out.println(a+"Updated");
			// a+=1;
		}
	}

	@Override
	public void update(Graphics g) {

		if (myImage == null) {
			myImage = (BufferedImage) createImage(this.getWidth(), this.getHeight());
			myGraphics = myImage.getGraphics();
		}

		myGraphics.setColor(getBackground());
		myGraphics.fillRect(0, 0, getWidth(), getHeight());
		myGraphics.setColor(getForeground());
		paint(myGraphics);
		g.drawImage(myImage, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		// System.out.println("paint called");

		// Background drawings
		g.drawImage(myBGImage, myBG3.getBgx(), myBG3.getBgy(), this);
		g.drawImage(myBGImage, myBG1.getBgx(), myBG1.getBgy(), this);
		g.drawImage(myBGImage, myBG2.getBgx(), myBG2.getBgy(), this);

		//2nd Background drawings
			for(int i=0;i<myGameGroundSpread1.length;i++){
				g.drawImage(myGameGroundImage, myGameGroundSpread1[i][0], myGameGroundSpread1[i][1], this);
				g.drawImage(myGameGroundImage, myGameGroundSpread2[i][0], myGameGroundSpread2[i][1], this);
				g.drawImage(myGameGroundImage, myGameGroundSpread3[i][0], myGameGroundSpread3[i][1], this);
			}
			for(int i=0;i<myGameOceanSpread1.length;i++){
				g.drawImage(myGameOceanImage, myGameOceanSpread1[i][0], myGameOceanSpread1[i][1], this);
				g.drawImage(myGameOceanImage, myGameOceanSpread2[i][0], myGameOceanSpread2[i][1], this);
				g.drawImage(myGameOceanImage, myGameOceanSpread3[i][0], myGameOceanSpread3[i][1], this);
			}
		
		// Player Drawings
		if (myPlayer.ducked)
			g.drawImage(myDuckChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else if (myPlayer.getJumping() > 0)
			g.drawImage(myJumpChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else
			g.drawImage(myCharacter, myPlayer.getX(), myPlayer.getY() - 63,
					this);

		// Enemy Drawings
		if (checkBornForEachEnemy(heliBoy1)) {
			g.drawImage(heliBoy1Image, heliBoy1.getX(), heliBoy1.getY(), this);
			startNextEnemyCounter(heliBoy2);
		}
		if (checkBornForEachEnemy(heliBoy2)) {
			g.drawImage(heliBoy2Image, heliBoy2.getX(), heliBoy2.getY(), this);
		}

		// Player Bullet Drawings
		if (myPlayerBullet.visible) {
			g.drawImage(myPlayerBulletImage, myPlayerBullet.projectileX,
					myPlayerBullet.projectileY, this);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			myPlayer.moveDown();
			myPlayer.setDucked(true);
			break;
		case KeyEvent.VK_UP:
			myPlayer.moveUp();
			break;
		case KeyEvent.VK_LEFT:
			myPlayer.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			myPlayer.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			myPlayer.jump();
			break;
		case KeyEvent.VK_CONTROL:
			bulletShoot();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			myPlayer.setDucked(false);
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_LEFT:
			myPlayer.stopMovingHorizontally();
			break;
		case KeyEvent.VK_RIGHT:
			myPlayer.stopMovingHorizontally();
			break;
		case KeyEvent.VK_SPACE:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void startNextEnemyCounter(Enemy_HeliBoy tempEnemy) {
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				tempEnemy.readyToBorn = true;
			}

		}, 5000);

	}

	public boolean checkBornForEachEnemy(Enemy_HeliBoy tempEnemy) {
		return tempEnemy.readyToBorn;
	}
	
	private BufferedImage getBufferedImageResource(String ImageName) {
		try {
			return ImageIO.read(this.getClass().getResource(
					"/resource/images/" + ImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<Image> animate(String relativePath) {

		File resourceDir = new File(relativePath);
		File[] imageList = resourceDir.listFiles();
		ArrayList<Image> tempList = new ArrayList<Image>();

		for (int i = 0; i < imageList.length; i++) {
			String s = imageList[i].getName().substring(0,
					imageList[i].getName().length() - 4);
			if (s.equalsIgnoreCase(Integer.toString(i))) {
				try {
					tempList.add(ImageIO.read(this.getClass().getResource(
							"/" + relativePath + "/" + imageList[i].getName())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tempList;
	}
}
